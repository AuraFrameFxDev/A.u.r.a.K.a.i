// core-module/build.gradle.kts - COMPLETE FIX
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

}

android {
    // 🔥 ADD THESE MISSING CONFIGURATIONS 🔥
    namespace = "dev.aurakai.auraframefx" // CHANGED
    compileSdk = 36  // Match your bleeding-edge setup

    defaultConfig {
        minSdk = 34      // Your required minimum

        // If this is an application module and you want its applicationId to be specific,
        // you might need to set it explicitly, e.g.:
        // applicationId = "dev.aurakai.auraframefx.coremodule" 
        // However, for the google-services.json to match, it needs to align with an entry in that file.
        // Since we're aligning with "dev.aurakai.auraframefx", we'll let namespace define it.
    }

    // 🔥 ENABLE COMPOSE FEATURES 🔥
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }



    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }



    dependencies {
        // Your existing dependencies stay the same
        api(project(":list"))
        implementation(libs.kotlin.stdlib.jdk8)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.commons.io)
        implementation(libs.commons.compress)
        implementation(libs.xz)
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Testing dependencies
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
        implementation(libs.androidx.core.ktx)
    }
}
