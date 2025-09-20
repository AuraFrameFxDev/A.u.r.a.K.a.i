package dev.aurakai.auraframefx.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.getByType<CommonExtension<*, *, *, *, *, *>>().apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("compose-compiler-extension").get().toString()
                }

                dependencies {
                    val bom = platform(libs.findLibrary("androidx-compose-bom").get())
                    "implementation"(bom)
                    "androidTestImplementation"(bom)

                    "implementation"(libs.findBundle("compose-ui").get())
                    "debugImplementation"(libs.findBundle("compose-debug").get())
                }
            }
        }
    }
}
