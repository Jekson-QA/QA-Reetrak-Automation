param(
    [string]$InputMarkdown = "docs/Reetrak_Automation_Project_Guide.md",
    [string]$OutputPdf = "docs/Reetrak_Automation_Project_Guide.pdf"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Escape-PdfText {
    param([string]$Text)

    return $Text.Replace("\", "\\").Replace("(", "\(").Replace(")", "\)")
}

function New-Block {
    param(
        [string]$Text,
        [string]$Type = "body"
    )

    [PSCustomObject]@{
        Type = $Type
        Text = $Text
    }
}

function Convert-MarkdownToBlocks {
    param([string[]]$Lines)

    $blocks = [System.Collections.Generic.List[object]]::new()
    $inCodeBlock = $false

    foreach ($rawLine in $Lines) {
        $line = $rawLine.TrimEnd()

        if ($line -match '^```') {
            $inCodeBlock = -not $inCodeBlock
            continue
        }

        if ($inCodeBlock) {
            $blocks.Add((New-Block -Text $line -Type "code"))
            continue
        }

        if ([string]::IsNullOrWhiteSpace($line)) {
            $blocks.Add((New-Block -Text "" -Type "space"))
            continue
        }

        if ($line -eq "---") {
            $blocks.Add((New-Block -Text "" -Type "divider"))
            continue
        }

        if ($line -match '^#\s+(.*)$') {
            $blocks.Add((New-Block -Text $matches[1].Trim() -Type "title"))
            continue
        }

        if ($line -match '^##\s+(.*)$') {
            $blocks.Add((New-Block -Text $matches[1].Trim() -Type "heading"))
            continue
        }

        if ($line -match '^###\s+(.*)$') {
            $blocks.Add((New-Block -Text $matches[1].Trim() -Type "subheading"))
            continue
        }

        if ($line -match '^\-\s+(.*)$') {
            $blocks.Add((New-Block -Text ("* " + $matches[1].Trim()) -Type "bullet"))
            continue
        }

        if ($line -match '^\d+\.\s+(.*)$') {
            $blocks.Add((New-Block -Text $line.Trim() -Type "number"))
            continue
        }

        $blocks.Add((New-Block -Text $line -Type "body"))
    }

    return $blocks
}

function Get-Style {
    param([string]$Type)

    switch ($Type) {
        "title" {
            return @{
                Font = "F2"
                Size = 22
                Leading = 30
                MaxChars = 40
                Before = 0
                After = 8
            }
        }
        "heading" {
            return @{
                Font = "F2"
                Size = 15
                Leading = 22
                MaxChars = 62
                Before = 8
                After = 4
            }
        }
        "subheading" {
            return @{
                Font = "F2"
                Size = 12
                Leading = 18
                MaxChars = 72
                Before = 5
                After = 2
            }
        }
        "bullet" {
            return @{
                Font = "F1"
                Size = 10
                Leading = 15
                MaxChars = 86
                Before = 0
                After = 0
            }
        }
        "number" {
            return @{
                Font = "F1"
                Size = 10
                Leading = 15
                MaxChars = 86
                Before = 0
                After = 0
            }
        }
        "code" {
            return @{
                Font = "F3"
                Size = 9
                Leading = 13
                MaxChars = 92
                Before = 2
                After = 2
            }
        }
        "divider" {
            return @{
                Font = "F1"
                Size = 10
                Leading = 16
                MaxChars = 1
                Before = 6
                After = 6
            }
        }
        "space" {
            return @{
                Font = "F1"
                Size = 10
                Leading = 12
                MaxChars = 1
                Before = 0
                After = 0
            }
        }
        default {
            return @{
                Font = "F1"
                Size = 10
                Leading = 15
                MaxChars = 92
                Before = 0
                After = 0
            }
        }
    }
}

function Split-TextLine {
    param(
        [string]$Text,
        [int]$MaxChars
    )

    $result = [System.Collections.Generic.List[string]]::new()

    if ([string]::IsNullOrWhiteSpace($Text)) {
        $result.Add("")
        return $result
    }

    $remaining = $Text
    while ($remaining.Length -gt $MaxChars) {
        $splitAt = $remaining.LastIndexOf(' ', [Math]::Min($MaxChars, $remaining.Length - 1))
        if ($splitAt -lt 1) {
            $splitAt = $MaxChars
        }

        $result.Add($remaining.Substring(0, $splitAt).TrimEnd())
        $remaining = $remaining.Substring($splitAt).TrimStart()
    }

    if ($remaining.Length -gt 0) {
        $result.Add($remaining)
    }

    return $result
}

function Convert-BlocksToLines {
    param([object[]]$Blocks)

    $lines = [System.Collections.Generic.List[object]]::new()

    foreach ($block in $Blocks) {
        $style = Get-Style -Type $block.Type

        if ($block.Type -eq "space") {
            $lines.Add([PSCustomObject]@{
                Type = "space"
                Font = $style.Font
                Size = $style.Size
                Text = ""
                Leading = $style.Leading
            })
            continue
        }

        if ($block.Type -eq "divider") {
            $lines.Add([PSCustomObject]@{
                Type = "divider"
                Font = $style.Font
                Size = $style.Size
                Text = ""
                Leading = $style.Leading
            })
            continue
        }

        for ($i = 0; $i -lt $style.Before; $i++) {
            $lines.Add([PSCustomObject]@{
                Type = "space"
                Font = "F1"
                Size = 10
                Text = ""
                Leading = 4
            })
        }

        $wrapped = Split-TextLine -Text $block.Text -MaxChars $style.MaxChars
        foreach ($line in $wrapped) {
            $lines.Add([PSCustomObject]@{
                Type = $block.Type
                Font = $style.Font
                Size = $style.Size
                Text = $line
                Leading = $style.Leading
            })
        }

        for ($i = 0; $i -lt $style.After; $i++) {
            $lines.Add([PSCustomObject]@{
                Type = "space"
                Font = "F1"
                Size = 10
                Text = ""
                Leading = 4
            })
        }
    }

    return $lines
}

function New-ContentStream {
    param(
        [object[]]$PageLines,
        [int]$PageNumber,
        [int]$PageWidth,
        [int]$PageHeight,
        [string]$DocTitle
    )

    $headerY = $PageHeight - 34
    $bodyStartY = $PageHeight - 76
    $leftMargin = 46
    $dividerY = $PageHeight - 44
    $footerY = 24

    $sb = [System.Text.StringBuilder]::new()

    [void]$sb.AppendLine("BT")
    [void]$sb.AppendLine("/F2 10 Tf")
    [void]$sb.AppendLine(("0.18 0.33 0.56 rg"))
    [void]$sb.AppendLine(("1 0 0 1 {0} {1} Tm ({2}) Tj" -f $leftMargin, $headerY, (Escape-PdfText $DocTitle)))
    [void]$sb.AppendLine("ET")

    [void]$sb.AppendLine("0.75 0.84 0.93 RG")
    [void]$sb.AppendLine(("46 {0} m 549 {0} l S" -f $dividerY))

    $y = $bodyStartY
    foreach ($entry in $PageLines) {
        if ($entry.Type -eq "divider") {
            [void]$sb.AppendLine("0.88 0.90 0.93 RG")
            [void]$sb.AppendLine(("46 {0} m 549 {0} l S" -f ($y + 4)))
            $y -= 8
            continue
        }

        if ($entry.Type -eq "space") {
            $y -= [int]$entry.Leading
            continue
        }

        $x = if ($entry.Type -in @("bullet", "number")) { 58 } else { 46 }
        $color = switch ($entry.Type) {
            "title" { "0.11 0.24 0.42 rg" }
            "heading" { "0.14 0.31 0.52 rg" }
            "subheading" { "0.27 0.27 0.27 rg" }
            "code" { "0.20 0.20 0.20 rg" }
            default { "0.15 0.15 0.15 rg" }
        }

        [void]$sb.AppendLine("BT")
        [void]$sb.AppendLine(("/{0} {1} Tf" -f $entry.Font, $entry.Size))
        [void]$sb.AppendLine($color)
        [void]$sb.AppendLine(("1 0 0 1 {0} {1} Tm ({2}) Tj" -f $x, $y, (Escape-PdfText $entry.Text)))
        [void]$sb.AppendLine("ET")
        $y -= [int]$entry.Leading
    }

    [void]$sb.AppendLine("BT")
    [void]$sb.AppendLine("/F1 9 Tf")
    [void]$sb.AppendLine("0.45 0.45 0.45 rg")
    [void]$sb.AppendLine(("1 0 0 1 46 {0} Tm (Generated from workspace documentation) Tj" -f $footerY))
    [void]$sb.AppendLine(("1 0 0 1 500 {0} Tm (Page {1}) Tj" -f $footerY, $PageNumber))
    [void]$sb.AppendLine("ET")

    return $sb.ToString()
}

$inputPath = Join-Path (Get-Location) $InputMarkdown
$outputPath = Join-Path (Get-Location) $OutputPdf

if (-not (Test-Path -LiteralPath $inputPath)) {
    throw ("Input markdown file not found: {0}" -f $inputPath)
}

$docTitle = "Reetrak Automation Framework Guide"
$pageWidth = 595
$pageHeight = 842
$usableHeight = 680

$lines = Get-Content -LiteralPath $inputPath
$blocks = Convert-MarkdownToBlocks -Lines $lines
$renderLines = Convert-BlocksToLines -Blocks $blocks

$pages = [System.Collections.Generic.List[object]]::new()
$current = [System.Collections.Generic.List[object]]::new()
$currentHeight = 0

foreach ($line in $renderLines) {
    $lineHeight = if ($line.Type -eq "divider") { 8 } else { [int]$line.Leading }
    if ($currentHeight + $lineHeight -gt $usableHeight -and $current.Count -gt 0) {
        $pages.Add($current.ToArray())
        $current = [System.Collections.Generic.List[object]]::new()
        $currentHeight = 0
    }

    $current.Add($line)
    $currentHeight += $lineHeight
}

if ($current.Count -gt 0) {
    $pages.Add($current.ToArray())
}

$contentStreams = [System.Collections.Generic.List[string]]::new()
for ($i = 0; $i -lt $pages.Count; $i++) {
    $contentStreams.Add((New-ContentStream -PageLines $pages[$i] -PageNumber ($i + 1) -PageWidth $pageWidth -PageHeight $pageHeight -DocTitle $docTitle))
}

$objects = [System.Collections.Generic.List[string]]::new()
$objects.Add('1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj')

$kids = for ($i = 0; $i -lt $contentStreams.Count; $i++) {
    '{0} 0 R' -f (3 + ($i * 2))
}
$objects.Add(('2 0 obj << /Type /Pages /Count {0} /Kids [{1}] >> endobj' -f $contentStreams.Count, ($kids -join ' ')))

$font1ObjectNumber = 3 + ($contentStreams.Count * 2)
$font2ObjectNumber = $font1ObjectNumber + 1
$font3ObjectNumber = $font2ObjectNumber + 1

for ($i = 0; $i -lt $contentStreams.Count; $i++) {
    $pageObjectNumber = 3 + ($i * 2)
    $contentObjectNumber = $pageObjectNumber + 1
    $stream = $contentStreams[$i]
    $streamBytes = [System.Text.Encoding]::ASCII.GetBytes($stream)
    $objects.Add(('{0} 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 {1} {2}] /Resources << /Font << /F1 {3} 0 R /F2 {4} 0 R /F3 {5} 0 R >> >> /Contents {6} 0 R >> endobj' -f $pageObjectNumber, $pageWidth, $pageHeight, $font1ObjectNumber, $font2ObjectNumber, $font3ObjectNumber, $contentObjectNumber))
    $objects.Add(('{0} 0 obj << /Length {1} >> stream' -f $contentObjectNumber, $streamBytes.Length) + "`n" + $stream + 'endstream endobj')
}

$objects.Add(('{0} 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj' -f $font1ObjectNumber))
$objects.Add(('{0} 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >> endobj' -f $font2ObjectNumber))
$objects.Add(('{0} 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Courier >> endobj' -f $font3ObjectNumber))

$pdf = [System.Text.StringBuilder]::new()
[void]$pdf.AppendLine('%PDF-1.4')

$offsets = [System.Collections.Generic.List[int]]::new()
$offsets.Add(0)

foreach ($object in $objects) {
    $offsets.Add([System.Text.Encoding]::ASCII.GetByteCount($pdf.ToString()))
    [void]$pdf.AppendLine($object)
}

$xrefStart = [System.Text.Encoding]::ASCII.GetByteCount($pdf.ToString())
[void]$pdf.AppendLine('xref')
[void]$pdf.AppendLine(('0 {0}' -f ($objects.Count + 1)))
[void]$pdf.AppendLine('0000000000 65535 f ')

for ($i = 1; $i -le $objects.Count; $i++) {
    [void]$pdf.AppendLine(('{0:D10} 00000 n ' -f $offsets[$i]))
}

[void]$pdf.AppendLine('trailer')
[void]$pdf.AppendLine(('<< /Size {0} /Root 1 0 R >>' -f ($objects.Count + 1)))
[void]$pdf.AppendLine('startxref')
[void]$pdf.AppendLine($xrefStart)
[void]$pdf.AppendLine('%%EOF')

$outputDirectory = Split-Path -Parent $outputPath
if (-not (Test-Path -LiteralPath $outputDirectory)) {
    New-Item -ItemType Directory -Path $outputDirectory | Out-Null
}

[System.IO.File]::WriteAllText($outputPath, $pdf.ToString(), [System.Text.Encoding]::ASCII)
Write-Output ('PDF created: {0}' -f $outputPath)
