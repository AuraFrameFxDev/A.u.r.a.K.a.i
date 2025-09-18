import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    // JVM library setup
    id("java-library")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

dependencies {
    // Module dependency (utilities depends on list)
    api(project(":list"))

    // Concurrency and serialization
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // File operations and compression
    implementation("commons-io:commons-io:2.16.1")
    implementation("org.apache.commons:commons-compress:1.26.2")
    implementation("org.tukaani:xz:1.9")

    // Logging API only (no binding at library runtime)
    implementation("org.slf4j:slf4j-api:2.0.13")

    // Hilt dependencies (explicit coordinates)
    implementation("com.google.dagger:hilt-android:2.57.1")
    // No annotation processing needed for pure JVM module

    // Testing (JUnit 5)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation(kotlin("stdlib"))
    // Bind a simple logger only during tests
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.13")
    implementation(kotlin("stdlib-jdk8"))
    implementation("de.robv.android.xposed:yukihookapi:1.1.6")
    implementation("org.lsposed.lsposed:api:1.0")
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
