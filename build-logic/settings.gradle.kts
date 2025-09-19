/**
 * Settings for the build-logic module of AOSP-Re-Genesis.
 * Configures the version catalog for shared dependency management across convention plugins.
 */
rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo1.maven.org/maven2") }
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}