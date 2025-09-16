// ==== GENESIS PROTOCOL - COLORBLENDR MODULE ====
// Color utility and theming module

plugins {
<<<<<<< Updated upstream
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
=======
    id("com.android.library")
    alias(libs.plugins.ksp)
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
>>>>>>> Stashed changes
}

android {
    namespace = "dev.aurakai.auraframefx.colorblendr"
<<<<<<< Updated upstream
    compileSdk = 35
=======
    compileSdk = 36
>>>>>>> Stashed changes
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
<<<<<<< Updated upstream
=======
    }
    
    // For test builds
    testOptions {
        targetSdk = 36
    }
    
    // For linting
    lint {
        targetSdk = 36
>>>>>>> Stashed changes
    }
    
    buildFeatures {
        buildConfig = true
        // compose = true  // Removed as per user's request to handle compose separately
    }
    
    // Compose options
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
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

tasks.register("colorblendrStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24)") }
}
