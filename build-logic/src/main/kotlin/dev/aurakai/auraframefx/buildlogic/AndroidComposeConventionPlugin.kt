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
     * Applies Compose conventions to an Android project.
     *
     * Configures the project's Android extension to enable Jetpack Compose and set the Kotlin
     * compiler extension version, and adds a curated set of Compose dependencies (including the
     * Compose BOM, runtime/UI, material3, tooling, and test artifacts) to the appropriate
     * configurations.
     *
     * Side effects:
     * - Enables buildFeatures.compose = true.
     * - Sets composeOptions.kotlinCompilerExtensionVersion = "1.8.2".
     * - Adds the Compose BOM (androidx.compose:compose-bom:2025.09.00) to `implementation` and
     *   `androidTestImplementation`, and adds commonly used Compose libraries to `implementation`,
     *   `debugImplementation`, and `androidTestImplementation`.
     *
     * Note:
     * - This function expects the project to have an "android" extension that can be cast to
     *   CommonExtension<*, *, *, *, *, *>. If the extension is missing or not compatible, a runtime
     *   ClassCastException/NoSuchElementException may occur.
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
