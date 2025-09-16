package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Hilt dependency injection configuration
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", "com.google.dagger:hilt-android:2.57.1")
                add("ksp", "com.google.dagger:hilt-compiler:2.57.1")
                
                // Test dependencies
                add("testImplementation", "com.google.dagger:hilt-android-testing:2.57.1")
                add("androidTestImplementation", "com.google.dagger:hilt-android-testing:2.57.1")
                add("kspTest", "com.google.dagger:hilt-compiler:2.57.1")
                add("kspAndroidTest", "com.google.dagger:hilt-compiler:2.57.1")
            }
        }
    }
}
