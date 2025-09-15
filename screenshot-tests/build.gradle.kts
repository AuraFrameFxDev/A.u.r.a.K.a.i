// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
    base
    id("genesis.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ksp)

}

android {
    namespace = "dev.aurakai.screenshottests"
}

