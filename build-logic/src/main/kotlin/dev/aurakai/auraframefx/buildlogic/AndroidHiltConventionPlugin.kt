package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidHiltConventionPlugin : Plugin<Project> {
    /**
     * Applies Hilt and KSP Gradle plugins to Android application or library modules.
     *
     * When the project has the `com.android.application` or `com.android.library` plugin applied,
     * this method applies `com.google.dagger.hilt.android` and `com.google.devtools.ksp`.
     * If neither Android plugin is present, no plugins are applied.
     */
    override fun apply(project: Project) = with(project) {
        pluginManager.withPlugin("com.android.application") {
            pluginManager.apply("com.google.dagger.hilt.android")
            pluginManager.apply("com.google.devtools.ksp")
        }
        pluginManager.withPlugin("com.android.library") {
            pluginManager.apply("com.google.dagger.hilt.android")
            pluginManager.apply("com.google.devtools.ksp")
        }
    }
}