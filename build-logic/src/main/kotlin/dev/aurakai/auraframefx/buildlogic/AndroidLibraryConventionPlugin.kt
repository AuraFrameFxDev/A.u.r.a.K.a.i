package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
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
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_24)
                }
            }
        }
    }
}
