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
     * Configures a Gradle project for Jetpack Compose and adds common Compose dependencies.
     *
     * Enables Compose in the Android extension, sets the Kotlin compiler extension version from the
     * project's version catalog (libs), and adds the Compose BOM plus typical UI, tooling, and test
     * dependencies to the project's dependency configurations.
     *
     * Note: this function mutates the project's Android extension and dependency configurations.
     * It uses `project.libs.findVersion("compose-compiler-extension").get()` to resolve the compiler
     * extension version and will throw if that version entry is not present in the catalog.
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
