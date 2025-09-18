package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Standard Android application configuration for all application modules.
 * 
 * Configures:
 * - Android application plugin
 * - Java 17 compatibility
 * - Kotlin JVM toolchain
 * - Build types (debug/release)
 * - Build features (BuildConfig, view binding, data binding)
 * - Packaging options
 * - Test configuration
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply Android application and Kotlin plugins
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            // Configure Android extension
            extensions.configure<ApplicationExtension> {
                // SDK Configuration
                compileSdk = 36
                
                defaultConfig {
                    minSdk = 34
                    targetSdk = 36
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    
                    // Enable vector drawable support
                    vectorDrawables.useSupportLibrary = true
                    
                    // Enable multidex
                    multiDexEnabled = true
                }

                // Build types
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                    debug {
                        isDebuggable = true
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-DEBUG"
                        
                        // Enable test coverage in debug builds
                        enableUnitTestCoverage = true
                        enableAndroidTestCoverage = true
                    }
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
                    animationsDisabled = true
                    execution = "ANDROIDX_TEST_ORCHESTRATOR"
                }

                // Configure Java compatibility
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                }
                
                // Kotlin JVM options will be configured after the Kotlin plugin is applied
            }

            // Configure Kotlin toolchain
            configureKotlin()
        }
    }

    private fun Project.configureKotlin() {
        extensions.findByName("kotlin")?.let { ext ->
            if (ext is org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension) {
                ext.jvmToolchain(17)
            }
        }
    }
}
