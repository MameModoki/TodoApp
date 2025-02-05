# ルートパスを定義
$projectRoot = "C:\Users\tellm\IdeaProjects\TodoApp\src\main\java\com\example\todoapp"

# フォルダ構成の定義
$folders = @(
    "controllers",
    "models",
    "views"
)

# 必要なフォルダを作成
foreach ($folder in $folders)
{
    $path = "$projectRoot\$folder"
    if (-Not (Test-Path $path))
    {
        New-Item -Path $path -ItemType Directory
    }
}

# ファイルの移動
Move-Item -Path "$projectRoot\Task.java" -Destination "$projectRoot\models\" -Force
Move-Item -Path "$projectRoot\Priority.java" -Destination "$projectRoot\models\" -Force
Move-Item -Path "$projectRoot\Contact.java" -Destination "$projectRoot\models\" -Force
Move-Item -Path "$projectRoot\Memo.java" -Destination "$projectRoot\models\" -Force
Move-Item -Path "$projectRoot\TodoTab.java" -Destination "$projectRoot\models\" -Force

Move-Item -Path "$projectRoot\CalendarTab.java" -Destination "$projectRoot\views\" -Force
Move-Item -Path "$projectRoot\ContactTab.java" -Destination "$projectRoot\views\" -Force
Move-Item -Path "$projectRoot\MemoTab.java" -Destination "$projectRoot\views\" -Force
Move-Item -Path "$projectRoot\TodoTab.java" -Destination "$projectRoot\views\" -Force

# 不要ファイルを削除
Remove-Item -Path "$projectRoot\Main.java~" -ErrorAction SilentlyContinue
Remove-Item -Path "C:\Users\tellm\IdeaProjects\TodoApp\pom.xml~" -ErrorAction SilentlyContinue

Write-Output "Maven folder structure has been fixed."

