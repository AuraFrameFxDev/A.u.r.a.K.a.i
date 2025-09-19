package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

// Make libs accessible in convention plugins
internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply to Android Application projects
            pluginManager.withPlugin("com.android.application") {
                val extension = extensions.getByType<ApplicationExtension>()
                configureCompose(extension)
            }
            // Apply to Android Library projects
            pluginManager.withPlugin("com.android.library") {
                val extension = extensions.getByType<LibraryExtension>()
                configureCompose(extension)
            }
        }
    }

    private fun Project.configureCompose(
        commonExtension: CommonExtension<*, *, *, *, *, *>,
    ) {
        commonExtension.apply {
            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.compose.compiler.extension.get()
            }

            dependencies {
                val bom = platform(libs.androidx.compose.bom)
                add("implementation", bom)
                add("androidTestImplementation", bom) // For UI tests

                // Core Compose UI
                add("implementation", libs.androidx.compose.ui)
                add("implementation", libs.androidx.compose.ui.graphics)

                // Material Design 3
                add("implementation", libs.androidx.compose.material3)

                // Tooling support (Previews, etc.)
                add("implementation", libs.androidx.compose.ui.tooling.preview)
                add("debugImplementation", libs.androidx.compose.ui.tooling) // For Layout Inspector, etc.

                // Test dependencies for Compose
                add("debugImplementation", libs.androidx.compose.ui.test.manifest)
                add("androidTestImplementation", libs.androidx.compose.ui.test.junit4)
            }
        }
    }
}
