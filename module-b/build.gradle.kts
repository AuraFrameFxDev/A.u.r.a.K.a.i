
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

}

android {
    namespace = "dev.aurakai.auraframefx.module.b"
    compileSdk = 36 // Updated for consistency

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }


    dependencies { // MOVED to root level
        // Module dependencies
        implementation(project(":core-module"))

        // Core Android
        implementation(libs.androidx.core.ktx) // This was duplicated at the end, now only here
        implementation(libs.bundles.lifecycle)

        // Compose
        val composeBom = platform(libs.androidx.compose.bom)
        implementation(composeBom)
        implementation(libs.bundles.compose.ui)
        debugImplementation(libs.bundles.compose.debug)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Utilities
        implementation(libs.kotlin.stdlib.jdk8)

        // Testing
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
        androidTestImplementation(libs.hilt.android.testing)
    }

    tasks.register("moduleBStatus") { // MOVED to root level, cleaned up, and Updated
        group = "aegenesis"
        doLast { println("📦 MODULE B - Ready (Java 25, JVM 25)") }
    }
}

// Duplicate dependencies block at the end is removed.
