# A-F modules

// GENESIS PROTOCOL
        plugins {
            id("genesis.android.library")
            id("genesis.android.compose")
            alias(libs.plugins.ksp)
            alias(libs.plugins.hilt)
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
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24)
    }
}


ependencies {
    // Module dependencies
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
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
    implementation(libs.kotlin.stdlib.jdk8)

    kotlin {
        jvmToolchain(24)
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        }
    }

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}
tasks.register("moduleFStatus") {
    group = "genesis"
    doLast {
        println("📦 MODULE F - ${android.namespace} - Ready!")
    tasks.register("moduleFStatus") {
        group = "aegenesis"
        doLast { println("\uD83D\uDCE6 MODULE F - Ready (Java 24)") }