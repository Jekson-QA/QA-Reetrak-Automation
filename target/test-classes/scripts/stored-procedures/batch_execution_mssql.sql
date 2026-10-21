USE [${databaseName}];

SET NOCOUNT ON;

DECLARE @BatchId INT = ${batchId};
DECLARE
    @TargetTable NVARCHAR(128),
    @SourceTable NVARCHAR(128),
    @ColumnName NVARCHAR(128),
    @Interval INT,
    @SQL NVARCHAR(MAX),
    @WhereClause NVARCHAR(MAX),
    @InnerQty INT,
    @Qty INT,
    @Now DATETIME,
    @ChildHasScanTime BIT,
    @ChildMaxScanDate DATETIME,
    @ChildLevel INT,
    @ParentLevel INT;

BEGIN TRY

IF OBJECT_ID('tempdb..#OutputData') IS NOT NULL
    DROP TABLE #OutputData;

CREATE TABLE #OutputData
(
    Id INT,
    BatchId INT,
    level INT,
    SSCC INT,
    count INT,
    isbulk2dLvl INT,
    inter_val INT,
    TableName NVARCHAR(100),
    ColumnName NVARCHAR(100)
);

INSERT INTO #OutputData
SELECT
    bl.Id,
    bl.BatchId,
    bl.level,
    bl.SSCC,
    bl.count,
    bl.isbulk2dLvl,
    bl.inter_val,
    CASE
        WHEN bl.level = 6 THEN 'palletdetail'
        WHEN bl.SSCC = 1 THEN 'ssccdetail'
        WHEN bl.level = 1 THEN 'primarycode'
        WHEN bl.level = 2 THEN 'secondary'
        WHEN bl.level = 3 THEN 'tertiary'
        WHEN bl.level = 4 THEN 'fourthcode'
        ELSE NULL
    END,
    CASE
        WHEN bl.level = 6 THEN NULL
        WHEN bl.SSCC = 1 THEN 'palletid'
        WHEN bl.level = 1 THEN 'secondid'
        WHEN bl.level = 2 THEN 'tertiaryid'
        WHEN bl.level = 3 THEN 'fourthid'
        WHEN bl.level = 4 THEN 'fifthid'
        ELSE NULL
    END
FROM batchlevel bl
WHERE bl.BatchId = @BatchId;

;WITH cte AS (
    SELECT inter_val,
           ROW_NUMBER() OVER (ORDER BY level, Id) rn
    FROM #OutputData
)
SELECT
    @InnerQty = MAX(CASE WHEN rn = 1 THEN inter_val END),
    @Qty = MAX(CASE WHEN rn = 2 THEN inter_val END)
FROM cte;

IF OBJECT_ID('tempdb..#NextLevel') IS NOT NULL
    DROP TABLE #NextLevel;

SELECT
    t.TableName AS TargetTable,
    t.ColumnName,
    s.inter_val AS IntervalVal,
    s.TableName AS SourceTable,
    t.level AS ChildLevel,
    s.level AS ParentLevel
INTO #NextLevel
FROM (
    SELECT *, ROW_NUMBER() OVER (ORDER BY level, Id) rn
    FROM #OutputData
) t
LEFT JOIN (
    SELECT *, ROW_NUMBER() OVER (ORDER BY level, Id) rn
    FROM #OutputData
) s
    ON s.rn = t.rn + 1
WHERE t.ColumnName IS NOT NULL;

DECLARE cur CURSOR FAST_FORWARD FOR
SELECT TargetTable, ColumnName, IntervalVal, SourceTable, ChildLevel, ParentLevel
FROM #NextLevel;

OPEN cur;

SET @Now = DATEADD(MINUTE, DATEDIFF(MINUTE, 0, GETDATE()) + 1, 0);

FETCH NEXT FROM cur
INTO @TargetTable, @ColumnName, @Interval, @SourceTable, @ChildLevel, @ParentLevel;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @ChildHasScanTime = 0;
    SET @ChildMaxScanDate = NULL;

    SET @WhereClause = '
    (
        BatchId = CAST(@BatchId AS VARCHAR(50))
        OR BatchId LIKE CAST(@BatchId AS VARCHAR(50)) + '',%''
        OR BatchId LIKE ''%,'' + CAST(@BatchId AS VARCHAR(50))
        OR BatchId LIKE ''%,'' + CAST(@BatchId AS VARCHAR(50)) + '',%''
    )';

    SET @SQL = '
    SELECT
        @ChildHasScanTime = CASE WHEN COUNT(scandate) > 0 THEN 1 ELSE 0 END,
        @ChildMaxScanDate = MAX(scandate)
    FROM ' + QUOTENAME(@TargetTable) + '
    WHERE ' + @WhereClause;

    IF @TargetTable = 'ssccdetail'
    BEGIN
        SET @SQL = @SQL + ' AND level = @ChildLevel';
    END

    EXEC sp_executesql
        @SQL,
        N'@BatchId INT, @ChildLevel INT, @ChildHasScanTime BIT OUTPUT, @ChildMaxScanDate DATETIME OUTPUT',
        @BatchId, @ChildLevel, @ChildHasScanTime OUTPUT, @ChildMaxScanDate OUTPUT;

    IF @SourceTable IS NOT NULL AND ISNULL(@Interval, 0) > 0
    BEGIN
        IF @TargetTable = 'ssccdetail' AND @SourceTable = 'ssccdetail'
        BEGIN
            SET @SQL = '
            ;WITH child AS (
                SELECT id,
                       ROW_NUMBER() OVER (ORDER BY id) rn
                FROM ssccdetail
                WHERE ' + @WhereClause + ' AND level = @ChildLevel
            ),
            parent AS (
                SELECT id,
                       ROW_NUMBER() OVER (ORDER BY id) rn
                FROM ssccdetail
                WHERE ' + @WhereClause + ' AND level = @ParentLevel
            )
            UPDATE c
            SET ' + QUOTENAME(@ColumnName) + ' = p.id
            FROM ssccdetail c
            INNER JOIN child ch ON c.id = ch.id
            INNER JOIN parent p
                ON p.rn = CEILING(ch.rn * 1.0 / @Interval)
            WHERE ' + @WhereClause + ' AND c.level = @ChildLevel';

            EXEC sp_executesql
                @SQL,
                N'@BatchId INT, @Interval INT, @ChildLevel INT, @ParentLevel INT',
                @BatchId, @Interval, @ChildLevel, @ParentLevel;
        END
        ELSE
        BEGIN
            SET @SQL = '
            ;WITH tgt AS (
                SELECT *,
                       ROW_NUMBER() OVER (ORDER BY id) rn
                FROM ' + QUOTENAME(@TargetTable) + '
                WHERE ' + @WhereClause + '
            ),
            src AS (
                SELECT id,
                       ROW_NUMBER() OVER (ORDER BY id) rn
                FROM ' + QUOTENAME(@SourceTable) + '
                WHERE ' + @WhereClause + ' __SRC_LEVEL_FILTER__
            )
            UPDATE tgt
            SET ' + QUOTENAME(@ColumnName) + ' = src.id
            FROM tgt
            INNER JOIN src
                ON src.rn = CEILING(tgt.rn * 1.0 / @Interval)';

            IF @SourceTable = 'ssccdetail'
            BEGIN
                SET @SQL = REPLACE(@SQL, '__SRC_LEVEL_FILTER__', 'AND level = @ParentLevel');
            END
            ELSE
            BEGIN
                SET @SQL = REPLACE(@SQL, '__SRC_LEVEL_FILTER__', '');
            END

            EXEC sp_executesql
                @SQL,
                N'@BatchId INT, @Interval INT, @ParentLevel INT',
                @BatchId, @Interval, @ParentLevel;
        END
    END

    SET @SQL = '
    ;WITH status_rows AS (
        SELECT id,
               ROW_NUMBER() OVER (ORDER BY id) rn
        FROM ' + QUOTENAME(@TargetTable) + '
        WHERE ' + @WhereClause;

    IF @TargetTable = 'ssccdetail'
    BEGIN
        SET @SQL = @SQL + ' AND level = @ChildLevel';
    END

    SET @SQL = @SQL + '
    )
    UPDATE tgt
    SET status = 1,
        isspecimen = 0,
        grade1 = ''A'',
        grade2 = ''A'',
        overallgrade = ''A'',
        reason = ''Success'',
        scandate = CASE
            WHEN tgt.scandate IS NOT NULL THEN tgt.scandate
            WHEN @ChildHasScanTime = 1 AND @ChildMaxScanDate IS NOT NULL
                THEN DATEADD(MINUTE, sr.rn, @ChildMaxScanDate)
            ELSE DATEADD(MINUTE, ((sr.rn - 1) / @Interval) * 2, @Now)
        END
    FROM ' + QUOTENAME(@TargetTable) + ' tgt
    INNER JOIN status_rows sr
        ON tgt.id = sr.id';

    EXEC sp_executesql
        @SQL,
        N'@BatchId INT, @ChildLevel INT, @Interval INT, @Now DATETIME, @ChildHasScanTime BIT, @ChildMaxScanDate DATETIME',
        @BatchId, @ChildLevel, @Interval, @Now, @ChildHasScanTime, @ChildMaxScanDate;

    IF @SourceTable IS NOT NULL
    BEGIN
        SET @SQL = '
        ;WITH status_rows AS (
            SELECT id,
                   ROW_NUMBER() OVER (ORDER BY id) rn
            FROM ' + QUOTENAME(@SourceTable) + '
            WHERE ' + @WhereClause;

        IF @SourceTable = 'ssccdetail'
        BEGIN
            SET @SQL = @SQL + ' AND level = @ParentLevel';
        END

        SET @SQL = @SQL + '
        )
        UPDATE src_tbl
        SET status = 1,
            grade1 = ''A'',
            grade2 = ''A'',
            overallgrade = ''A'',
            reason = ''Success'',
            scandate = CASE
                WHEN src_tbl.scandate IS NOT NULL THEN src_tbl.scandate
                WHEN @ChildHasScanTime = 1 AND @ChildMaxScanDate IS NOT NULL
                    THEN DATEADD(MINUTE, sr.rn, @ChildMaxScanDate)
                ELSE DATEADD(MINUTE, ((sr.rn - 1) * 2) + 1, @Now)
            END
        FROM ' + QUOTENAME(@SourceTable) + ' src_tbl
        INNER JOIN status_rows sr
            ON src_tbl.id = sr.id';

        EXEC sp_executesql
            @SQL,
            N'@BatchId INT, @ParentLevel INT, @Now DATETIME, @ChildHasScanTime BIT, @ChildMaxScanDate DATETIME',
            @BatchId, @ParentLevel, @Now, @ChildHasScanTime, @ChildMaxScanDate;
    END

    FETCH NEXT FROM cur
    INTO @TargetTable, @ColumnName, @Interval, @SourceTable, @ChildLevel, @ParentLevel;
END

CLOSE cur;
DEALLOCATE cur;

UPDATE BatchRequest
SET finalStatus = 2,
    isactive = 4
WHERE Id = @BatchId;

DELETE FROM line_batch_manager
WHERE batchid = @BatchId;

SELECT *
FROM #OutputData
ORDER BY level, Id;

DROP TABLE #OutputData;
DROP TABLE #NextLevel;

END TRY
BEGIN CATCH
    IF CURSOR_STATUS('global', 'cur') >= -1
    BEGIN
        CLOSE cur;
        DEALLOCATE cur;
    END

    IF OBJECT_ID('tempdb..#OutputData') IS NOT NULL
        DROP TABLE #OutputData;

    IF OBJECT_ID('tempdb..#NextLevel') IS NOT NULL
        DROP TABLE #NextLevel;

    THROW;
END CATCH;
