// ==== GENESIS PROTOCOL - SECURE COMMUNICATION MODULE ====
// Security module using convention plugins

plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt) // Added Hilt plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.aurakai.auraframefx.securecomm"
    compileSdk = 36

    defaultConfig {
        minSdk = 34

        // For testing and linting
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    kotlin {
        jvmToolchain(24)
    }
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24)
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        }
    }
}

dependencies {
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.bouncycastle)
    implementation(libs.kotlin.stdlib.jdk8)

    // Test dependencies
    testImplementation(platform(libs.junit.jupiter.api))
    testImplementation(libs.junit4)
    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
}
