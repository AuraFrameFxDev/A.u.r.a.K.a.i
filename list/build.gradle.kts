plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    `maven-publish`
    `java-library`
    alias(libs.plugins.compose.compiler)
}

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(24) }
}


dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.compose.runtime)
    // implementation("androidx.compose.runtime:runtime:1.8.2") // replaced with version catalog
    // Hilt dependencies (add at runtime if needed for DI)
    implementation(libs.hilt.core) // replaced direct dependency with version catalog
    // ksp(libs.hilt.compiler) // Uncomment if KSP is needed
    // testRuntimeOnly("org.junit.platform:junit-platform-launcher") // replaced with version catalog
    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}