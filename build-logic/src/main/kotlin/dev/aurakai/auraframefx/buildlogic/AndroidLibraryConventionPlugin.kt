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
    /**
     * Applies Android library and Kotlin Android conventions to the given Gradle project.
     *
     * Configures the Android Library extension and Kotlin compilation defaults:
     * - Applies the "com.android.library" and "org.jetbrains.kotlin.android" plugins.
     * - Sets compileSdk to 36 and defaultConfig.minSdk to 34, with a test instrumentation runner and consumer ProGuard rules.
     * - Disables minification for the release build type.
     * - Reads the `java.toolchain` Gradle property (defaults to 21) to determine the Java toolchain version and uses it for Java source/target compatibility.
     * - Configures the Kotlin Android project's JVM toolchain to the same toolchain version.
     * - Sets all KotlinCompile tasks' JVM target to JVM_24.
     *
     * This function mutates the target Project's plugins, extensions, and tasks.
     */
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
                        isMinifyEnabled = false // Libraries shouldn't typically minify themselves
                    }
                }

                val toolchainVersion = providers.gradleProperty("java.toolchain").orElse("21").get().toInt()
                val javaCompatibilityVersion = JavaVersion.toVersion(toolchainVersion)

                compileOptions {
                    sourceCompatibility = javaCompatibilityVersion
                    targetCompatibility = javaCompatibilityVersion
                }

                // Configure Kotlin JVM toolchain
                extensions.getByType(org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension::class.java).apply {
                    jvmToolchain(toolchainVersion)
                }
            }

            // Configure Kotlin JVM target
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_24)
                }
            }
        }
    }
}
