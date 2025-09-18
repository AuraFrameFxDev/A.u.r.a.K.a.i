package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Standard Android library configuration for all modules.
 * 
 * Configures:
 * - Android library plugin
 * - Java 17 compatibility
 * - Kotlin JVM toolchain
 * - Basic packaging options
 * - Test instrumentation runner
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply Android library plugin
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            // Configure Android extension
            extensions.configure<LibraryExtension> {
                // SDK Configuration
                compileSdk = 36
                
                defaultConfig {
                    minSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    
                    // Enable vector drawable support
                    vectorDrawables.useSupportLibrary = true
                }

                // Java/Kotlin compatibility
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                    isCoreLibraryDesugaringEnabled = true
                }

                // Build features
                buildFeatures {
                    buildConfig = true
                    viewBinding = true
                    dataBinding = true
                }

                // Packaging options
                packaging {
                    resources {
                        excludes += setOf(
                            "/META-INF/{AL2.0,LGPL2.1}",
                            "META-INF/AL2.0",
                            "META-INF/LGPL2.1"
                        )
                        pickFirsts += "**/META-INF/*.version"
                    }
                }

                // Test options
                testOptions {
                    unitTests.all {
                        it.useJUnitPlatform()
                        it.testLogging {
                            events("passed", "skipped", "failed")
                        }
                    }
                }
            }

            // Configure Kotlin
            configureKotlin()
        }
    }

    private fun Project.configureKotlin() {
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
                freeCompilerArgs.addAll(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xjvm-default=all"
                )
            }
        }
        // Configure Java compilation
        tasks.withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()
        }
    }
}
