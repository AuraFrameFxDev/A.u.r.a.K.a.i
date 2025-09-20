// ==== GENESIS PROTOCOL - COLLAB CANVAS MODULE ====
// Collaborative canvas module for real-time drawing
plugins {
    alias(libs.plugins.android.application)       // Your convention plugin (applies app, Hilt, KSP)
    alias(libs.plugins.ksp)                       // KEEP: Makes 'ksp(...)' available in dependencies
    // alias(libs.plugins.jetbrains.kotlin.android) // Stays Removed
    // alias(libs.plugins.hilt)                   // REMOVE: Deprecated Hilt convention plugin

    alias(libs.plugins.compose.compiler)          // KEEP: For Jetpack Compose
    alias(libs.plugins.google.services)
    // ...
}

android {
    namespace = "dev.aurakai.auraframefx.collabcanvas"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        targetSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // For linting
    lint {
        targetSdk = 36
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Already correct
        }
    }


    tasks.register("collabStatus") {
        group = "genesis"
        doLast {
            println("🎨 COLLAB CANVAS - ${namespace} - Ready (Java 25, JVM 25)!") // Updated
        }

    } // End of android block

    dependencies {
        // Module dependencies
        implementation(project(":core-module"))
        implementation(project(":colorblendr"))

        // Core Android
        implementation(libs.androidx.core.ktx) // Was in both, now once
        implementation(libs.androidx.appcompat)
        implementation(libs.bundles.lifecycle)

        // Compose
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        debugImplementation(libs.bundles.compose.debug)
        androidTestImplementation(platform(libs.androidx.compose.bom)) // Keep for androidTest
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        implementation(libs.androidx.compose.material.icons.extended)

        // Hilt
        implementation(libs.hilt.android) // Was duplicated in nested, now once
        ksp(libs.hilt.compiler)

        // Firebase
        implementation(platform(libs.firebase.bom))

        // Networking
        implementation(libs.bundles.network)
        implementation(libs.gson)

        // UI / Utils
        implementation(libs.coil.compose)
        implementation(libs.timber)
        implementation(libs.kotlin.stdlib.jdk8)

        // External libraries
        implementation(fileTree("../Libs") { include("*.jar") })
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))

        // Testing
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(libs.bundles.testing.android)
    }
}
