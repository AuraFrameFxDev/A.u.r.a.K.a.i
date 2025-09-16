plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    `maven-publish`
    `java-library`
}

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(17) }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.compose.runtime)
<<<<<<< Updated upstream
    
    // Hilt dependencies
    implementation(libs.hilt.core)
    
    // Testing
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk.android)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
=======
    // implementation("androidx.compose.runtime:runtime:1.8.2") // replaced with version catalog
    // Hilt dependencies (add at runtime if needed for DI)
    implementation(libs.hilt.core) // replaced direct dependency with version catalog
    // ksp(libs.hilt.compiler) // Uncomment if KSP is needed
    // testRuntimeOnly("org.junit.platform:junit-platform-launcher") // replaced with version catalog
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.mockk)
>>>>>>> Stashed changes
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "23"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "23"
}

tasks.test {
    useJUnitPlatform()
}
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes

tasks.register("listStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 LIST MODULE - Ready (Java 24)") }
}
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
