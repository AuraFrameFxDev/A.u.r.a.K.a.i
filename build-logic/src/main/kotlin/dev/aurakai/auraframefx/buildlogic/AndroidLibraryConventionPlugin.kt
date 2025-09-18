package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Standard Android library configuration for all Genesis Protocol modules.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 36
                defaultConfig {
                    minSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                // Use a standard Java version (17) supported by Android.
                // jvmToolchain will set both source/target compatibility automatically.
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                }

                // BuildConfig generation is enabled by default in recent AGP versions.
                // testOptions { unitTests { isIncludeAndroidResources = true } } is deprecated.

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            // Set the Kotlin toolchain globally for the project (if needed)
            extensions.findByName("kotlin")?.let { ext ->
                if (ext is org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension) {
                    ext.jvmToolchain(24)
                }
            }
        }
    }
}
