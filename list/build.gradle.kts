import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply convention plugins
    id("genesis.android.library")
    id("genesis.android.compose")
    id("genesis.android.dagger.hilt")

    // KSP for annotation processing
    alias(libs.plugins.ksp)

    // Code formatting
    id("com.diffplug.spotless") version "7.2.1"

    // Kotlin plugins
}

val libs = the<LibrariesForLibs>()

android {
    namespace = "dev.aurakai.auraframefx.list"

    // Configure Java toolchain
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    kotlin {
        jvmToolchain(24) // Ensuring Kotlin compiles with JDK 24
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)  // Changed to JVM 17 for compatibility
            freeCompilerArgs.addAll(
                listOf(
                    "-Xjvm-default=all",
                    "-opt-in=kotlin.RequiresOptIn"
                )
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    // Kotlin Standard Library
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

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

tasks.withType<Test> {
    useJUnitPlatform()
}

// Status task for CI/CD visibility
tasks.register("listStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 LIST MODULE - Ready (Java 24)") }
}

// Configure Spotless for code formatting
spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
