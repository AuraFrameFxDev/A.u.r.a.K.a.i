import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    id("org.jetbrains.dokka") version "2.0.0"
    id("com.diffplug.spotless") version "7.2.1"
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

// utilities/build.gradle.kts - CORRECTED DEPENDENCIES
dependencies {
    // All your existing dependencies (keep as-is)
    api(project(":list"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)
    implementation(libs.slf4j.api)
    implementation(libs.hilt.android)

    // Kotlin stdlib and coroutines
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.coroutines.core)

    // Testing dependencies (keep as-is)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
    testImplementation(kotlin("stdlib"))
    testRuntimeOnly(libs.slf4j.simple)
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("utilitiesStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 UTILITIES MODULE - Ready (Java 24)") }
}
