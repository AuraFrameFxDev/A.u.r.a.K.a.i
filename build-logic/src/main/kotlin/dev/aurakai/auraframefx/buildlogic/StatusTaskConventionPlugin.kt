package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar

/**
 * Plugin for standardizing status tasks across all modules.
 * Applies a consistent naming and grouping convention for module status tasks.
 */
class StatusTaskConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Remove any existing status tasks to avoid duplicates
        project.tasks.findByName("status")?.let { task ->
            project.tasks.remove(task)
        }

        // Create a standard status task for the module
        project.tasks.register("status") {
            group = "aegenesis"
            description = "Displays the status of this module"
            
            val moduleName = project.name.replace("-", " ").split(" ").joinToString(" ") { 
                it.replaceFirstChar { char -> char.uppercase() } 
            }
            
            doLast {
                val javaVersion = JavaVersion.current()
                println("\uD83D\uDCE6 ${moduleName.uppercase()} MODULE - Ready (Java ${javaVersion.majorVersion})")
            }
        }
    }

    companion object {
        // Helper function to get a consistent status task name
        fun getStatusTaskName(project: Project): String {
            return "${project.name}Status"
        }
    }
}
