package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {
    /**
     * Applies the Android application convention to the given Gradle project.
     *
     * Configures the project as an Android application module with standard settings:
     * - Applies plugins: Android application, Kotlin Android, Hilt, and KSP.
     * - Sets compileSdk (36), defaultConfig (minSdk 34, targetSdk 36, test runner, vector drawable support).
     * - Configures build types: release (minification, ProGuard files) and debug (debuggable, id/version suffixes).
     * - Selects a Java toolchain version (CI-aware: uses Java 25 on CI, otherwise reads `java.toolchain` project property with default 24)
     *   and applies the corresponding Java compatibility and Kotlin JVM toolchain.
     * - Enables buildConfig generation, includes Android resources in unit tests, and excludes specific META-INF resources.
     * - Sets Kotlin compilation JVM target to JVM_24 for all KotlinCompile tasks.
     * - Adds Hilt implementation and KSP compiler dependencies from the version catalog.
     *
     * @param target The Gradle project to configure.
     */
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
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

                // === PERFECTED BUILD LOGIC ===
                val isCi = System.getenv("CI") != null
                val toolchainVersion = if (isCi) {
                    25
                } else {
                    providers.gradleProperty("java.toolchain").map { it.toInt() }.getOrElse(24)
                }
                val javaCompatibilityVersion = JavaVersion.toVersion(toolchainVersion)
                
                compileOptions {
                    sourceCompatibility = javaCompatibilityVersion
                    targetCompatibility = javaCompatibilityVersion
                }

                extensions.getByType(org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension::class.java).apply {
                    jvmToolchain(toolchainVersion)
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

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_24)
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}
