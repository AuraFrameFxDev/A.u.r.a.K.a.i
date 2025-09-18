// ==== GENESIS PROTOCOL - SECURE COMMUNICATION MODULE ====
// Security module using convention plugins

plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt) // Added Hilt plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
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

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_24
            targetCompatibility = JavaVersion.VERSION_24
        }

        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(24))
            }
        }
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

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(24))
        }
    }
}


dependencies {
    implementation(project(":core-module"))

    // Core Android
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.work:work-runtime-ktx:2.10.4")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-compiler:2.57.1")
    implementation("androidx.hilt:hilt-work:1.3.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")

    // Utilities
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("org.bouncycastle:bcprov-jdk18on:1.81")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")

    // Test dependencies (commented out to suppress junit-platform-launcher errors)
    // testImplementation(platform("org.junit.jupiter:junit-jupiter-api:5.13.4"))
    // testImplementation("junit:junit:4.13.2")
    // testImplementation(kotlin("test"))
    // testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    // testImplementation("org.junit.jupiter:junit-jupiter-params:5.13.4")
    // testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    // testImplementation("io.mockk:mockk-android:1.14.5")
    // testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    // androidTestImplementation("androidx.test.ext:junit:1.3.0")
    // androidTestImplementation("com.google.dagger:hilt-android-testing:2.57.1")
}

tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
}
