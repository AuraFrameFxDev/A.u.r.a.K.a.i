// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
}

// NO explicit repositories block here; it will use settings.gradle.kts

// Dependencies required for writing the convention plugins themselves.
// Using explicit string coordinates as a workaround for libs accessor issue.
dependencies {
    implementation("com.android.tools.build:gradle:9.0.0-alpha02") // Changed to 9.0.0-alpha02
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.gms:google-services:4.4.3")

    // Re-enable compileOnly dependencies with explicit coordinates
    compileOnly("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    compileOnly("org.openapitools:openapi-generator-gradle-plugin:7.15.0")
    compileOnly("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
}

// Register your custom convention plugins
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "GenesisAndroidLibraryPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.dagger.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidNativeConventionPlugin"
        }
    }
}
