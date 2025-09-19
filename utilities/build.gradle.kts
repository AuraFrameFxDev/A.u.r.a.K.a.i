import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    alias(libs.plugins.android.library)       // Correct Android plugin for a library, applied first
    // alias(libs.plugins.jetbrains.kotlin.android) // REMOVED
    alias(libs.plugins.hilt)                  // Hilt applied after Android
    alias(libs.plugins.ksp)                   // KSP applied after Hilt
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20" // Keep, version managed here
    id("org.jetbrains.dokka") version "2.0.0"                     // Keep
    id("com.diffplug.spotless") version "7.2.1"                 // Keep
}

android {
    namespace = "dev.aurakai.auraframefx.utilities" // Changed namespace to .utilities
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // Added for library module
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(24))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            // proguardFiles(...) removed as it's typically for applications
        }
    }

    dependencies {
        // Module dependencies
        api(project(":list"))

        // Kotlin and coroutines
        implementation(libs.kotlin.stdlib.jdk8)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.serialization.json)

        // Hilt dependency injection
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Utilities
        implementation(libs.commons.io)
        implementation(libs.commons.compress)
        implementation(libs.xz)

        // YukiHook and LSPosed (corrected coordinates)


        // Force newer AndroidX versions to override Hilt's old dependencies
        implementation(libs.androidx.core.ktx)
        implementation(libs.bundles.lifecycle)

        // Testing dependencies
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
        testImplementation(kotlin("test"))


        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            // Configure any Kotlin compile options if needed
            // For example: compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }

        tasks.register("utilitiesStatus") {
            group = "aegenesis"
            description = "Checks the status of the Utilities module"
            doLast {
                println("📦 UTILITIES MODULE - Ready (Java 24)")
            }
        }
    } // End of android block

// Added standard test configuration for JUnit 5
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
