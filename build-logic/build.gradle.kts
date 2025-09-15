plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
        name = "Gradle Releases"
    }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        name = "JetBrains Compose Dev"
    }
}

// Dependencies required for the convention plugins themselves.
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    
    // AGP APIs for plugins
    implementation("com.android.tools.build:gradle-api:9.0.0-alpha05")
    
    // Hilt
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    
    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.13.4")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2025.09.00"))
    implementation("androidx.compose.runtime:runtime")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.8.2")
    
    // KSP - Using the correct plugin notation
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-1.0.20")
    
    // Add Maven repository for KSP if needed
    repositories {
        google()
        mavenCentral()
    }
    
    // Kotlin DSL
    implementation(gradleApi())
    implementation(localGroovy())
}

gradlePlugin {
    plugins {
            register("androidApplication") {
                id = "genesis.android.application"
                implementationClass =
                    "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
            }
            register("androidLibrary") {
                id = "genesis.android.library"
                implementationClass = "GenesisAndroidLibraryPlugin"
            }
            register("androidCompose") {
                id = "genesis.android.compose"
                implementationClass = "dev.aurakai.auraframefx.buildlogic.GenesisAndroidComposePlugin"
            }
        }
    }

