package genesis.android.compose

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        target.pluginManager.apply("androidx.compose.compiler")
        target.extensions.findByType(BaseExtension::class.java)?.apply {
            buildFeatures.apply {
                this::class.java.getMethod("setCompose", Boolean::class.java).invoke(this, true)
            }
            val composeOptions = this::class.java.getMethod("getComposeOptions").invoke(this)
            composeOptions.javaClass.getMethod("setKotlinCompilerExtensionVersion", String::class.java)
                .invoke(composeOptions, target.findProperty("composeCompilerVersion") as? String ?: "1.5.12")
        }
    }
}

