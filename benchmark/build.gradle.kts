plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
}

android {
    namespace = "dev.aurakai.auraframefx.benchmark"
    compileSdk = 36

    defaultConfig {
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] =
            "EMULATOR,LOW_BATTERY,DEBUGGABLE"
        testInstrumentationRunnerArguments["android.experimental.self-instrumenting"] = "true"
        minSdk = 34
    }

    buildTypes {
        maybeCreate("benchmark")
        getByName("benchmark") {
            enableUnitTestCoverage = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

// Keep your bleeding-edge Java 24 toolchain
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(24))
        }
    }

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.hilt.android)

        ksp(libs.hilt.compiler)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        ksp(libs.hilt.compiler)
        implementation(libs.timber)
        implementation(project(":core-module"))
        implementation(project(":datavein-oracle-native"))
        implementation(project(":secure-comm"))
        implementation(project(":oracle-drive-integration"))
        androidTestImplementation(libs.androidx.benchmark.junit4)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(libs.androidx.test.uiautomator)
        testImplementation(libs.junit4)
        testImplementation(libs.mockk)
        androidTestImplementation(libs.mockk.android)
        testImplementation(libs.hilt.android.testing)
        androidTestImplementation(libs.hilt.android.testing)
        kspAndroidTest(libs.hilt.compiler)
        implementation(libs.kotlin.stdlib.jdk8)
        implementation(libs.kotlinx.serialization.json)
    }

    tasks.register("benchmarkStatus") {
        group = "aegenesis"
        doLast { println("\uD83D\uDCE6 BENCHMARK MODULE - Ready (Java 24)") }
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:-deprecation")
    }
}
