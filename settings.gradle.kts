toolchainManagement {
    jvm {
        javaRepositories {
            gradlePluginPortal()
            mavenCentral()
        }
    }
}

// settings.gradle.kts - Optimized Repository Configuration
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://api.xposed.info/") }  // For YukiHookAPI
        maven { url = uri("https://jitpack.io") }         // For LSPosed
        maven { url = uri("https://maven.google.com") }   // For AndroidX dependencies
    }
}

// Centralize repositories for project dependencies
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://api.xposed.info/") }  // For YukiHookAPI
        maven { url = uri("https://jitpack.io") }         // For LSPosed
        maven { url = uri("https://maven.google.com") }   // For AndroidX dependencies
        
        // Explicitly declare JitPack repository with content filter
        exclusiveContent {
            forRepository {
                maven { url = uri("https://jitpack.io") }
            }
            filter {
                includeGroup("com.github.HighCapable.YukiHookAPI")
                includeGroup("com.github.LSPosed")
            }
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "ReGenesis"
includeBuild("build-logic")

// Include all modules (keep your existing includes)
include(
    ":app",
    ":core-module",
    ":feature-module",
    ":benchmark",
    ":collab-canvas",
    ":colorblendr",
    ":datavein-oracle-native",
    ":oracle-drive-integration",
    ":romtools",
    ":secure-comm",
    ":module-a",
    ":module-b",
    ":module-c",
    ":module-d",
    ":module-e",
    ":module-f",
    ":sandbox-ui",
    ":screenshot-tests",
    ":utilities",
    ":list",
    ":jvm-test"
)