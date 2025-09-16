plugins {
    // JVM library setup
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

// Centralized toolchain version
val jdkVersion = 17

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(jdkVersion)) }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Module dependency (utilities depends on list)
    api(project(":list"))

    // Concurrency and serialization
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)

    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)

    // Logging API only (no binding at library runtime)
    implementation(libs.slf4j)

    // Kotlin Standard Library
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing (JUnit 5)
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk.android)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("utilitiesStatus") {
    group = "genesis"
    doLast { 
        println("📦 UTILITIES MODULE - Ready!") 
    }
}
