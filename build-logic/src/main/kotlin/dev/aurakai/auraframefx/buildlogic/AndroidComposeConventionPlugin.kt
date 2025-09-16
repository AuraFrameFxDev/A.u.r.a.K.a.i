package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Compose-enabled Android library configuration
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    /**
     * Applies Compose-focused configuration to the target Gradle project.
     *
     * Configures the Android library extension named "android" (cast to CommonExtension) to enable
     * Jetpack Compose and set the Kotlin compiler extension version, and adds a standard set of
     * Compose dependencies (including the Compose BOM, runtime, material3, tooling, and test libs).
     *
     * Side effects:
     * - Enables buildFeatures.compose = true.
     * - Sets composeOptions.kotlinCompilerExtensionVersion = "1.8.2".
     * - Adds the Compose BOM to implementation and androidTestImplementation.
     * - Adds common Compose implementation, debugImplementation, and androidTestImplementation deps.
     *
     * Notes:
     * - This function expects an "android" extension to be present and castable to CommonExtension;
     *   if absent or not castable, a runtime exception will be thrown.
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
                    kotlinCompilerExtensionVersion = project.libs.findVersion("compose-compiler-extension").get().toString()
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
