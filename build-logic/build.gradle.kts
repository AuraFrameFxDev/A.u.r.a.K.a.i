// build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
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
    // Core build plugins
    implementation("com.android.tools.build:gradle:9.0.0-alpha05")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.8.2")
    
    // Gradle API
    implementation(gradleApi())
    
    // Testing libraries for the plugins themselves
    implementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    implementation("org.junit.jupiter:junit-jupiter-params:5.13.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    implementation("io.mockk:mockk-android:1.14.5")
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidHiltConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.AndroidNativeConventionPlugin"
        }
    }
}
