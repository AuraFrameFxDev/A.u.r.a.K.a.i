plugins {
    id("java-library")
    id("maven-publish")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"}

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)
    implementation(libs.hilt.android)


    // Hilt dependencies

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("listStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 LIST MODULE - Ready (Java 24)") }
}
