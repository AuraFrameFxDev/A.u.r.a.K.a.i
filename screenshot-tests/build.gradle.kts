// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "dev.aurakai.screenshottests"
    compileSdk = 36

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(24)
        }
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
}
