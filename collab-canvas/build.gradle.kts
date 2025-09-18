// ==== GENESIS PROTOCOL - COLLAB CANVAS MODULE ====
// Collaborative canvas module for real-time drawing
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // ...
}


android {
    namespace = "dev.aurakai.auraframefx.collabcanvas"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(24)
            dependencies {
                // Module dependencies
                implementation(project(":core-module"))
                implementation(project(":colorblendr"))

                // Core Android
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.appcompat)
                implementation(libs.bundles.lifecycle)

                // Compose
                implementation(platform(libs.androidx.compose.bom))
                implementation(libs.bundles.compose.ui)
                debugImplementation(libs.bundles.compose.debug)

                // Hilt
                implementation(libs.hilt.android)
                ksp(libs.hilt.compiler)
                implementation(libs.hilt.android)

                // Testing
                testImplementation(libs.junit.jupiter.api)
                testRuntimeOnly(libs.junit.jupiter.engine)
                androidTestImplementation(libs.androidx.espresso.core)
                androidTestImplementation(platform(libs.androidx.compose.bom))
                androidTestImplementation(libs.androidx.compose.ui.test.junit4)
                implementation(libs.androidx.compose.material.icons.extended)

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
                testImplementation(libs.bundles.testing.unit)
                androidTestImplementation(libs.bundles.testing.android)
            }

            tasks.register("collabStatus") {
                group = "genesis"
                doLast {
                    println("🎨 COLLAB CANVAS - ${android.namespace} - Ready!")
                }
            }
        }
    }
}
