DROP PROCEDURE IF EXISTS BatchExecutionSOP;

CREATE PROCEDURE BatchExecutionSOP(IN p_BatchId INT)
proc_end: BEGIN

    DECLARE done INT DEFAULT 0;

    DECLARE v_TargetTable VARCHAR(128);
    DECLARE v_SourceTable VARCHAR(128);
    DECLARE v_ColumnName VARCHAR(128);
    DECLARE v_Interval INT;

    DECLARE v_InnerQty INT DEFAULT 0;
    DECLARE v_Qty INT DEFAULT 0;
    DECLARE v_SkipMapping TINYINT DEFAULT 0;
    DECLARE v_FinalStatus INT;
    DECLARE v_BaseNow DATETIME;
    DECLARE v_ChildHasScanTime TINYINT DEFAULT 0;
    DECLARE v_ChildMaxScanDate DATETIME;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    SELECT finalStatus
    INTO v_FinalStatus
    FROM BatchRequest
    WHERE Id = p_BatchId
    LIMIT 1;

    IF v_FinalStatus IS NULL OR v_FinalStatus <> 0 THEN
        SELECT 'Batch already processed or invalid BatchId' AS Message;
        LEAVE proc_end;
    END IF;

    DROP TEMPORARY TABLE IF EXISTS OutputData;

    CREATE TEMPORARY TABLE OutputData AS
    SELECT
        bl.Id, bl.BatchId, bl.level, bl.SSCC, bl.count,
        bl.isbulk2dLvl, bl.inter_val,
        CASE
            WHEN bl.level = 6 THEN 'palletdetail'
            WHEN bl.SSCC = 1 THEN 'ssccdetail'
            WHEN bl.level = 1 THEN 'primarycode'
            WHEN bl.level = 2 THEN 'secondary'
            WHEN bl.level = 3 THEN 'tertiary'
            WHEN bl.level = 4 THEN 'fourthcode'
        END AS TableName,
        CASE
            WHEN bl.level = 6 THEN NULL
            WHEN bl.SSCC = 1 THEN 'palletid'
            WHEN bl.level = 1 THEN 'secondid'
            WHEN bl.level = 2 THEN 'tertiaryid'
            WHEN bl.level = 3 THEN 'fourthid'
            WHEN bl.level = 4 THEN 'fifthid'
        END AS ColumnName
    FROM batchlevel bl
    WHERE bl.BatchId = p_BatchId
    ORDER BY bl.level;

    SELECT MAX(level) INTO @maxLevel FROM OutputData;

    IF NOT EXISTS (SELECT 1 FROM OutputData WHERE isbulk2dLvl = 1) THEN
        SET v_SkipMapping = 1;
    END IF;

    SELECT inter_val INTO v_InnerQty FROM OutputData ORDER BY level LIMIT 1;
    SELECT inter_val INTO v_Qty FROM OutputData ORDER BY level LIMIT 1,1;
    SET v_BaseNow = DATE_ADD(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:00'), INTERVAL 1 MINUTE);

    DROP TEMPORARY TABLE IF EXISTS BatchIds;
    CREATE TEMPORARY TABLE BatchIds (BatchId VARCHAR(50) PRIMARY KEY);

    DROP TEMPORARY TABLE IF EXISTS NextLevel;
    CREATE TEMPORARY TABLE NextLevel (
        TableName VARCHAR(100),
        ColumnName VARCHAR(100),
        inter_val INT,
        SourceTable VARCHAR(100)
    );

    DROP TEMPORARY TABLE IF EXISTS batch_mapping;
    CREATE TEMPORARY TABLE batch_mapping (
        tgt_id INT,
        src_id INT
    );

    SET @prevTable = NULL;
    SET @prevColumn = NULL;

    BEGIN
        DECLARE done2 INT DEFAULT 0;
        DECLARE v_Table VARCHAR(100);
        DECLARE v_Col VARCHAR(100);
        DECLARE v_Int INT;

        DECLARE cur2 CURSOR FOR
            SELECT TableName, ColumnName, inter_val FROM OutputData ORDER BY level;

        DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = 1;

        OPEN cur2;

        loop2: LOOP
            FETCH cur2 INTO v_Table, v_Col, v_Int;
            IF done2 THEN LEAVE loop2; END IF;

            IF @prevTable IS NOT NULL AND @prevColumn IS NOT NULL THEN
                INSERT INTO NextLevel VALUES (@prevTable, @prevColumn, v_Int, v_Table);
            END IF;

            SET @prevTable = v_Table;
            SET @prevColumn = v_Col;
        END LOOP;

        CLOSE cur2;
    END;

    BEGIN
        DECLARE cur CURSOR FOR
            SELECT TableName, ColumnName, inter_val, SourceTable FROM NextLevel;

        OPEN cur;

        main_loop: LOOP

            FETCH cur INTO v_TargetTable, v_ColumnName, v_Interval, v_SourceTable;
            IF done THEN LEAVE main_loop; END IF;

            SET v_ChildHasScanTime = 0;
            SET v_ChildMaxScanDate = NULL;

            SET @sql = CONCAT(
            'INSERT IGNORE INTO BatchIds SELECT BatchId FROM ', v_TargetTable,
            ' WHERE BatchId = ', p_BatchId);

            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;

            SET @sql = CONCAT(
            'SELECT CASE WHEN COUNT(scandate) > 0 THEN 1 ELSE 0 END, MAX(scandate)
             INTO @ChildHasScanTime, @ChildMaxScanDate
             FROM ', v_TargetTable, '
             WHERE BatchId = ', p_BatchId
            );

            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;

            SET v_ChildHasScanTime = IFNULL(@ChildHasScanTime, 0);
            SET v_ChildMaxScanDate = @ChildMaxScanDate;
            SET @BaseNow = v_BaseNow;
            SET @ChildHasScanTimeVar = v_ChildHasScanTime;
            SET @ChildMaxScanDateVar = v_ChildMaxScanDate;

            IF v_SourceTable IS NOT NULL
               AND v_Interval > 0
               AND v_SkipMapping = 0
            THEN

                TRUNCATE TABLE batch_mapping;

                IF v_TargetTable = 'secondary'
                   AND v_ColumnName = 'tertiaryid'
                   AND v_SourceTable = 'ssccdetail'
                THEN

                    SET @sql = CONCAT(
                    'INSERT INTO batch_mapping (tgt_id, src_id)
                     SELECT c.id, p.id
                     FROM (
                         SELECT id, BatchId,
                                @rnc := @rnc + 1 AS rn
                         FROM secondary, (SELECT @rnc := 0) r
                         WHERE BatchId = ', p_BatchId, '
                         ORDER BY id
                     ) c
                     JOIN (
                         SELECT id, BatchId,
                                @rnp := @rnp + 1 AS rn
                         FROM ssccdetail, (SELECT @rnp := 0) r
                         WHERE BatchId = ', p_BatchId, ' AND level = 3
                         ORDER BY id
                     ) p
                     ON p.rn = CEILING(c.rn / ', v_Interval, ')'
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                    SET @sql = CONCAT(
                    'UPDATE secondary tgt
                     JOIN batch_mapping m ON m.tgt_id = tgt.id
                     SET tgt.tertiaryid = m.src_id
                     WHERE tgt.BatchId = ', p_BatchId
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                ELSEIF v_TargetTable = 'ssccdetail' AND v_SourceTable = 'ssccdetail' THEN

                    SET @sql = CONCAT(
                    'INSERT INTO batch_mapping (tgt_id, src_id)
                     SELECT c.id, p.id
                     FROM (
                         SELECT id, BatchId,
                                @rnc := @rnc + 1 AS rn
                         FROM ssccdetail, (SELECT @rnc := 0) r
                         WHERE BatchId = ', p_BatchId, ' AND level = 3
                         ORDER BY id
                     ) c
                     JOIN (
                         SELECT id, BatchId,
                                @rnp := @rnp + 1 AS rn
                         FROM ssccdetail, (SELECT @rnp := 0) r
                         WHERE BatchId = ', p_BatchId, ' AND level = 4
                         ORDER BY id
                     ) p
                     ON p.rn = CEILING(c.rn / ', v_Interval, ')'
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                    SET @sql = CONCAT(
                    'UPDATE ssccdetail tgt
                     JOIN batch_mapping m ON m.tgt_id = tgt.id
                     SET tgt.', v_ColumnName, ' = m.src_id
                     WHERE tgt.BatchId = ', p_BatchId, '
                     AND tgt.level = 3'
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                ELSE

                    SET @sql = CONCAT(
                    'INSERT INTO batch_mapping (tgt_id, src_id)
                     SELECT t.id, s.id
                     FROM (
                        SELECT id, BatchId,
                               @rn1 := IF(@b1 = BatchId, @rn1+1,1) rn,
                               @b1 := BatchId
                        FROM ', v_TargetTable, ',
                             (SELECT @rn1:=0,@b1:=NULL) v1
                        WHERE BatchId = ', p_BatchId, '
                        ORDER BY id
                     ) t
                     JOIN (
                        SELECT id, BatchId,
                               @rn2 := IF(@b2 = BatchId, @rn2+1,1) rn,
                               @b2 := BatchId
                        FROM ', v_SourceTable, ',
                             (SELECT @rn2:=0,@b2:=NULL) v2
                        WHERE BatchId = ', p_BatchId, '
                        ORDER BY id
                     ) s
                     ON s.BatchId=t.BatchId
                     AND s.rn = CEILING(t.rn / ', v_Interval, ')'
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                    SET @sql = CONCAT(
                    'UPDATE ', v_TargetTable,
                    ' tgt JOIN batch_mapping m ON m.tgt_id=tgt.id
                     SET tgt.', v_ColumnName, '=m.src_id
                     WHERE tgt.BatchId = ', p_BatchId
                    );

                    PREPARE stmt FROM @sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;

                END IF;

            END IF;

            SET @sql = CONCAT(
            'UPDATE ', v_TargetTable, ' tgt
             JOIN (
                 SELECT id,
                        @rownum_tgt := @rownum_tgt + 1 AS rn
                 FROM ', v_TargetTable, ',
                      (SELECT @rownum_tgt := 0) r
                 WHERE BatchId = ', p_BatchId, '
                 ORDER BY id
             ) r ON r.id = tgt.id
             SET tgt.status = 1,
                 tgt.isspecimen = 0,
                 tgt.grade1 = ''A'',
                 tgt.grade2 = ''A'',
                 tgt.overallgrade = ''A'',
                 tgt.reason = ''Success'',
                 tgt.scandate = CASE
                     WHEN tgt.scandate IS NOT NULL THEN tgt.scandate
                     WHEN @ChildHasScanTimeVar = 1 AND @ChildMaxScanDateVar IS NOT NULL
                         THEN DATE_ADD(@ChildMaxScanDateVar, INTERVAL r.rn MINUTE)
                     ELSE DATE_ADD(@BaseNow, INTERVAL FLOOR((r.rn - 1) / ', v_Interval, ') * 2 MINUTE)
                 END
             WHERE tgt.BatchId = ', p_BatchId
            );

            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;

            IF v_SourceTable IS NOT NULL THEN

                SET @sql = CONCAT(
                'UPDATE ', v_SourceTable, ' src
                 JOIN (
                     SELECT id,
                            @rownum_src := @rownum_src + 1 AS rn
                     FROM ', v_SourceTable, ',
                          (SELECT @rownum_src := 0) r
                     WHERE BatchId = ', p_BatchId, '
                     ORDER BY id
                 ) r ON r.id = src.id
                 SET src.status = 1,
                     src.grade1 = ''A'',
                     src.grade2 = ''A'',
                     src.overallgrade = ''A'',
                     src.reason = ''Success'',
                     src.scandate = CASE
                         WHEN src.scandate IS NOT NULL THEN src.scandate
                         WHEN @ChildHasScanTimeVar = 1 AND @ChildMaxScanDateVar IS NOT NULL
                             THEN DATE_ADD(@ChildMaxScanDateVar, INTERVAL r.rn MINUTE)
                         ELSE DATE_ADD(@BaseNow, INTERVAL ((r.rn - 1) * 2) + 1 MINUTE)
                     END
                 WHERE src.BatchId = ', p_BatchId
                );

                PREPARE stmt FROM @sql;
                EXECUTE stmt;
                DEALLOCATE PREPARE stmt;

            END IF;

        END LOOP;

        CLOSE cur;
    END;

    UPDATE BatchRequest br
    JOIN BatchIds b ON CAST(br.Id AS CHAR) = b.BatchId
    SET br.finalStatus = 2,
        br.isactive = 4;

    DELETE lbm
    FROM line_batch_manager lbm
    JOIN BatchIds b ON CAST(lbm.batchid AS CHAR) = b.BatchId;

    SELECT * FROM OutputData;

    DROP TEMPORARY TABLE IF EXISTS batch_mapping;
    DROP TEMPORARY TABLE IF EXISTS BatchIds;
    DROP TEMPORARY TABLE IF EXISTS NextLevel;
    DROP TEMPORARY TABLE IF EXISTS OutputData;

END;

CALL BatchExecutionSOP(${batchId});

DROP PROCEDURE IF EXISTS BatchExecutionSOP;
