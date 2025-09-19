package dev.aurakai.auraframefx

import com.android.build.gradle.LibraryExtension

plugins {
    id("com.android.library")
}

extensions.configure<LibraryExtension>("android") {
    buildFeatures.compose = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
        freeCompilerArgs.addAll(
            listOf(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
            )
        )
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2025.09.00")
    add("implementation", composeBom)
    add("androidTestImplementation", composeBom)
    add("implementation", "androidx.compose.ui:ui-tooling-preview")
    add("implementation", "androidx.compose.material3:material3")
    add("implementation", "androidx.navigation:navigation-compose:2.9.4")
    add("implementation", "androidx.core:core-ktx:1.17.0")
    add("debugImplementation", "androidx.compose.ui:ui-tooling")
    add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
}
