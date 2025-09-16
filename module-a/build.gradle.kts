// GENESIS PROTOCOL - MODULE A
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
    namespace = "dev.aurakai.auraframefx.module.a"
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
    
=======
=======
>>>>>>> Stashed changes

    // For test builds
    testOptions {
        targetSdk = 36
    }

    // For linting
    lint {
        targetSdk = 36
    }

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        jvmTarget = "23"
    }
    kotlinOptions {
        jvmTarget = "23"
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))
    
    // Core Android
    implementation(libs.androidx.core.ktx)
<<<<<<< Updated upstream
<<<<<<< Updated upstream
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
=======

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
=======

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
>>>>>>> Stashed changes

    // Add other module-specific dependencies here
>>>>>>> Stashed changes
    implementation(libs.kotlin.stdlib.jdk8)
    
    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("moduleAStatus") {
<<<<<<< Updated upstream
    group = "genesis"
    doLast { 
        println("📦 MODULE A - ${android.namespace} - Ready!") 
    }
=======
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 MODULE A - Ready (Java 24)") }
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
