// GENESIS PROTOCOL - MODULE A
plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
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
    kotlinOptions {
        jvmTarget = "23"
    }
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Add other module-specific dependencies here
    implementation(libs.kotlin.stdlib.jdk8)
}

tasks.register("moduleAStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE A - Ready (Java 24)") }
}
