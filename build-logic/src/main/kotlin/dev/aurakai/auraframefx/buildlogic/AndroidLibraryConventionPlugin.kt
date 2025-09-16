package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Standard Android library configuration for all Genesis Protocol modules
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    /**
     * Applies the Android library convention to the given Gradle project.
     *
     * Configures the project with the `com.android.library` plugin and sets standard library
     * defaults used across Genesis Protocol modules:
     * - compileSdk = 36
     * - defaultConfig: minSdk = 34, testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
     * - Java toolchain and compileOptions driven by the Gradle property `java.toolchain` (defaults to 24)
     *   and applied to both sourceCompatibility and targetCompatibility
     * - Kotlin JVM toolchain set from the same `java.toolchain` property
     * - Enables BuildConfig generation
     * - Includes Android resources in unit tests
     * - Excludes license META-INF resources (`AL2.0`, `LGPL2.1`) from packaging
     *
     * @param target The Gradle project to configure.
     */
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

                val toolchain = providers.gradleProperty("java.toolchain").orElse("24").get().toInt()
                val javaVer = JavaVersion.toVersion(toolchain)
                compileOptions {
                    sourceCompatibility = javaVer
                    targetCompatibility = javaVer
                }

                buildFeatures {
                    buildConfig = true
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            kotlinExtension.jvmToolchain(
                providers.gradleProperty("java.toolchain").orElse("24").get().toInt()
            )
        }
    }
}
