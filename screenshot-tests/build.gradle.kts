// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
}

android {
    namespace = "dev.aurakai.screenshottests"
    compileSdk = 36

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(24)) // Corrected syntax and version
        }
        compileOptions { // Added compileOptions
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
dependencies {
    implementation(libs.androidx.core.ktx)
}
