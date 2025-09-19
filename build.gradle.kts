// Top-level build file for the ReGenesis project.
// Plugins applied here with 'apply false' are made available to all subprojects.
// Versions for these plugins should be managed in settings.gradle.kts (via pluginManagement),
// which should, in turn, reference versions from gradle/libs.versions.toml.

plugins {
    // Standard Android/Kotlin/etc. plugins made available to subprojects
    // Subprojects will apply these by ID (e.g., plugins { id("com.android.application") })
    // Convention plugins (like genesis.android.application) will typically apply these.
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("com.google.dagger.hilt.android") apply false
    id("com.google.devtools.ksp") apply false
    id("com.google.gms.google-services") apply false
    id("org.jetbrains.dokka") apply false
    id("com.diffplug.spotless") apply false

    // Your custom convention plugins from build-logic
    // These are also made available for subprojects to apply by ID.
    id("genesis.android.application") apply false
    id("genesis.android.library") apply false
    id("genesis.android.compose") apply false
    id("genesis.android.dagger.hilt") apply false
    id("genesis.android.native") apply false
    id("genesis.tasks.status") apply false
}

// No repositories block here; repositories are managed centrally in settings.gradle.kts.
// No dependencies block here; dependencies are managed by individual modules or convention plugins.
// All other configurations should be delegated to convention plugins or specific module build scripts.
