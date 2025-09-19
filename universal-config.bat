# Genesis Protocol - Universal Module Configuration Script
# Applies Java 24, JVM 23, NDK, YukiHook, LSPosed, and all standard dependencies to ALL modules

@echo off
echo Applying universal Genesis Protocol configuration to ALL modules...

REM Get list of all modules from settings.gradle.kts
set modules=app core-module feature-module benchmark collab-canvas colorblendr datavein-oracle-native oracle-drive-integration romtools secure-comm module-a module-b module-c module-d module-e module-f sandbox-ui screenshot-tests utilities list jvm-test

echo Configuring %~1 modules with universal settings...

REM Process each module
for %%M in (%modules%) do (
    if exist "%%M\build.gradle.kts" (
        echo Processing module: %%M
        
        REM Backup original file
        copy "%%M\build.gradle.kts" "%%M\build.gradle.kts.backup" >nul 2>&1
        
        REM Universal Java 24 / JVM 23 configuration
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'sourceCompatibility = JavaVersion\.VERSION_17', 'sourceCompatibility = JavaVersion.VERSION_24' | Set-Content '%%M\build.gradle.kts'"
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'targetCompatibility = JavaVersion\.VERSION_17', 'targetCompatibility = JavaVersion.VERSION_24' | Set-Content '%%M\build.gradle.kts'"
        powershell -Command "(Get-Content '%%M\build.gradle.kts') -replace 'jvmTarget = \"17\"', 'jvmTarget = \"23\"' | Set-Content '%%M\build.gradle.kts'"
        
        REM Add Java 24 toolchain if not present
        findstr /C:"toolchain" "%%M\build.gradle.kts" >nul || (
            echo // Java 24 toolchain >> "%%M\build.gradle.kts"
            echo java { >> "%%M\build.gradle.kts"
            echo     toolchain { >> "%%M\build.gradle.kts"
            echo         languageVersion.set(JavaLanguageVersion.of(24)) >> "%%M\build.gradle.kts"
            echo     } >> "%%M\build.gradle.kts"
            echo } >> "%%M\build.gradle.kts"
        )
        
        REM Add NDK configuration for modules that need it
        if "%%M"=="datavein-oracle-native" (
            echo Adding NDK configuration to %%M
            findstr /C:"externalNativeBuild" "%%M\build.gradle.kts" >nul || (
                echo     externalNativeBuild { >> "%%M\build.gradle.kts"
                echo         cmake { >> "%%M\build.gradle.kts"
                echo             path = file("src/main/cpp/CMakeLists.txt") >> "%%M\build.gradle.kts"
                echo             version = "3.31.6" >> "%%M\build.gradle.kts"
                echo         } >> "%%M\build.gradle.kts"
                echo     } >> "%%M\build.gradle.kts"
            )
        )
        
        if "%%M"=="secure-comm" (
            echo Adding NDK configuration to %%M
            findstr /C:"externalNativeBuild" "%%M\build.gradle.kts" >nul || (
                echo     externalNativeBuild { >> "%%M\build.gradle.kts"
                echo         cmake { >> "%%M\build.gradle.kts"
                echo             path = file("src/main/cpp/CMakeLists.txt") >> "%%M\build.gradle.kts"
                echo             version = "3.31.6" >> "%%M\build.gradle.kts"
                echo         } >> "%%M\build.gradle.kts"
                echo     } >> "%%M\build.gradle.kts"
            )
        )
        
        REM Add YukiHook/LSPosed dependencies where needed
        if "%%M"=="utilities" (
            echo Adding YukiHook/LSPosed dependencies to %%M
            findstr /C:"yukihook-api" "%%M\build.gradle.kts" >nul || (
                echo     // YukiHook and LSPosed dependencies >> "%%M\build.gradle.kts"
                echo     implementation(libs.yukihook.api) >> "%%M\build.gradle.kts"
                echo     implementation(libs.lsposed.api) >> "%%M\build.gradle.kts"
                echo     ksp(libs.yukihook.ksp) >> "%%M\build.gradle.kts"
            )
        )
        
        echo Module %%M configured successfully
    ) else (
        echo WARNING: %%M\build.gradle.kts not found, skipping...
    )
)

echo.
echo Universal Genesis Protocol configuration complete!
echo All modules now have:
echo - Java 24 compilation
echo - JVM 23 bytecode target  
echo - Java 24 toolchain
echo - NDK configuration (where applicable)
echo - YukiHook/LSPosed dependencies (where applicable)
echo.
pause
