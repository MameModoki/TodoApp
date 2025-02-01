# Maven 用のフォルダ構成を修正するスクリプト

# 現在のディレクトリを `TodoApp` に設定
$projectRoot = Get-Location

# Maven の正しいフォルダ構成
$srcMainJava = "$projectRoot\src\main\java"
$srcMainResources = "$projectRoot\src\main\resources"

# 必要なフォルダを作成
if (!(Test-Path $srcMainJava)) {
    New-Item -ItemType Directory -Path $srcMainJava | Out-Null
}
if (!(Test-Path $srcMainResources)) {
    New-Item -ItemType Directory -Path $srcMainResources | Out-Null
}

# Javaソースコードを `src/main/java` に移動
$oldSrcDir = "$projectRoot\src\TodoApp"
if (Test-Path $oldSrcDir) {
    Move-Item -Path "$oldSrcDir\*.java" -Destination $srcMainJava -Force
}

# FXML, CSS, 画像, JSON, 設定ファイルを `src/main/resources` に移動
$resourceFiles = Get-ChildItem -Path "$oldSrcDir" -Include "*.fxml", "*.css", "*.png", "*.jpg", "*.json", "*.properties" -Recurse
if ($resourceFiles) {
    Move-Item -Path $resourceFiles.FullName -Destination $srcMainResources -Force
}

# `src/TodoApp` が空なら削除
if ((Get-ChildItem -Path $oldSrcDir).Count -eq 0) {
    Remove-Item -Path $oldSrcDir -Force
}

Write-Host "Mavenフォルダ構成を修正しました。"
