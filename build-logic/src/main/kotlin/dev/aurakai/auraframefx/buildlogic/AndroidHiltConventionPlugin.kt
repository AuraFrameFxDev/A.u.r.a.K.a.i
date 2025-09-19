package dev.aurakai.auraframefx.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidHiltConventionPlugin : Plugin<Project> {
    /**
     * This plugin is DEPRECATED.
     * Its functionality (applying Hilt and KSP) has been moved to
     * AndroidApplicationConventionPlugin and GenesisAndroidLibraryPlugin.
     * Modules should no longer apply this convention plugin (dev.aurakai.auraframefx.buildlogic.android.hilt).
     */
    override fun apply(project: Project) = with(project) {
        // This plugin's functionality (applying Hilt and KSP) has been moved to
        // AndroidApplicationConventionPlugin and GenesisAndroidLibraryPlugin.
        // This convention plugin (dev.aurakai.auraframefx.buildlogic.android.hilt)
        // should ideally no longer be applied by modules.
        // Leaving this as a no-op for now to avoid breaking existing module plugin blocks
        // that might still reference it via alias(libs.plugins.hilt).
        // Consider removing alias(libs.plugins.hilt) from module plugin blocks.
        project.logger.warn(
            "The convention plugin 'dev.aurakai.auraframefx.buildlogic.android.hilt' " +
            "is deprecated and its functionality has been merged into the main Android " +
            "application/library convention plugins. Please remove its application from your modules."
        )
    }
}
