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