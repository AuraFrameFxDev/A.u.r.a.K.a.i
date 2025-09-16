pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
    }
    plugins {
        id("com.android.application") version "9.0.0-alpha05" apply false
        id("com.android.library") version "9.0.0-alpha05" apply false
        id("org.jetbrains.kotlin.android") version "2.2.20" apply false
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
        id("com.google.gms.google-services") version "4.4.3" apply false
        id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version("2.0.1") apply false
        id("org.lsposed.lsparanoid") version("1.0.0") apply false
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://androidx.dev/storage/compose-compiler/repository/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
        maven { url = uri("https://jitpack.io") }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

rootProject.name = "ReGenesis"

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
