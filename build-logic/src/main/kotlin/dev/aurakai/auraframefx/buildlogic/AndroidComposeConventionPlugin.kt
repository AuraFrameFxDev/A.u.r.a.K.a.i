package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Compose-enabled Android library configuration
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.withId("com.android.base") {
                val extension = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>
                extension.apply {
                    buildFeatures { compose = true }
                }
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
