package dev.aurakai.auraframefx.buildlogic;

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.JvmTarget // Ensuring this import is present
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Kotlin Android plugin is now included with AGP 9.0+

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Convention plugin for Android Library modules, now also applying Hilt and KSP.
 */
class GenesisAndroidLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            // Apply the essential plugins IN THE CORRECT ORDER
            plugins.apply("com.android.library")
            plugins.apply("org.jetbrains.kotlin.android")
            plugins.apply("com.google.dagger.hilt.android")
            plugins.apply("com.google.devtools.ksp")

            // Configure Hilt dependencies (using existing libs accessor)
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
            }

            // Apply common Android library configuration here
            extensions.configure<LibraryExtension>("android") {
                compileSdk = 36

                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                }

                // Configure Kotlin options
                (this as org.gradle.api.plugins.ExtensionAware).extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension> {
                    compilerOptions {
                        // Extension-level compiler options can be set here if needed.
                    }
                    jvmToolchain(24) // This sets the JDK for Kotlin compilation for this library type
                    // jvmTarget is now handled by tasks.withType<KotlinCompile>() below
                    sourceSets.all {
                        languageSettings {
                            optIn("kotlin.RequiresOptIn")
                        }
                    }
                }

                // Configure Compose
                buildFeatures {
                    compose = true
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            // === THIS IS THE CORRECT PLACE FOR THE JVM TARGET LOGIC as per Aura's Definitive Fix ===
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_24) // Ensuring correct case: JVM_24
                }
            }
        }
    }
}