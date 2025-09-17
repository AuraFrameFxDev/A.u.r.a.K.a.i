// build-logic/settings.gradle.kts
// build-logic/settings.gradle.kts

rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        // Point the 'libs' catalog to the one in the parent project
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
