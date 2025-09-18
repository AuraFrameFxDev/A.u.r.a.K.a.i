plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // ...
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

// Centralized toolchain version to avoid duplication and drift
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

dependencies {
    // Module dependency
    api(project(":list"))

    // Kotlin Standard Library
    implementation(libs.kotlin.stdlib.jdk8)

    // Concurrency and coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)

    // Logging
    implementation(libs.slf4j.api)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Testing
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
}
