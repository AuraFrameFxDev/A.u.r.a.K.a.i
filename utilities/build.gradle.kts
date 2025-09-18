import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `kotlin-dsl`
    id("com.google.devtools.ksp") version "2.2.20-2.0.3"
    id("com.diffplug.spotless") version "7.2.1"
    kotlin("plugin.serialization") version "2.2.20"
}

val libs = the<LibrariesForLibs>()

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

dependencies {
    // Kotlin Standard Library
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // AndroidX Dependencies
    implementation("androidx.activity:activity:1.5.1")
    implementation("androidx.annotation:annotation-experimental:1.3.1")
    implementation("androidx.fragment:fragment:1.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")
    implementation("androidx.savedstate:savedstate:1.2.0")

    // Project dependencies
    api(project(":list"))

    // Utilities
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)
    implementation(libs.slf4j.api)

    // Hilt for JVM
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Testing
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
    testImplementation(kotlin("test"))
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)

    // Test AndroidX Dependencies
    testImplementation("androidx.activity:activity:1.5.1")
    testImplementation("androidx.annotation:annotation-experimental:1.3.1")
    testImplementation("androidx.fragment:fragment:1.5.1")
    testImplementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    testImplementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")
    testImplementation("androidx.savedstate:savedstate:1.2.0")

    // Logging for tests
    testRuntimeOnly(libs.slf4j.simple)

    // Kotlin test
    testImplementation(kotlin("test-junit5"))
}

// Configure JUnit 5
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
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
