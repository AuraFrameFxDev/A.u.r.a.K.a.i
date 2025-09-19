@echo off
echo Applying final Genesis Protocol build fixes...

REM Clean and refresh dependencies
gradlew --no-daemon clean --refresh-dependencies

REM Force version consistency by clearing gradle cache
rmdir /s /q .gradle\caches 2>nul

echo Genesis Protocol fixes applied. Ready to build!
gradlew --no-daemon assembleDebug

pause
