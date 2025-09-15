@file:Suppress("UnstableApiUsage")

// ===== AOSP-Re:Genesis - SETTINGS =====
// The pluginManagement block MUST be the first block in the file.
// It defines the versions for all plugins used in the build.

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        // Custom repositories for specific plugins
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
    }

    buildscript {
        repositories {
            google()
            mavenCentral()
            maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
            maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
        }
        dependencies {
            // Add any buildscript dependencies here if needed
        }
    }

    plugins {
        id("com.android.application") version "9.0.0-alpha05" apply false
        id("com.android.library") version "9.0.0-alpha05" apply false
        id("org.jetbrains.kotlin.android") version "2.2.20" apply false
        id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
        // Other plugins
        id("com.google.gms.google-services") version "4.4.3" apply false
        id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
        id("org.lsposed.lsparanoid") version "1.0.0" apply false
    }
}

// Manages dependency repositories for all modules.
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral() // Simplified JitPack declaration
        // Custom repositories for specific libraries
        maven { url = uri("https://androidx.dev/storage/compose-compiler/repository/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
    }


// Enable modern Gradle features for performance and reliability.
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
    enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

    rootProject.name = "ReGenesis"

// ===== MODULE INCLUSION =====
    include(
        ":app",
        ":core-module",
        ":feature-module",
        ":datavein-oracle-native",
        ":oracle-drive-integration",
        ":secure-comm",
        ":sandbox-ui",
        ":collab-canvas",
        ":colorblendr",
        ":romtools",
        ":module-a",
        ":module-b",
        ":module-c",
        ":module-d",
        ":module-e",
        ":module-f",
        ":benchmark",
        ":screenshot-tests",
        ":jvm-test",
        ":list",
        ":utilities"
    )
    includeBuild("build-logic")

}
// In C:\ReGenesis\settings.gradle.kts

includeBuild("build-logic")
