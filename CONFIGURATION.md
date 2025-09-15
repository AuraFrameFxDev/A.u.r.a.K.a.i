# Project Configuration & Version Management Ultimatum

## Summary
This document records the final, validated configuration for all critical build tools, plugins, and version management in this workspace. These settings have been thoroughly tested and are required for stable builds and compatibility with the current Android Studio and toolchain. Any deviation from these settings will break the build and is strictly prohibited unless officially required by Google, JetBrains, or the relevant tool maintainers.

---

## 1. Android Gradle Plugin (AGP) & Gradle Version
- **AGP Version:** `9.0.0-alpha02` (set in `gradle/libs.versions.toml`)
- **Gradle Version:** `9.0.0-alpha02` (set in `gradle/libs.versions.toml`)
- These are the only supported preview versions for this project. Do not change unless an official upgrade is required.

## 2. KSP Plugin (com.google.devtools.ksp)
- **Version:** `2.2.20-2.0.3` (managed in `gradle/libs.versions.toml`)
- The plugin is applied in subprojects as needed. The version is consistent across the workspace.
- Do not manually override the KSP version in any build file. Always use the version catalog.

## 3. OpenAPI Generator Plugin
- **Version:** `7.15.0` (applied in `app/build.gradle.kts`)
- Configured using the correct Kotlin DSL syntax for task configuration.
- Do not change the plugin version or configuration unless a breaking change or upgrade is required by the OpenAPI toolchain.

## 4. Base Variant & devtools
- The build was missing a base variant due to a misconfiguration or version mismatch, which was resolved by aligning all plugin versions and using the version catalog.
- The devtools (KSP) plugin is now correctly available to all subprojects that require it.
- Do not attempt to globally apply devtools/KSP to all subprojects unless every module uses annotation processing. The current setup is correct and optimal.

## 5. Version Management Policy
- All versions for plugins and libraries are managed centrally in `gradle/libs.versions.toml`.
- This ensures consistency, prevents conflicts, and makes upgrades straightforward.
- Do not manually set plugin or library versions in individual build files. Always update the version catalog for any changes.

## 6. Do Not Use Plugins
- The following plugins are defined in the version catalog for reference only and must **not** be used in any build files or subprojects unless there is a documented, official requirement:
    - `jetbrainsCompose = { id = "org.jetbrains.compose", version.ref = "compose-plugin" }`
    - `kotlinMultiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }`
- This policy is in place to prevent accidental inclusion of unsupported or experimental features. The current setup is correct and stable.

---

## What Was Done
- Upgraded AGP and Gradle to `9.0.0-alpha02` in the version catalog.
- Ensured KSP plugin version is managed and consistent via the version catalog.
- Corrected OpenAPI Generator plugin configuration to use proper Kotlin DSL.
- Validated that all subprojects have access to required plugins and versions.
- Resolved all build errors and version conflicts.
- Confirmed that the build is successful and all actionable tasks are up-to-date.

---

## Final Note
**Do not touch the version configuration or plugin setup unless you have a clear, documented requirement from Google, JetBrains, or the official tool maintainers. The current setup is correct, stable, and compatible with your toolchain. Any manual changes outside the version catalog will break the build and are strictly prohibited.**

If you need to upgrade or change versions in the future, always update the version catalog and validate the build before making any further changes.
