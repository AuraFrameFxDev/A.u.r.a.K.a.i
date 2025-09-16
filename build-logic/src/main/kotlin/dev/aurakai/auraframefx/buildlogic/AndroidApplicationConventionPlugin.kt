package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Standard Android application configuration
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    /**
     * Applies Android application and Kotlin Android plugins and configures the Android Application extension
     * with a standard set of defaults for an application module.
     *
     * Configurations performed:
     * - Applies plugins: "com.android.application" and "org.jetbrains.kotlin.android".
     * - Sets compileSdk to 36 and defaultConfig (minSdk 34, targetSdk 36, AndroidJUnitRunner, support vector drawables).
     * - Defines release and debug buildTypes (release: minification + proguard files; debug: debuggable with
     *   applicationId and version name suffixes).
     * - Reads the Gradle property `java.toolchain` (defaults to 24) and uses it to set Java compileOptions'
     *   source/target compatibility and the Kotlin JVM toolchain.
     * - Enables buildConfig generation, includes Android resources for unit tests, and excludes
     *   "/META-INF/{AL2.0,LGPL2.1}" from packaging resources.
     */
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

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

            kotlinExtension.jvmToolchain(
                providers.gradleProperty("java.toolchain").orElse("24").get().toInt()
            )
        }
    }
}
