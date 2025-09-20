// build-logic/settings.gradle.kts
rootProject.name = "build-logic"  // Add this line

dependencyResolutionManagement {
    repositories { // ADDED repositories block for consistency
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    versionCatalogs {
        // Point the 'libs' catalog to the one in the parent project
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}