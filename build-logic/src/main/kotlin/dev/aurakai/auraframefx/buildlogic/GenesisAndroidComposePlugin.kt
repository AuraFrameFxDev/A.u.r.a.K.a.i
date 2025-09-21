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
     * Applies Android library and Jetpack Compose conventions to the given Gradle project.
     *
     * Configures the target project by applying the Android library plugin, enabling Jetpack
     * Compose for the library extension, setting Java source/target compatibility and Kotlin
     * jvmTarget to Java/JVM 24, and setting the library minSdk to 34. It also adds common
     * Compose dependencies using the Compose BOM (version 2025.09.00) and core libraries
     * (UI, tooling-preview, tooling (debug), foundation, material3), activity-compose (1.11.0),
     * and lifecycle-viewmodel-compose (2.9.3). Kotlin compiler free arguments
     * `-Xjvm-default=all` and `-opt-in=kotlin.RequiresOptIn` are added to KotlinCompile tasks.
     *
     * This function mutates the Gradle Project by applying plugins, configuring the Android
     * library extension and Kotlin compilation tasks, and adding dependencies.
     */
    override fun apply(target: Project) {
        with(target) {
            // Apply the Android and Compose compiler plugins
            pluginManager.apply(LibraryPlugin::class)

            // Configure Android extension
            configure<com.android.build.gradle.LibraryExtension> {
                buildFeatures.compose = true


                compileOptions {
            sourceCompatibility = JavaVersion.VERSION_24
            targetCompatibility = JavaVersion.VERSION_24
                }

                defaultConfig {
                    minSdk = 34
                }
            }

            // Configure Kotlin options
            tasks.withType(KotlinCompile::class).configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
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
