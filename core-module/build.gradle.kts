plugins {
    kotlin("jvm")
    // JVM library setup
    id("java-library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

// Centralized toolchain version to avoid duplication and drift
val jdkVersion = 24

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(jdkVersion)) }
}

kotlin {
    jvmToolchain(24)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
    }
}

dependencies {
    // Module dependency
    api(project(":list"))

    // Concurrency and serialization
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)

    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)

    // Logging API only (do not bind implementation at runtime for libraries)
    implementation(libs.slf4j)

    // Testing (JUnit 5)
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk)
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
    implementation(libs.kotlin.stdlib.jdk8)
}

tasks.test {
    useJUnitPlatform()
}
