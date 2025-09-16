// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
        name = "Gradle Releases"
    }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        name = "JetBrains Compose Dev"
    }
}

// Dependencies required for the convention plugins themselves.
dependencies {
    implementation(libs.android.gradle
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.hilt.android.gradle.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
    implementation(gradleApi())
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.junit.jupiter.api)
    implementation(libs.junit.jupiter.params)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockk.android))

}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass =
                "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidLibraryConventionPlugin" // Corrected name
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin" // Corrected name
        }
    }
}
includeBuild("build-logic")
