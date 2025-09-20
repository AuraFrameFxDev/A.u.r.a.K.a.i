// GENESIS PROTOCOL - MODULE C

plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

}

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }
}

dependencies {
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    ksp(libs.hilt.compiler)
    // Add other dependencies as needed
}

tasks.register("moduleCStatus") {
    group = "aegenesis" // Updated for consistency
    doLast { 
        println("📦 MODULE C - Ready (Java 25, JVM 25)") // Updated
    }
}
