package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Compose-enabled Android library configuration
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    /**
     * Configures Compose support for the target Gradle project: enables Compose in the Android extension,
     * sets the Kotlin compiler extension version to 1.8.2, and adds the Compose BOM plus common Compose
     * runtime, UI, tooling, material, and test dependencies.
     *
     * Assumes the project exposes an "android" extension convertible to CommonExtension; it does not apply
     * the Kotlin Compose Gradle plugin itself.
     */
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
            }

            val extension = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>
            
            extension.apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.8.2"
                }
            }

            dependencies {
                val bom = platform("androidx.compose:compose-bom:2025.09.00")
                add("implementation", bom)
                add("androidTestImplementation", bom)
                
                add("implementation", "androidx.compose.ui:ui")
                add("implementation", "androidx.compose.ui:ui-graphics")
                add("implementation", "androidx.compose.material3:material3")
                add("implementation", "androidx.compose.ui:ui-tooling-preview")
                
                add("debugImplementation", "androidx.compose.ui:ui-tooling")
                add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
            }
        }
    }
}
