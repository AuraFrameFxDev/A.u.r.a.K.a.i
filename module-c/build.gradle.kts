// GENESIS PROTOCOL - MODULE C
plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"}

android {
    namespace = "dev.aurakai.auraframefx.module.c"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24)
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Add other dependencies as needed
}

tasks.register("moduleCStatus") {
    group = "genesis"
    doLast { 
        println("📦 MODULE C - ${android.namespace} - Ready!") 
    }
}
