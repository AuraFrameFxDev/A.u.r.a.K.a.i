// GENESIS PROTOCOL - MODULE C
plugins {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
=======
    id("com.android.library")
>>>>>>> Stashed changes
=======
    id("com.android.library")
>>>>>>> Stashed changes
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "dev.aurakai.auraframefx.module.c"
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    compileSdk = 35
=======
    compileSdk = 36
>>>>>>> Stashed changes
=======
    compileSdk = 36
>>>>>>> Stashed changes
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    buildFeatures {
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

tasks.register("moduleCStatus") {
    group = "genesis"
    doLast { 
        println("📦 MODULE C - ${android.namespace} - Ready!") 
    }
=======
    // For test builds
    testOptions {
        targetSdk = 36
    }
    
    // For linting
    lint {
        targetSdk = 36
    }
    
    compileOptions {
=======
    // For test builds
    testOptions {
        targetSdk = 36
    }
    
    // For linting
    lint {
        targetSdk = 36
    }
    
    compileOptions {
>>>>>>> Stashed changes
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
    }
}

tasks.register("moduleCStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 MODULE C - Ready (Java 24)") }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
