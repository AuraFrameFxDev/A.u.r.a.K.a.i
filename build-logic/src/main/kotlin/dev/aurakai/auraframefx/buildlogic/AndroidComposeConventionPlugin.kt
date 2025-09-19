package dev.aurakai.auraframefx.buildlogic

import com.android.build.gradle.LibraryExtension
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.library") {
                val androidExtension = extensions.findByType(LibraryExtension::class.java)
                androidExtension?.buildFeatures?.compose = true
                androidExtension?.composeOptions?.kotlinCompilerExtensionVersion = "1.5.1"
                // Optionally configure other build features here
                // androidExtension?.buildFeatures?.buildConfig = true
                // androidExtension?.buildFeatures?.aidl = true
                dependencies {
                    val bom = platform("androidx.compose:compose-bom:2025.09.00")
                    add("implementation", bom)
                    add("androidTestImplementation", bom)
                    add("implementation", "androidx.compose.ui:ui")
                    add("implementation", "androidx.compose.ui:ui-graphics")
                    add("implementation", "androidx.compose.material3:material3")
                    add("implementation", "androidx.compose.ui:ui-tooling-preview")
                    add("debugImplementation", "androidx.compose.ui:ui-tooling")
                    add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
                    add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
                }
            }
            pluginManager.withPlugin("com.android.application") {
                val androidExtension = extensions.findByType(ApplicationExtension::class.java)
                androidExtension?.buildFeatures?.compose = true
                androidExtension?.composeOptions?.kotlinCompilerExtensionVersion = "1.5.1"
                // Optionally configure other build features here
                dependencies {
                    val bom = platform("androidx.compose:compose-bom:2025.09.00")
                    add("implementation", bom)
                    add("androidTestImplementation", bom)
                    add("implementation", "androidx.compose.ui:ui")
                    add("implementation", "androidx.compose.ui:ui-graphics")
                    add("implementation", "androidx.compose.material3:material3")
                    add("implementation", "androidx.compose.ui:ui-tooling-preview")
                    add("debugImplementation", "androidx.compose.ui:ui-tooling")
                    add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
                    add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
                }
            }
        }
    }
}
