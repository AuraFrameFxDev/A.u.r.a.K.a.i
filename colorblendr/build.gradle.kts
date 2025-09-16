// ==== GENESIS PROTOCOL - COLORBLENDR MODULE ====
// Color utility and theming module

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "dev.aurakai.auraframefx.colorblendr"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))
    
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.lifecycle)
    
    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Utilities
    implementation(libs.timber)
    implementation(libs.kotlin.stdlib.jdk8)
    
    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}

tasks.register("colorStatus") {
    group = "genesis"
    doLast { 
        println("🌈 COLORBLENDR - ${android.namespace} - Ready!")
    }
}
