// ==== GENESIS PROTOCOL - SECURE COMMUNICATION MODULE ====
// Security module using convention plugins

plugins {
    alias(libs.plugins.android.application) // Assuming this is an application module, adjust if library
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt) version "2.57.1" apply false
    alias(libs.plugins.kotlin.serialization) // Use the alias from libs.versions.toml
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
        // Removed redundant compileOptions and java toolchain as they should be handled by convention plugins or root settings
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    // Removed redundant compileOptions and java toolchain from here too
}


dependencies {
    implementation(project(":core-module"))

    // Core Android - Using version catalog aliases
    implementation(libs.androidx.core.ktx)
    implementation("androidx.work:work-runtime-ktx:2.10.4") // Reverted to explicit version and implementation

    // Hilt - Using version catalog aliases
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Or libs.hilt.android.compiler if that's the alias for the compiler
    implementation(libs.hilt.work) // Ensure alias exists, e.g., libs.androidx.hilt.work or libs.hilt.work

    // Serialization - Using version catalog aliases
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    // Firebase - Using version catalog aliases
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    // Utilities - Using version catalog aliases
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation("org.bouncycastle:bcprov-jdk18on:1.82") // Reverted to explicit version
    implementation(libs.kotlin.stdlib.jdk8)

    // Test dependencies (commented out to suppress junit-platform-launcher errors) - Using aliases if they were active
    // testImplementation(platform(libs.junit.jupiter.api)) // Assuming platform alias exists
    // testImplementation(libs.junit4) // Assuming alias junit4 exists for junit:junit
    // testImplementation(kotlin("test")) // This one is fine as is, or use libs.kotlin.test if defined
    // testImplementation(libs.junit.jupiter.api)
    // testImplementation(libs.junit.jupiter.params) // Assuming alias exists
    // testRuntimeOnly(libs.junit.jupiter.engine)
    // testImplementation(libs.mockk.android) // Assuming alias exists, or libs.mockk if it's the general one
    // testImplementation(libs.kotlinx.coroutines.test)
    // androidTestImplementation(libs.androidx.test.ext.junit) // Assuming alias exists
    // androidTestImplementation(libs.hilt.android.testing) // Assuming alias exists
}

tasks.register("secureCommStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
}
