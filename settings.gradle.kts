// settings.gradle.kts

// Centralize repositories for plugins
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://repo1.maven.org/maven2") } // For OpenAPI Generator
    }
    plugins {
        id("com.android.application") version "8.3.0"
        id("org.jetbrains.kotlin.android") version "1.9.23"
        id("org.jetbrains.compose") version "1.6.10"
        id("com.google.devtools.ksp") version "1.9.23-1.0.20"
        id("com.google.firebase.crashlytics") version "3.0.1"
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "org.openapitools" && requested.id.name == "openapi-generator") {
                useModule("org.openapitools:openapi-generator-gradle-plugin:${requested.version}")
            }
        }
    }
}

// Centralize repositories for project dependencies
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// Enable modern Gradle features
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ReGenesis"

// Include the build-logic module as a composite build
includeBuild("build-logic")

// Include all of your app and library modules
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
