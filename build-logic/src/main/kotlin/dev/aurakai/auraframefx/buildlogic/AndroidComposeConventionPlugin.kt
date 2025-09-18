package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configures Compose for Android applications and libraries.
 * 
 * This plugin:
 * - Enables Compose build features
 * - Configures Compose compiler options
 * - Adds required Compose dependencies using BOM
 * - Sets up testing and debugging utilities
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Only apply to Android modules
            pluginManager.withPlugin("com.android.base") {
                // Get the version catalog
                extensions.getByType<VersionCatalogsExtension>().named("libs")
                
                // Configure Compose for both application and library modules
                extensions.getByType(CommonExtension::class).apply {
                    // Enable Compose build features
                    buildFeatures.compose = true
                    
                    // Configure Compose options
                    composeOptions {
                        // Use the bleeding-edge Compose compiler version
                        kotlinCompilerExtensionVersion = "2.2.20"
                    }
                    
                    // Add Compose dependencies with safe access to version catalog
                    afterEvaluate {
                        dependencies {
                            try {
                                // Compose BOM (Bill of Materials) - BLEEDING EDGE
                                val composeBom = platform("androidx.compose:compose-bom:2025.09.00")
                                
                                // Core Compose dependencies
                                add("implementation", composeBom)
                                add("implementation", "androidx.compose.ui:ui")
                                add("implementation", "androidx.compose.ui:ui-graphics")
                                add("implementation", "androidx.compose.ui:ui-tooling-preview")
                                add("implementation", "androidx.compose.material3:material3")
                                
                                // Debug and testing dependencies
                                add("debugImplementation", "androidx.compose.ui:ui-tooling")
                                add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
                                
                                // AndroidX Activity Compose for compatibility
                                add("implementation", "androidx.activity:activity-compose:1.9.0")
                                
                                // Testing
                                add("androidTestImplementation", composeBom)
                                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
                            } catch (e: Exception) {
                                logger.warn("Failed to add some Compose dependencies: ${e.message}")
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Extension function to get the version catalog for the project.
 */
internal val Project.libs: org.gradle.api.artifacts.VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
