package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configures Hilt for Android applications and libraries.
 * Applies the Hilt Android plugin and KSP plugin for annotation processing.
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            // Apply Hilt and KSP plugins for both application and library modules
            withPlugin("com.android.application") {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
                configureHiltDependencies()
            }
            withPlugin("com.android.library") {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
                configureHiltDependencies()
            }
        }
    }

    private fun Project.configureHiltDependencies() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        
        dependencies {
            // Hilt Core
            add("implementation", libs.findLibrary("hilt.android").get())
            add("ksp", libs.findLibrary("hilt.compiler").get())
            
            // Hilt testing
            add("testImplementation", libs.findLibrary("hilt.android.testing").get())
            add("androidTestImplementation", libs.findLibrary("hilt.android.testing").get())
            add("kspTest", libs.findLibrary("hilt.compiler").get())
            add("kspAndroidTest", libs.findLibrary("hilt.compiler").get())
        }
    }
}