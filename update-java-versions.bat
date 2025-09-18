# Genesis Protocol - Java 24 & JVM 23 Universal Update Script

@echo off
echo Updating all modules to Java 24 / JVM 23...

REM List of modules to update
set modules=app core-module feature-module datavein-oracle-native oracle-drive-integration romtools secure-comm module-a module-b module-c module-d module-e module-f sandbox-ui collab-canvas colorblendr utilities list jvm-test

REM Update each module's build.gradle.kts file
for %%M in (%modules%) do (
    if exist "%%M\build.gradle.kts" (
        echo Updating %%M...
        
        REM Update compileOptions to Java 24
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'sourceCompatibility = JavaVersion.VERSION_17', 'sourceCompatibility = JavaVersion.VERSION_24' | Set-Content '%%M\build.gradle.kts'"
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'targetCompatibility = JavaVersion.VERSION_17', 'targetCompatibility = JavaVersion.VERSION_24' | Set-Content '%%M\build.gradle.kts'"
        
        REM Update kotlinOptions to JVM 23
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'jvmTarget = \"17\"', 'jvmTarget = \"23\"' | Set-Content '%%M\build.gradle.kts'"
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'jvmTarget = \"1.8\"', 'jvmTarget = \"23\"' | Set-Content '%%M\build.gradle.kts'"
        
        REM Add Java 24 toolchain if not present
        findstr /C:"toolchain" "%%M\build.gradle.kts" >nul || (
            echo Adding Java 24 toolchain to %%M...
            echo. >> "%%M\build.gradle.kts"
            echo // Java 24 toolchain for bleeding-edge features >> "%%M\build.gradle.kts"
            echo java { >> "%%M\build.gradle.kts"
            echo     toolchain { >> "%%M\build.gradle.kts"
            echo         languageVersion.set(JavaLanguageVersion.of(24)) >> "%%M\build.gradle.kts"
            echo     } >> "%%M\build.gradle.kts"
            echo } >> "%%M\build.gradle.kts"
        )
    )
)

echo Genesis Protocol updated to Java 24 / JVM 23 across all modules!
pause
