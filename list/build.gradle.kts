plugins {
    alias(libs.plugins.android.library) // Changed to use alias from version catalog
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

android {
    namespace = "dev.aurakai.auraframefx.list"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
}

// Java 24 toolchain
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}


dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Test dependencies
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testImplementation(kotlin("test"))
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks.register("listStatus") {
    group = "aegenesis"
    description = "Displays the status of the List Module"
    doLast {
        println("📦 LIST MODULE - $group - Ready (Java 24)")
    }
}
