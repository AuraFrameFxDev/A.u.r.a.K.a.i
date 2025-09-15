// AOSP-ReGenesis/build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

group = "dev.aurakai.auraframefx.buildlogic"

// Repositories required for resolving external dependencies.
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

// Configure test execution (temporarily disabled for bleeding-edge compatibility)
tasks.test {
    useJUnitPlatform()
    enabled = false // Disable until AGP 9.0 test compatibility is resolved
}

// Disable test compilation temporarily
tasks.compileTestKotlin {
    enabled = false
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        // This is the new registration for the Hilt convention plugin
        register("androidHilt") {
            id = "genesis.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "AndroidNativeConventionPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:9.0.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
