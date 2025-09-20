// GENESIS PROTOCOL - MODULE E
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.aurakai.auraframefx.module.e"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }

    // Modern Kotlin configuration // ADDED

    dependencies {
        // Module dependencies
        implementation(project(":core-module"))

        // Core Android
        implementation(libs.androidx.core.ktx)
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
    tasks.register("moduleEStatus") {
        group = "aegenesis"
        doLast { println("📦 MODULE E - Ready (Java 25, JVM 25)") } // Updated
    }
}

