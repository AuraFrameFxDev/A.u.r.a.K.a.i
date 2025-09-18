 import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    `kotlin-dsl`
    alias(libs.plugins.ksp)
    id("com.diffplug.spotless") version "7.2.1"
    kotlin("plugin.serialization") version "2.0.0"
}

val libs = the<LibrariesForLibs>()

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

// Configure Kotlin Multiplatform
configure<KotlinMultiplatformExtension> {
    jvmToolchain(24)
    
    jvm {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
                    freeCompilerArgs.addAll(
                        "-Xjvm-default=all",
                        "-opt-in=kotlin.RequiresOptIn"
                    )
                }
            }
        }
    }
}

// Configure KSP
ksp {
    // Add any KSP-specific configurations here
}

dependencies {
    // Kotlin Standard Library
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.serialization.json)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    
    // Hilt for JVM


    // Testing
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.mockk)
    testImplementation(kotlin("test"))
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
    
    // Logging for tests
    testRuntimeOnly(libs.slf4j.simple)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("listStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 LIST MODULE - Ready (Java 24)") }
}
