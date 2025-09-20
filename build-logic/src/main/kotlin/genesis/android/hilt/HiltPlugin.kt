package genesis.android.hilt

import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.google.dagger.hilt.android")
        target.pluginManager.apply("com.google.devtools.ksp")
        target.dependencies.add("implementation", "com.google.dagger:hilt-android:2.57.1")
        target.dependencies.add("ksp", "com.google.dagger:hilt-compiler:2.57.1")
    }
}

