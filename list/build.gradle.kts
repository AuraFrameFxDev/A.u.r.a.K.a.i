
plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.compose")

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }


// Java 25 toolchain // Updated
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25)) // Updated
        }
    }
} // End of android block

dependencies { // MOVED to root level
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    ksp(libs.hilt.compiler)

    // Test dependencies
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test) // MODIFIED
    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks.register("listStatus") { // MOVED to root level
    group = "aegenesis"
    description = "Displays the status of the List Module"
    doLast {
        println("📦 LIST MODULE - $group - Ready (Java 25, JVM 25)") // Updated
    }
}
