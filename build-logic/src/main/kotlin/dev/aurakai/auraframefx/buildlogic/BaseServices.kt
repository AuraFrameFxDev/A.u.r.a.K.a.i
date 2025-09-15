package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.Project
import com.android.build.api.dsl.ApplicationExtension

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
