// ==== GENESIS PROTOCOL - ANDROID COMPOSE CONVENTION ====
// Compose-enabled Android library configuration

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    /**
     * Applies the Android Compose convention to the given Gradle project.
     *
     * This will:
     * - Apply the base "genesis.android.library" convention plugin.
     * - Apply the Kotlin Compose plugin "org.jetbrains.kotlin.plugin.compose".
     * - Configure the Android LibraryExtension to enable Compose build features.
     *
     * @param target The Gradle [Project] this plugin is being applied to.
     */
    override fun apply(target: Project) {
        with(target) {
            // Apply the base library convention first
            pluginManager.apply("genesis.android.library")

            // Try to configure as LibraryExtension first
            extensions.findByType(LibraryExtension::class.java)?.let {
                it.buildFeatures.compose = true
                it.composeOptions.kotlinCompilerExtensionVersion = "1.8.2"
            }
            // If not a library, try ApplicationExtension
            extensions.findByType(ApplicationExtension::class.java)?.let {
                it.buildFeatures.compose = true
                it.composeOptions.kotlinCompilerExtensionVersion = "1.8.2"
            }

            // Add Compose BOM to dependencies for version alignment
            dependencies {
                add("implementation", platform("androidx.compose:compose-bom:2025.09.00"))
            }
        }
    }
}
