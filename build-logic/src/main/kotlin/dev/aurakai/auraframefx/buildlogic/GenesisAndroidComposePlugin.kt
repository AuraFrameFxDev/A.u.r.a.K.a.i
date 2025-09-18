package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class GenesisAndroidComposePlugin : Plugin<Project> {
    /**
     * Applies Android library and Compose conventions to the given Gradle project.
     *
     * Enables Jetpack Compose for the project's Android library module, sets Java and Kotlin
     * compilation targets to Java/JVM 24, configures minSdk to 34, and adds common Compose
     * dependencies (using the Compose BOM plus core UI, tooling, foundation, Material3,
     * Activity Compose, and lifecycle ViewModel Compose integrations). Also sets Kotlin compiler
     * free arguments for JVM default methods and opt-in annotations.
     */
    override fun apply(target: Project) {
        with(target) {
            // Apply the Android and Compose compiler plugins
            pluginManager.apply(LibraryPlugin::class)

            // Configure Android extension
            configure<com.android.build.gradle.LibraryExtension> {
                buildFeatures.compose = true


                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }

                defaultConfig {
                    minSdk = 34
                }
            }

            // Configure Kotlin options
            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
                    freeCompilerArgs.addAll(
                        "-Xjvm-default=all",
                        "-opt-in=kotlin.RequiresOptIn"
                    )
                }
            }

            // Add Compose dependencies
            dependencies {
                val composeBom = platform("androidx.compose:compose-bom:2025.09.00")
                "implementation"(composeBom)
                "androidTestImplementation"(composeBom)

                // Core Compose dependencies
                "implementation"("androidx.compose.ui:ui")
                "implementation"("androidx.compose.ui:ui-tooling-preview")
                "debugImplementation"("androidx.compose.ui:ui-tooling")
                "implementation"("androidx.compose.foundation:foundation")
                "implementation"("androidx.compose.material3:material3")
                "implementation"("androidx.activity:activity-compose:1.11.0")

                // Integration with ViewModels
                "implementation"("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.3")
            }
        }
    }
}
