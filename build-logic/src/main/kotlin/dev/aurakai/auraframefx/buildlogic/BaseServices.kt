package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

// Data class representing build features
data class GradleBuildFeatures(
    val viewBinding: Boolean = false,
    val compose: Boolean = false,
    val dataBinding: Boolean = false
)

// Function to apply base services to a project
fun applyBaseServices(project: Project, gradleBuildFeatures: GradleBuildFeatures) {
    project.extensions.findByType(ApplicationExtension::class.java)?.buildFeatures?.apply {
        viewBinding = gradleBuildFeatures.viewBinding
        compose = gradleBuildFeatures.compose
        dataBinding = gradleBuildFeatures.dataBinding
    }
    // Add other base service setup here
}
