# Rename main dir
Move-Item -Path "src\main\java\com\sprintProject\OrderInventoryApplication" -Destination "src\main\java\com\sprintProject\orderinventoryapplication"

# Rename subdirs in main
$mainPath = "src\main\java\com\sprintProject\orderinventoryapplication"
Move-Item -Path "$mainPath\ControllerClasses" -Destination "$mainPath\controller"
Move-Item -Path "$mainPath\ServiceLayer" -Destination "$mainPath\service"
Move-Item -Path "$mainPath\RepositoryLayer" -Destination "$mainPath\repository"
Move-Item -Path "$mainPath\EntityClasses" -Destination "$mainPath\entity"
Move-Item -Path "$mainPath\CustomExceptions" -Destination "$mainPath\customexception"

# For test
Move-Item -Path "src\test\java\com\sprintProject\OrderInventoryApplication" -Destination "src\test\java\com\sprintProject\orderinventoryapplication"
$testPath = "src\test\java\com\sprintProject\orderinventoryapplication"
Move-Item -Path "$testPath\testServiceLayer" -Destination "$testPath\testservice"

# Now, replacements
$replacements = @{
    "com.sprintProject.OrderInventoryApplication.CustomExceptions" = "com.sprintProject.orderinventoryapplication.customexception"
    "com.sprintProject.OrderInventoryApplication.ControllerClasses" = "com.sprintProject.orderinventoryapplication.controller"
    "com.sprintProject.OrderInventoryApplication.ServiceLayer" = "com.sprintProject.orderinventoryapplication.service"
    "com.sprintProject.OrderInventoryApplication.RepositoryLayer" = "com.sprintProject.orderinventoryapplication.repository"
    "com.sprintProject.OrderInventoryApplication.EntityClasses" = "com.sprintProject.orderinventoryapplication.entity"
    "com.sprintProject.OrderInventoryApplication.testServiceLayer" = "com.sprintProject.orderinventoryapplication.testservice"
    "com.sprintProject.OrderInventoryApplication.dto" = "com.sprintProject.orderinventoryapplication.dto"
    "com.sprintProject.OrderInventoryApplication.security" = "com.sprintProject.orderinventoryapplication.security"
    "com.sprintProject.OrderInventoryApplication.serialization" = "com.sprintProject.orderinventoryapplication.serialization"
    "com.sprintProject.OrderInventoryApplication" = "com.sprintProject.orderinventoryapplication"
}

# Sort by length descending
$sortedReplacements = $replacements.GetEnumerator() | Sort-Object { $_.Key.Length } -Descending

Get-ChildItem -Path "src" -Recurse -Filter *.java | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file -Raw
    foreach ($rep in $sortedReplacements) {
        $old = $rep.Key
        $new = $rep.Value
        $content = $content -replace [regex]::Escape($old), $new
    }
    Set-Content $file $content
}