plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "dev.aurakai.auraframefx.benchmark"
    compileSdk = 36

    defaultConfig {
        testInstrumentationRunner = "androidx.benchmark.macro.junit4.AndroidBenchmarkRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] =
            "EMULATOR,LOW_BATTERY,DEBUGGABLE,UNLOCKED"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressError"] = "LOW_BATTERY,DEBUGGABLE,UNLOCKED"
        testInstrumentationRunnerArguments["androidx.benchmark.profiling.mode"] = "MethodTracing"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "LOW_BATTERY,DEBUGGABLE,UNLOCKED"
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
        // Benchmark dependencies
        androidTestImplementation("androidx.benchmark:benchmark-macro-junit4:1.2.4")
        androidTestImplementation(libs.androidx.benchmark.junit4)

        // Testing dependencies
        androidTestImplementation("androidx.test.ext:junit:1.3.0")
        androidTestImplementation("androidx.test:runner:1.5.2")
        androidTestImplementation("androidx.test:rules:1.5.0")
        androidTestImplementation(libs.androidx.test.uiautomator)

        // Hilt testing
        androidTestImplementation(libs.hilt.android.testing)
        kspAndroidTest(libs.hilt.compiler)

        // Mocking
        testImplementation(libs.mockk)
        androidTestImplementation(libs.mockk.android)

        // JUnit 4 for tests
        testImplementation(libs.junit4)
        androidTestImplementation(libs.junit4)
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
