// build-logic/settings.gradle.kts

rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
    versionCatalogs {
        // Point the 'libs' catalog to the one in the parent project
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
