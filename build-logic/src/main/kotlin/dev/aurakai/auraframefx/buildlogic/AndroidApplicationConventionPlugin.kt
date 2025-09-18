package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Standard Android application configuration
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    /**
     * Applies the Android application Gradle plugin and configures the Android ApplicationExtension
     * with a standard set of defaults for application modules.
     *
     * Configured settings:
     * - Applies plugin "com.android.application".
     * - compileSdk set to 36.
     * - defaultConfig: minSdk 34, targetSdk 36, testInstrumentationRunner set to
     *   "androidx.test.runner.AndroidJUnitRunner", and support for vector drawables enabled.
     * - buildTypes:
     *   - release: code minification enabled with the default Android optimize ProGuard file plus
     *     "proguard-rules.pro".
     *   - debug: debuggable, adds an applicationIdSuffix ".debug" and versionNameSuffix "-DEBUG".
     * - Java compileOptions source/target compatibility derived from the Gradle property
     *   "java.toolchain" (defaults to "24"); value is converted to an integer and mapped to JavaVersion.
     * - Enables buildConfig generation.
     * - Includes Android resources during unit tests.
     * - Excludes "/META-INF/{AL2.0,LGPL2.1}" from packaged resources.
     *
     * Side effects: applies a plugin to the project and mutates the Android extension configuration.
     */
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 34
                    targetSdk = 36
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                    debug {
                        isDebuggable = true
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-DEBUG"
                    }
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

            // JVM toolchain is configured in the Android block's compileOptions
        }
    }
}
