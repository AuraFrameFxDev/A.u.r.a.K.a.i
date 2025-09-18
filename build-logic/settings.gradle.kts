import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Retrieve the version catalog extension
            val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
            
            // Example usage:
            dependencies {
                // Apply a dependency from the catalog
                implementation(libs.androidx.core)
                
                // Use a version from the catalog
                // For a version reference like `libs.versions.composeCompiler`, use .get()
                // val composeVersion = libs.versions.composeCompiler.get()
            }
        }
    }
}
