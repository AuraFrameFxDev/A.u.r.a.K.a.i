import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()


plugins {
    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.aurakai.auraframefx.utilities" // Changed namespace to .utilities
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // Added for library module
    }

    // Java toolchain and compile options are now handled by the convention plugin.

    buildTypes {
        release {
            isMinifyEnabled = false
            // proguardFiles(...) removed as it's typically for applications

        } // End of android block

        dependencies { // MOVED to root level
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

            // Xposed & YukiHook & LSPosed
            implementation(libs.xposed.api)
            implementation(libs.yukihook.api)
            ksp(libs.yukihook.ksp)
            implementation(libs.yukihookapi.api.lsposed)
            ksp(libs.yukihookapi.processor.lsposed)

            // Force newer AndroidX versions to override Hilt's old dependencies
            implementation(libs.androidx.core.ktx)
            implementation(libs.bundles.lifecycle)

            // Testing dependencies
            testImplementation(libs.junit.jupiter.api)
            testRuntimeOnly(libs.junit.jupiter.engine)
            testImplementation(libs.mockk)
            testImplementation(libs.kotlin.test) // Use version catalog alias for kotlin-test
        }

// MOVED to root level and Updated
        tasks.register("utilitiesStatus") {
            group = "aegenesis"
            description = "Checks the status of the Utilities module"
            doLast {
                println("📦 UTILITIES MODULE - Ready (Java 25, JVM 25)") // Updated
            }
        }

// MOVED to root level - Added standard test configuration for JUnit 5
        tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}
