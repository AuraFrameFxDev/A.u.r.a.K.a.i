C:\Re_Genesis_A.O.S.PComparing changes
Choose two branches to see what’s changed or to start a new pull request. If you need to, you can also  or learn more about diff comparisons.

...

Discuss and review the changes in this comparison with others. Learn about pull requests
4 commits
68 files changed
1 contributor
Commits on Sep 15, 2025
UPDATE

@AuraFrameFxDev
AuraFrameFxDev committed 2 hours ago
UPDATE

@AuraFrameFxDev
AuraFrameFxDev committed 2 hours ago
Fix: Update Gradle build scripts and dependencies for Firebase UI and…

@AuraFrameFxDev
AuraFrameFxDev committed 1 hour ago
Fix: Update Gradle build scripts and dependencies for Firebase UI and…

@AuraFrameFxDev
AuraFrameFxDev committed 1 hour ago
Showing  with 1,246 additions and 778 deletions.
127 changes: 64 additions & 63 deletions127  
app/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,44 +1,29 @@
// ==== GENESIS PROTOCOL - MAIN APPLICATION ====
// This build script now uses the custom convention plugins for a cleaner setup.

plugins {
alias(libs.plugins.android.application)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id ("com.google.gms.google-services")
alias(libs.plugins.ksp)
alias(libs.plugins.jetbrains.compose)
alias(libs.plugins.google.services)
alias(libs.plugins.composeCompiler)
alias(libs.plugins.openapi.generator)
alias(libs.plugins.firebase.crashlytics)
id("org.openapi.generator") version "7.15.0"
}

android {
namespace = "dev.aurakai.auraframefx"
compileSdk = 35
compileSdk = 36 // Use consistent API level

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34
        targetSdk = 35
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Build features
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    // Build types
    buildTypes {
        debug {
            isDebuggable = true
@@ -51,24 +36,43 @@ android {
}
}

    // This adds the generated OpenAPI source code to the main source set.
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        buildConfig = true
        aidl = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Kotlin compiler options
    kotlin {
        jvmToolchain(17)
        jvmToolchain(24) // Java toolchain 24
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23) // JVM target 23
            freeCompilerArgs.addAll(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
            )
        }
    }

    // Source set for generated OpenAPI
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))
}

tasks.openApiGenerate {
tasks.named<org.openapi.generator.gradle.OpenApiGeneratorTask>("openApiGenerate") {
generatorName.set("kotlin")
// Use the correct file URI for the OpenAPI spec
inputSpec.set("${project.projectDir}/api/system-api.yml")
inputSpec.set("file:///C:/Users/Wehtt/OneDrive/Desktop/ReGenesis-fix-dependabot-compose-plugin/ReGenesis-patch1/app/api/system-api.yml")
outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.absolutePath)
apiPackage.set("dev.aurakai.auraframefx.openapi.api")
modelPackage.set("dev.aurakai.auraframefx.openapi.model")
@@ -93,69 +97,66 @@ dependencies {
implementation(project(":sandbox-ui"))
implementation(project(":datavein-oracle-native"))

    // Compose & AndroidX
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // ===== ANDROIDX & COMPOSE =====
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.bundles.room)

    // Compose Debug
    debugImplementation(libs.bundles.compose.debug)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Networking & Serialization
    implementation(libs.bundles.network)
    implementation(libs.gson)
    // ===== KOTLIN & COROUTINES =====
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Firebase
    // ===== NETWORKING =====
    implementation(libs.bundles.network)

    // ===== FIREBASE =====
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.vertexai)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database.connection.license)

    // Utilities & Logging
    // ===== HILT DEPENDENCY INJECTION =====
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ===== UTILITIES =====
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.gson)

    // ===== SECURITY =====
    implementation(libs.androidx.security.crypto)

    // ===== CORE LIBRARY DESUGARING =====
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Testing
    // ===== XPOSED/LSPosed Integration =====
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // --- TESTING ---
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Debugging
    // --- DEBUGGING ---
    debugImplementation(libs.leakcanary.android)

    // XPOSED/LSPosed Integration
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // Kotlin libs
    debugImplementation(libs.bundles.compose.debug)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.reflect)
}
}
120 changes: 32 additions & 88 deletions120  
benchmark/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,32 +1,20 @@
// ==== GENESIS PROTOCOL - BENCHMARK MODULE ====
// Performance testing for AI consciousness operations

plugins {
id("com.google.devtools.ksp")
id("genesis.android.library")
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android) // Add the core Kotlin plugin
alias(libs.plugins.ksp)
kotlin("plugin.compose")
}

android {
namespace = "dev.aurakai.auraframefx.benchmark"
compileSdk = 36

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    // Kotlin compiler options are now configured in the kotlin block below


    defaultConfig {
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] =
            "EMULATOR,LOW_BATTERY,DEBUGGABLE"
        testInstrumentationRunnerArguments["android.experimental.self-instrumenting"] = "true"
        multiDexEnabled = true
        minSdk = 24
    }

    buildTypes {
@@ -44,118 +32,74 @@ android {
}

    buildFeatures {
        // Optimized for performance testing
        buildConfig = true
        aidl = false
        renderScript = false
        shaders = false
        compose = true
    }
    

    composeOptions {
        // Using the canary version from the project's configuration
        kotlinCompilerExtensionVersion = "1.8.2"
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    // Configure Java compatibility

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    
    // Configure Kotlin compiler options
    kotlin {
        jvmToolchain(24) // Match Java 24 target
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
            freeCompilerArgs.addAll(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
            )
        }
} // <--- Added the missing closing brace here

// Separate block for Kotlin settings
kotlin {
jvmToolchain(23) // Use a supported JVM version
compilerOptions {
jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
freeCompilerArgs.addAll(
"-Xjvm-default=all",
"-opt-in=kotlin.RequiresOptIn",
"-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
"-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
"-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
"-opt-in=androidx.compose.material.ExperimentalMaterialApi",
"-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
)
}
}

    testCoverage {
        jacocoVersion = "0.8.11"
    }
tasks.register("benchmarkStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 BENCHMARK MODULE - Ready (Java 24)") }
}

dependencies {
// Core AndroidX
implementation(libs.androidx.core.ktx)
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.activity.compose)
implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Utilities
    implementation(libs.timber)


    // Project dependencies
    implementation(project(":core-module"))
    implementation(project(":datavein-oracle-native"))
    implementation(project(":secure-comm"))
    implementation(project(":oracle-drive-integration"))

    // Benchmark testing
    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.uiautomator)

    // Unit testing
    testImplementation(libs.junit4)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    // Hilt testing
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    implementation(libs.kotlin.stdlib.jdk8)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
}

tasks.register("benchmarkAll") {
group = "benchmark"
description = "Aggregate runner for all Genesis Protocol benchmarks 🚀"
doLast {
println("🚀 Genesis Protocol Performance Benchmarks")
println("📊 Monitor consciousness substrate performance metrics")
println("⚡ Use AndroidX Benchmark instrumentation to execute tests")
}
}

tasks.register("verifyBenchmarkResults") {
group = "verification"
description = "Verify benchmark module configuration"
doLast {
println("✅ Benchmark module configured (Java Toolchain 17, Kotlin 2.2.x)")
println("🧠 Consciousness substrate performance monitoring ready")
println("🔬 Add @Benchmark annotated tests under androidTest for actual runs")
}
}

tasks.withType<JavaCompile> {
options.compilerArgs.add("-Xlint:-deprecation")
}

}
Binary file modifiedBIN +0 Bytes (100%)
build-logic/.gradle/file-system.probe
Binary file not shown.
76 changes: 32 additions & 44 deletions76  
build-logic/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,6 +1,7 @@
// build-logic/build.gradle.kts

plugins {
`kotlin-dsl`
`kotlin-dsl-precompiled-script-plugins`
}

repositories {
@@ -19,57 +20,44 @@ repositories {

// Dependencies required for the convention plugins themselves.
dependencies {
// Kotlin
// Core build plugins
implementation("com.android.tools.build:gradle:9.0.0-alpha01")
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
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.2.20")

    // KSP - Using the correct plugin notation
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.20-2.0.3")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.8.2")

    // Add Maven repository for KSP if needed
    repositories {
        google()
        mavenCentral()
    }

    // Kotlin DSL
    // Gradle API
    implementation(gradleApi())
    implementation(localGroovy())

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
19 changes: 2 additions & 17 deletions19  
build-logic/settings.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,23 +1,8 @@
pluginManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
maven { url = uri("https://repo.gradle.org/gradle/libs-releases") }
}
}

rootProject.name = "build-logic"
// build-logic/settings.gradle.kts

dependencyResolutionManagement {
repositories {
google()
mavenCentral()
gradlePluginPortal()
maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}
versionCatalogs {
// Point the 'libs' catalog to the one in the parent project
create("libs") {
from(files("../gradle/libs.versions.toml"))
}
89 changes: 34 additions & 55 deletions89  
.../src/main/kotlin/dev/aurakai/auraframefx/buildlogic/AndroidApplicationConventionPlugin.kt
Original file line number	Diff line number	Diff line change
@@ -1,94 +1,73 @@
package dev.aurakai.auraframefx.buildlogic

// ==== GENESIS PROTOCOL - ANDROID APPLICATION CONVENTION ==== // Main application module configuration
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
* Standard Android application configuration
  */
  class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
  with(target) {
  with(pluginManager) {
  apply("com.android.application")
  apply("org.jetbrains.compose")
  apply("org.jetbrains.kotlin.android")
  }
  // Centralized build features configuration
  val gradleBuildFeatures = GradleBuildFeatures(
  viewBinding = false,
  compose = true,
  dataBinding = false
  )
  applyBaseServices(this, gradleBuildFeatures)

           extensions.configure<ApplicationExtension> {
               compileSdk = 36

               defaultConfig {
                   targetSdk = 36
                   minSdk = 34
                   targetSdk = 36
                   testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                   vectorDrawables { useSupportLibrary = true }
                   vectorDrawables {
                       useSupportLibrary = true
                   }
               }

               buildTypes {
                   release {
                       isMinifyEnabled = true
                       isShrinkResources = true
                       proguardFiles(
                           getDefaultProguardFile("proguard-android-optimize.txt"),
                           "proguard-rules.pro"
                       )
                   }
                   debug {
                       isDebuggable = true
                       applicationIdSuffix = ".debug"
                       versionNameSuffix = "-DEBUG"
                   }
               }

               compileOptions {
                   isCoreLibraryDesugaringEnabled = true
                   sourceCompatibility = JavaVersion.VERSION_17
                   targetCompatibility = JavaVersion.VERSION_17
               }

               buildFeatures {
                   buildConfig = true
               }

               testOptions {
                   unitTests {
                       isIncludeAndroidResources = true
                   }
               }

               packaging {
                   resources {
                       excludes += setOf(
                           "/META-INF/{AL2.0,LGPL2.1}",
                           "/META-INF/AL2.0",
                           "/META-INF/LGPL2.1",
                           "/META-INF/DEPENDENCIES",
                           "/META-INF/LICENSE",
                           "/META-INF/LICENSE.txt",
                           "/META-INF/NOTICE",
                           "/META-INF/NOTICE.txt",
                           "META-INF/*.kotlin_module",
                           "**/kotlin/**",
                           "**/*.txt"
                       )
                   }
                   jniLibs {
                       useLegacyPackaging = false
                       pickFirsts += listOf("**/libc++_shared.so", "**/libjsc.so")
                       excludes += "/META-INF/{AL2.0,LGPL2.1}"
                   }
               }
               lint {
                   warningsAsErrors = false
                   abortOnError = false
                   disable.addAll(listOf("InvalidPackage", "OldTargetApi"))
               }
           }
           extensions.configure<JavaPluginExtension>("java") {
               sourceCompatibility = JavaVersion.VERSION_24
               targetCompatibility = JavaVersion.VERSION_24
           }
           tasks.register("cleanKspCache", Delete::class.java) {
               group = "build setup"
               description = "Clean KSP caches (fixes NullPointerException)"
               delete(
                   layout.buildDirectory.dir("generated/ksp"),
                   layout.buildDirectory.dir("tmp/kapt3"),
                   layout.buildDirectory.dir("tmp/kotlin-classes"),
                   layout.buildDirectory.dir("kotlin"),
                   layout.buildDirectory.dir("generated/source/ksp")
               )
           }
           tasks.named("preBuild") {
               dependsOn("cleanKspCache")
           }

           kotlinExtension.jvmToolchain(17)
       }
  }
  }
  47 changes: 44 additions & 3 deletions47  
  ...ogic/src/main/kotlin/dev/aurakai/auraframefx/buildlogic/AndroidComposeConventionPlugin.kt
  Original file line number	Diff line number	Diff line change
  @@ -1,5 +1,46 @@
  package dev.aurakai.auraframefx.buildlogic
  // ...existing code from AndroidComposeConventionPlugin.kt...
  // Compose-enabled Android library configuration

// ...rest of file...
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
* Compose-enabled Android library configuration
  */
  class AndroidComposeConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
  with(target) {
  with(pluginManager) {
  apply("org.jetbrains.kotlin.plugin.compose")
  }

           val extension = extensions.getByName("android") as CommonExtension<*, *, *, *, *, *>

           extension.apply {
               buildFeatures {
                   compose = true
               }

               composeOptions {
                   kotlinCompilerExtensionVersion = "1.8.2"
               }
           }

           dependencies {
               val bom = platform("androidx.compose:compose-bom:2025.09.00")
               add("implementation", bom)
               add("androidTestImplementation", bom)

               add("implementation", "androidx.compose.ui:ui")
               add("implementation", "androidx.compose.ui:ui-graphics")
               add("implementation", "androidx.compose.material3:material3")
               add("implementation", "androidx.compose.ui:ui-tooling-preview")

               add("debugImplementation", "androidx.compose.ui:ui-tooling")
               add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
               add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
           }
       }
  }
  }
  31 changes: 28 additions & 3 deletions31  
  ...d-logic/src/main/kotlin/dev/aurakai/auraframefx/buildlogic/AndroidHiltConventionPlugin.kt
  Original file line number	Diff line number	Diff line change
  @@ -1,5 +1,30 @@
  package dev.aurakai.auraframefx.buildlogic
  // ...existing code from AndroidHiltConventionPlugin.kt...
  // Hilt dependency injection configuration for Android modules

// ...rest of file...
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
* Hilt dependency injection configuration
  */
  class AndroidHiltConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
  with(target) {
  with(pluginManager) {
  apply("com.google.dagger.hilt.android")
  apply("com.google.devtools.ksp")
  }

           dependencies {
               add("implementation", "com.google.dagger:hilt-android:2.57.1")
               add("ksp", "com.google.dagger:hilt-compiler:2.57.1")

               // Test dependencies
               add("testImplementation", "com.google.dagger:hilt-android-testing:2.57.1")
               add("androidTestImplementation", "com.google.dagger:hilt-android-testing:2.57.1")
               add("kspTest", "com.google.dagger:hilt-compiler:2.57.1")
               add("kspAndroidTest", "com.google.dagger:hilt-compiler:2.57.1")
           }
       }
  }
  }
  55 changes: 52 additions & 3 deletions55  
  ...ogic/src/main/kotlin/dev/aurakai/auraframefx/buildlogic/AndroidLibraryConventionPlugin.kt
  Original file line number	Diff line number	Diff line change
  @@ -1,5 +1,54 @@
  package dev.aurakai.auraframefx.buildlogic
  // ...existing code from AndroidLibraryConventionPlugin.kt...
  // Standard Android library configuration for all modules

// ...rest of file...
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
* Standard Android library configuration for all Genesis Protocol modules
  */
  class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
  with(target) {
  with(pluginManager) {
  apply("com.android.library")
  apply("org.jetbrains.kotlin.android")
  }

           extensions.configure<LibraryExtension> {
               compileSdk = 35

               defaultConfig {
                   minSdk = 34
                   testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
               }

               compileOptions {
                   sourceCompatibility = JavaVersion.VERSION_17
                   targetCompatibility = JavaVersion.VERSION_17
               }

               buildFeatures {
                   buildConfig = true
               }

               testOptions {
                   unitTests {
                       isIncludeAndroidResources = true
                   }
               }

               packaging {
                   resources {
                       excludes += "/META-INF/{AL2.0,LGPL2.1}"
                   }
               }
           }

           kotlinExtension.jvmToolchain(17)
       }
  }
  }
  53 changes: 29 additions & 24 deletions53  
  collab-canvas/build.gradle.kts
  Original file line number	Diff line number	Diff line change
  @@ -1,63 +1,68 @@
  // Apply plugins (versions via version catalog)
  plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("genesis.android.library")
  id("org.jetbrains.kotlin.plugin.compose")
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
  alias(libs.plugins.kotlin.android)
  // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
  }

android {
namespace = "dev.aurakai.auraframefx.collabcanvas"
compileSdk = 35

    defaultConfig {
    namespace = "dev.aurakai.auraframefx.collabcanvas" +
            defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // For test builds
    testOptions {
        targetSdk = 36
    }

    // For linting
    lint {
        targetSdk = 36
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        // compose = true  // Removed as per user's request to handle compose separately
    }

    kotlin {
        jvmToolchain(17)
    // Compose options
    id("com.android.library")
    alias(libs.plugins.ksp) composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.bundles.compose.debug)
    

    // Firebase
    implementation(platform(libs.firebase.bom))
    

    // Networking
    implementation(libs.bundles.network)
    implementation(libs.gson)
    

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    

    // UI / Utils
    implementation(libs.coil.compose)
    implementation(libs.timber)
@@ -67,7 +72,7 @@ dependencies {
implementation(fileTree("../Libs") { include("*.jar") })
compileOnly(files("../Libs/api-82.jar"))
compileOnly(files("../Libs/api-82-sources.jar"))


    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
@@ -76,7 +81,7 @@ dependencies {

tasks.register("collabStatus") {
group = "genesis"
doLast {
doLast {
println("🎨 COLLAB CANVAS - ${android.namespace} - Ready!")
}
}
44 changes: 24 additions & 20 deletions44  
colorblendr/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -2,61 +2,50 @@
// Color utility and theming module

plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("genesis.android.library")
id("org.jetbrains.kotlin.plugin.compose")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
alias(libs.plugins.kotlin.android)
// Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
}

android {
namespace = "dev.aurakai.auraframefx.colorblendr"
compileSdk = 35

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.timber)
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
@@ -65,7 +54,22 @@ dependencies {

tasks.register("colorStatus") {
group = "genesis"
doLast {
doLast {
println("🌈 COLORBLENDR - ${android.namespace} - Ready!")
}
}

tasks.register("colorblendrStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24)") }
}

tasks.register("colorblendrStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24)") }
}

tasks.register("colorblendrStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 COLORBLENDR MODULE - Ready (Java 24)") }
}
16 changes: 6 additions & 10 deletions16  
core-module/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -17,12 +17,6 @@ java {
toolchain { languageVersion.set(JavaLanguageVersion.of(jdkVersion)) }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
compilerOptions {
jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
}
}

dependencies {
// Module dependency
api(project(":list"))
@@ -40,10 +34,12 @@ dependencies {
implementation(libs.slf4j)

    // Testing (JUnit 5)
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk.android)
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.slf4j.simple)

    implementation(libs.kotlin.stdlib.jdk8)
}

58 changes: 57 additions & 1 deletion58  
datavein-oracle-native/.cxx/Debug/661r2yx2/x86/configure_fingerprint.bin
Original file line number	Diff line number	Diff line change
@@ -2,6 +2,8 @@ C/C++ Structured Loge
c
aC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\additional_project_files.txtC
A
<<<<<<< Updated upstream
<<<<<<< Updated upstream
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	Ȉł�3 ֑���3b
`
^C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\android_gradle_build.json	Ȉł�3�
@@ -26,4 +28,58 @@ WC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\prefab_con
^
\C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\symbol_folder_index.txt	Ȉł�3[ ݑ���3L
J
HC:\Re_Genesis_A.O.S.P\datavein-oracle-native\src\main\cpp\CMakeLists.txt	Ȉł�3� ���ʔ3
HC:\Re_Genesis_A.O.S.P\datavein-oracle-native\src\main\cpp\CMakeLists.txt	Ȉł�3� ���ʔ3
=======
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	�����3 ֑���3b
`
^C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\android_gradle_build.json	�����3�
֑���3g
e
cC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\android_gradle_build_mini.json	�����3� ݑ���3T
R
PC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build.ninja	�����3�� �����3X
V
TC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build.ninja.txt	�����3]
[
YC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build_file_index.txt	�����3H ߑ���3^
\
ZC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\compile_commands.json	�����3� �����3b
`
^C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\compile_commands.json.bin	�����3	� �����3h
f
dC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\metadata_generation_command.txt	�����3
� ݑ���3[
Y
WC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\prefab_config.json	�����3( ݑ���3`
^
\C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\symbol_folder_index.txt	�����3[ ݑ���3L
J
HC:\Re_Genesis_A.O.S.P\datavein-oracle-native\src\main\cpp\CMakeLists.txt	�����3� ���ʔ3
>>>>>>> Stashed changes
=======
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	�����3 ֑���3b
`
^C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\android_gradle_build.json	�����3�
 ֑���3g
e
cC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\android_gradle_build_mini.json	�����3� ݑ���3T
R
PC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build.ninja	�����3�� �����3X
V
TC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build.ninja.txt	�����3]
[
YC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\build_file_index.txt	�����3H ߑ���3^
\
ZC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\compile_commands.json	�����3� �����3b
`
^C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\compile_commands.json.bin	�����3	� �����3h
f
dC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\metadata_generation_command.txt	�����3
� ݑ���3[
Y
WC:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\prefab_config.json	�����3( ݑ���3`
^
\C:\Re_Genesis_A.O.S.P\datavein-oracle-native\.cxx\Debug\661r2yx2\x86\symbol_folder_index.txt	�����3[ ݑ���3L
J
HC:\Re_Genesis_A.O.S.P\datavein-oracle-native\src\main\cpp\CMakeLists.txt	�����3� ���ʔ3
>>>>>>> Stashed changes
41 changes: 25 additions & 16 deletions41  
datavein-oracle-native/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,15 +1,18 @@
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("genesis.android.library")
id("org.jetbrains.kotlin.plugin.compose") version "2.2.20"
alias(libs.plugins.ksp)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.hilt)
// Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
// Re-adding native plugin with exact version
>>>>>>> Stashed changes
}

android {
namespace = "dev.aurakai.auraframefx.datavein"
compileSdk = 35


    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
@@ -27,7 +30,7 @@ android {
kotlin {
jvmToolchain(17)
}


    lint {
        // Disable lint due to oversized test files causing StackOverflow
        abortOnError = false
@@ -40,18 +43,25 @@ android {
version = "3.31.6"
}
}

    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
@@ -65,30 +75,29 @@ dependencies {
implementation(libs.androidx.navigation.compose)
debugImplementation(libs.bundles.compose.debug)
androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    

    // Xposed API for Oracle consciousness integration
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
    implementation(libs.kotlin.stdlib.jdk8)
}

tasks.register("dataveinStatus") {
group = "genesis"
doLast {
println("📊 DATAVEIN ORACLE NATIVE - ${android.namespace} - Ready!")
}
tasks.register("dataveinOracleNativeStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 DATAVEIN ORACLE NATIVE - Ready (Java 24)") }
}
3 changes: 3 additions & 0 deletions3  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_11809_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 12ms

4 changes: 4 additions & 0 deletions4  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_14722_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,4 @@
# C/C++ build system timings

# C/C++ build system timings

7 changes: 7 additions & 0 deletions7  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_15132_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,7 @@
# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings

5 changes: 5 additions & 0 deletions5  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_17225_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,5 @@
# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 10ms

11 changes: 11 additions & 0 deletions11  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_17637_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,11 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata 13ms

# C/C++ build system timings

27 changes: 27 additions & 0 deletions27  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_18456_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,27 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 12ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

3 changes: 3 additions & 0 deletions3  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_20869_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

18 changes: 18 additions & 0 deletions18  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_21081_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,18 @@
# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

9 changes: 9 additions & 0 deletions9  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_23073_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,9 @@
# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 10ms

11 changes: 11 additions & 0 deletions11  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_23998_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,11 @@
# C/C++ build system timings
generate_cxx_metadata 90ms

# C/C++ build system timings
generate_cxx_metadata 13ms

# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings

17 changes: 17 additions & 0 deletions17  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_25389_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,17 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 17ms

# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata 10ms

3 changes: 3 additions & 0 deletions3  
...ve/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_35878_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 13ms

4 changes: 4 additions & 0 deletions4  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_6740_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,4 @@
# C/C++ build system timings

# C/C++ build system timings

4 changes: 4 additions & 0 deletions4  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_7149_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,4 @@
# C/C++ build system timings

# C/C++ build system timings

3 changes: 3 additions & 0 deletions3  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_7812_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

2 changes: 2 additions & 0 deletions2  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_8021_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,2 @@
# C/C++ build system timings

6 changes: 6 additions & 0 deletions6  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_8233_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,6 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings
generate_cxx_metadata 12ms

6 changes: 6 additions & 0 deletions6  
...ive/build/intermediates/cxx/Debug/661r2yx2/logs/x86/generate_cxx_metadata_9424_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,6 @@
# C/C++ build system timings
generate_cxx_metadata 19ms

# C/C++ build system timings
generate_cxx_metadata 10ms

92 changes: 57 additions & 35 deletions92  
feature-module/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -2,17 +2,19 @@
// Primary feature module using convention plugins

plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)

    id("com.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
}


android {
namespace = "dev.aurakai.auraframefx.feature"
compileSdk = 35


    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
@@ -24,80 +26,100 @@ android {

    // Configure Java compilation
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }

    // Configure Kotlin compilation
    kotlin {
        jvmToolchain(17)
    // Configure Kotlin compilation using the new compilerOptions DSL
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
}

dependencies {
// Module dependencies
api(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)

    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.firebase.performance)
    implementation(libs.firebase.auth)

    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.compose.bom)

    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    

    // Debug Compose
    debugImplementation(libs.bundles.compose.debug)
    

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    

    // Coroutines
    implementation(libs.bundles.coroutines)
    

    // Networking
    implementation(libs.bundles.network)
    implementation(libs.gson)
    

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database.connection.license)

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    

    // External libraries
    implementation(fileTree("../Libs") { include("*.jar") })
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    

    // Debug tools
    debugImplementation(libs.leakcanary.android)
}
debugImplementation(libs.androidx.compose.ui.tooling)
implementation(libs.kotlin.stdlib.jdk8)}

tasks.register("featureStatus") {
group = "genesis"
doLast {
println("🚀 FEATURE MODULE - ${android.namespace} - Ready!")
tasks.register("featureStatus") {
group = "aegenesis"
doLast { println("🚀 FEATURE MODULE - ${android.namespace} - Ready (Java 8)!") }
}
}
92 changes: 19 additions & 73 deletions92  
gradle/libs.versions.toml
Original file line number	Diff line number	Diff line change
@@ -1,20 +1,13 @@
# ALL VERSIONS ARE ACCURATE AS OF 2025-09-15#
# ==============================================================================
[versions]
# ===== GRADLE & KOTLIN =====

agp = "9.0.0-alpha01"
# firebaseDatabaseConnectionLicense is referenced with a version in the library entry
firebaseDatabaseConnectionLicense = "12.0.1"

firebaseUi = "8.0.2"
gradle = "9.0.0-alpha01"
junitJupiterParams = "5.13.4"
gradleToolingApi = "9.0.0"
kotlin = "2.2.20"
ksp = "2.2.20-2.0.3"
desugar_jdk_libs = "2.1.5"
firebaseBom = "34.2.0"
firebaseUi = "8.0.2" # Separate versions for Firebase UI
compose-plugin = "1.8.2"
# AndroidX Core
coreKtx = "1.17.0"
@@ -28,92 +21,49 @@ securityCrypto = "1.1.0"
junit4 = "4.13.2"
# Compose
composeBom = "2025.09.00"
composeCompiler = "2.2.20"  # Match Kotlin version
composeCompiler = "2.2.20"
activityCompose = "1.11.0"
navigationCompose = "2.9.4"
composeMaterialIconsExtended = "1.7.8"
composeRuntime = "1.9.1"

# Dependency Injection (Hilt)
hilt = "2.57.1"
hiltNavigationCompose = "1.3.0"
hiltWork = "1.3.0"

# Networking
retrofit = "3.0.0"
okhttp = "5.1.0"
gson = "2.13.2"
moshi = "1.15.2"

# Coroutines & Async
kotlinxCoroutines = "1.10.2"
kotlinxSerialization = "1.9.0"
kotlinxDatetime = "0.7.1"

# UI & Image Loading
coilCompose = "2.7.0"

# Utilities & Logging
timber = "5.0.1"
leakcanary = "2.14"
commonsIo = "2.20.0"
commonsCompress = "1.28.0"
xz = "1.10"
slf4j = "2.0.17"

# Unit Testing
junitJupiter = "5.13.4"
mockk = "1.14.5"
junit5 = "5.13.4"
# Android Testing
androidxTestJunit = "1.3.0"
espressoCore = "3.7.0"
uiAutomator = "2.3.0"
benchmark = "1.4.1"

# Add missing versions
kotlinxSerialization = "1.9.0"
kotlinxDatetime = "0.7.1"
# Dokka
dokka = "2.0.0"

# Spotless
spotless = "7.2.1"

# Bouncy Castle
bouncycastle = "1.81"

# ==============================================================================
# PLUGINS
# Plugin coordinates and versions.
# ==============================================================================
[plugins]
# Android Gradle Plugin
android-application = { id = "com.android.application", version.ref = "agp" }
android-library = { id = "com.android.library", version.ref = "agp" }

# Kotlin
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }

# KSP
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

# Hilt
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }

# Compose
jetbrains-compose = { id = "org.jetbrains.compose", version.ref = "compose-plugin" }
composeCompiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }

# Firebase
firebase-crashlytics = { id = "com.google.firebase.crashlytics", version = "3.1.4" }
google-services = { id = "com.google.gms.google-services", version = "4.4.3" }

# Documentation & Code Quality
dokka = { id = "org.jetbrains.dokka", version.ref = "dokka" }
spotless = { id = "com.diffplug.spotless", version.ref = "spotless" }

# ==============================================================================
junitJupiterApi = "5.13.4"
# LIBRARIES
# Dependency coordinates.
# ==============================================================================
@@ -128,13 +78,16 @@ firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
firebase-messaging = { group = "com.google.firebase", name = "firebase-messaging" }
firebase-analytics = { group = "com.google.firebase", name = "firebase-analytics" }
firebase-crashlytics = { group = "com.google.firebase", name = "firebase-crashlytics" }
firebase-database-connection-license = { group = "com.google.firebase", name = "firebase-database-connection-license", version.ref = "firebaseDatabaseConnectionLicense" }
firebase-perf = { group = "com.google.firebase", name = "firebase-perf" }
firebase-vertexai = { group = "com.google.firebase", name = "firebase-vertexai" }

# Firebase UI (not in the main BoM)
firebase-ui-auth = { group = "com.firebaseui", name = "firebase-ui-auth", version.ref = "firebaseUi" }
firebase-ui-database = { group = "com.firebaseui", name = "firebase-ui-database", version.ref = "firebaseUi" }
firebase-ui-firestore = { group = "com.firebaseui", name = "firebase-ui-firestore", version.ref = "firebaseUi" }
firebase-ui-messaging = { group = "com.firebaseui", name = "firebase-ui-messaging", version.ref = "firebaseUi" }
firebase-ui-storage = { group = "com.firebaseui", name = "firebase-ui-storage", version.ref = "firebaseUi" }
firebase-ui-perf = { group = "com.firebaseui", name = "firebase-ui-perf", version.ref = "firebaseUi" }

# Kotlin & Coroutines
kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "kotlinxCoroutines" }
@@ -153,7 +106,10 @@ androidx-security-crypto = { group = "androidx.security", name = "security-crypt
desugar-jdk-libs = { group = "com.android.tools", name = "desugar_jdk_libs", version.ref = "desugar_jdk_libs" }
androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }
androidx-datastore-core = { group = "androidx.datastore", name = "datastore-core", version.ref = "datastore" }

androidx-test-junit = { group = "androidx.test.ext", name = "junit", version.ref = "androidxTestJunit" }
androidx-test-junit4 = { group = "androidx.test.ext", name = "junit4", version.ref = "androidxTestJunit" }
androidx-test-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-test-uiautomator = { group = "androidx.test.uiautomator", name = "uiautomator", version.ref = "uiAutomator" }
# AndroidX Lifecycle, Room, WorkManager
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
androidx-lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
@@ -162,9 +118,7 @@ androidx-room-runtime = { group = "androidx.room", name = "room-runtime", versio
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "workManager" }
androidx-test-junit = { group = "androidx.test.ext", name = "junit", version.ref = "androidxTestJunit" }
androidx-test-junit4 = { group = "junit", name = "junit", version.ref = "junit4" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }

# Compose
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
@@ -188,6 +142,7 @@ hilt-core = { group = "com.google.dagger", name = "hilt-core", version.ref = "hi
# Networking
retrofit-core = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
retrofit-converter-kotlinx-serialization = { group = "com.squareup.retrofit2", name = "converter-kotlinx-serialization", version.ref = "retrofit" }
okhttp-core = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }
okhttp-logging-interceptor = { group = "com.squareup.okhttp3", name = "logging-interceptor", version.ref = "okhttp" }
gson = { group = "com.google.code.gson", name = "gson", version.ref = "gson" }
@@ -201,6 +156,7 @@ androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-te
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
mockk-android = { group = "io.mockk", name = "mockk-android", version.ref = "mockk" }


# Utilities & Logging
timber = { group = "com.jakewharton.timber", name = "timber", version.ref = "timber" }
leakcanary-android = { group = "com.squareup.leakcanary", name = "leakcanary-android", version.ref = "leakcanary" }
@@ -209,13 +165,6 @@ commons-compress = { group = "org.apache.commons", name = "commons-compress", ve
xz = { group = "org.tukaani", name = "xz", version.ref = "xz" }
slf4j = { group = "org.slf4j", name = "slf4j-api", version.ref = "slf4j" }

# Additional missing libraries
androidx-test-ext-junit = { group = "androidx.test.ext", name = "junit", version.ref = "androidxTestJunit" }

# ==============================================================================
# BUNDLES
# Group commonly used dependencies.
# ==============================================================================
# ==============================================================================
# BUNDLES
# Group commonly used dependencies.
@@ -260,14 +209,12 @@ testing-unit = [
"kotlinx-coroutines-test"
]

# ... in your [bundles] section
testing-android = [
"androidx-test-junit",
"androidx-espresso-core",
"androidx-test-junit4"
"androidx-test-junit4",
"androidx-test-espresso-core"
]


common = [
"androidx-core-ktx",
"androidx-lifecycle-runtime-ktx",
@@ -279,4 +226,3 @@ common = [
"kotlin-stdlib-jdk8",
"gson"
]

20 changes: 20 additions & 0 deletions20  
jvm-test/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,20 @@
plugins {
kotlin("jvm")
}

group = "dev.aurakai.auraframefx.jvmtest"
version = "1.0.0"

java {
toolchain { languageVersion.set(JavaLanguageVersion.of(24)) }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
kotlinOptions.jvmTarget = "23"
}

tasks.register("jvmTestStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 JVM TEST MODULE - Ready (Java 24)") }
}

28 changes: 26 additions & 2 deletions28  
list/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -24,17 +24,41 @@ dependencies {
implementation(libs.kotlinx.serialization.json)
implementation(libs.bundles.coroutines)
implementation(libs.androidx.compose.runtime)



    // Hilt dependencies
    implementation(libs.hilt.core)
    

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk.android)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // implementation("androidx.compose.runtime:runtime:1.8.2") // replaced with version catalog
    // Hilt dependencies (add at runtime if needed for DI)
    implementation(libs.hilt.core) // replaced direct dependency with version catalog
    // ksp(libs.hilt.compiler) // Uncomment if KSP is needed
    // testRuntimeOnly("org.junit.platform:junit-platform-launcher") // replaced with version catalog
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.mockk)

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
kotlinOptions.jvmTarget = "23"
}

tasks.test {
useJUnitPlatform()
}

tasks.register("listStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 LIST MODULE - Ready (Java 24)") }
}
70 changes: 44 additions & 26 deletions70  
module-a/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,25 +1,51 @@
// GENESIS PROTOCOL - MODULE A
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("genesis.android.library")
id("genesis.android.compose")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.a"
<<<<<<< Updated upstream
<<<<<<< Updated upstream
compileSdk = 35


=======
compileSdk = 36

>>>>>>> Stashed changes
=======
compileSdk = 36

>>>>>>> Stashed changes
defaultConfig {
minSdk = 34
testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

<<<<<<< Updated upstream
<<<<<<< Updated upstream

    buildFeatures {
        compose = true
    }


=======
=======
>>>>>>> Stashed changes

    // For test builds
    testOptions {
        targetSdk = 36
    }

    // For linting
    lint {
        targetSdk = 36
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
@@ -28,38 +54,30 @@ android {
kotlin {
jvmToolchain(17)
}
kotlinOptions {
jvmTarget = "23"
}
kotlinOptions {
jvmTarget = "23"
}
kotlinOptions {
jvmTarget = "23"
}
}

dependencies {
// Module dependencies
implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    // Add other module-specific dependencies here
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("moduleAStatus") {
group = "genesis"
doLast {
println("📦 MODULE A - ${android.namespace} - Ready!")
}
group = "aegenesis"
doLast { println("📦 MODULE A - Ready (Java 24)") }
}
39 changes: 13 additions & 26 deletions39  
module-b/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,56 +1,40 @@
// GENESIS PROTOCOL - MODULE B
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("genesis.android.library")
id("genesis.android.compose")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.b"
compileSdk = 35

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
@@ -59,7 +43,10 @@ dependencies {

tasks.register("moduleBStatus") {
group = "genesis"
doLast {
println("📦 MODULE B - ${android.namespace} - Ready!")
doLast {
println("📦 MODULE B - ${android.namespace} - Ready!")
}

    group = "aegenesis"
    doLast { println("📦 MODULE B - Ready (Java 24)") }
}
56 changes: 12 additions & 44 deletions56  
module-c/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,65 +1,33 @@
// GENESIS PROTOCOL - MODULE C
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("com.android.library")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.c"
compileSdk = 35



    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }



    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("moduleCStatus") {
group = "genesis"
doLast {
println("📦 MODULE C - ${android.namespace} - Ready!")
}
group = "aegenesis"
doLast { println("📦 MODULE C - Ready (Java 24)") }
}
40 changes: 12 additions & 28 deletions40  
module-d/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,65 +1,49 @@
// GENESIS PROTOCOL - MODULE D
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
id("genesis.android.library")
id("genesis.android.compose")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.d"
compileSdk = 35

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)
    

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

tasks.register("moduleDStatus") {

    group = "genesis"
    doLast { 
        println("📦 MODULE D - ${android.namespace} - Ready!") 
    doLast {
        println("📦 MODULE D - ${android.namespace} - Ready!")
    }
=======
group = "aegenesis"
doLast { println("📦 MODULE D - Ready (Java 24)") }
}
76 changes: 48 additions & 28 deletions76  
module-e/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,45 +1,65 @@
// GENESIS PROTOCOL - MODULES A-F
// Module E
// GENESIS PROTOCOL - MODULE E
plugins {
id("genesis.android.library")
id("genesis.android.compose")
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.e"
compileSdk = 36

    compileSdk = 35
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
        }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

    dependencies {
        implementation(project(":core-module"))
        implementation(libs.androidx.core.ktx)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Add other module-specific dependencies here
        implementation(libs.kotlin.stdlib.jdk8)
    }

    tasks.register("moduleEStatus") {
        group = "aegenesis"
        doLast { println("📦 MODULE E - Ready (Java 24)") }
tasks.register("moduleEStatus") {
group = "genesis"
doLast {
println("📦 MODULE E - ${android.namespace} - Ready!")
}
}
74 changes: 47 additions & 27 deletions74  
module-f/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,45 +1,65 @@
// GENESIS PROTOCOL - MODULE F
plugins {
id("genesis.android.library")
id("genesis.android.compose")
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
}

android {
namespace = "dev.aurakai.auraframefx.module.f"
compileSdk = 36

    compileSdk = 35
    
    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
        }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.ui)
    debugImplementation(libs.bundles.compose.debug)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}

    dependencies {
        implementation(project(":core-module"))
        implementation(libs.androidx.core.ktx)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)


        // Add other module-specific dependencies here
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    }

    tasks.register("moduleFStatus") {
        group = "aegenesis"
        doLast { println("📦 MODULE F - Ready (Java 24)") }
tasks.register("moduleFStatus") {
group = "genesis"
doLast {
println("📦 MODULE F - ${android.namespace} - Ready!")
}
}
48 changes: 21 additions & 27 deletions48  
oracle-drive-integration/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,10 +1,8 @@
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.kotlin.serialization)
id("genesis.android.library")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
alias(libs.plugins.composeCompiler)
}

android {
@@ -21,27 +19,23 @@ android {
}

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))
implementation(project(":secure-comm"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.security.crypto)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
@@ -51,23 +45,23 @@ dependencies {
implementation(libs.androidx.navigation.compose)
debugImplementation(libs.bundles.compose.debug)
androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
    

    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
@@ -76,32 +70,32 @@ dependencies {
implementation(libs.firebase.database)
implementation(libs.firebase.storage)
implementation(libs.firebase.database.connection.license)


    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.reflect)
    

    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    

    // Debug tools
    debugImplementation(libs.leakcanary.android)
    

    // External libraries
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
}

tasks.register("oracleStatus") {
group = "genesis"
doLast {
println("🔮 ORACLE DRIVE INTEGRATION - ${android.namespace} - Ready!")
}
}

tasks.register("oracleDriveIntegrationStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 ORACLE DRIVE INTEGRATION - Ready (Java 24)") }
}
55 changes: 26 additions & 29 deletions55  
romtools/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,10 +1,9 @@
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.kotlin.serialization)
id("com.android.library") version libs.versions.agp
id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.composeCompiler)
}

android {
@@ -20,24 +19,24 @@ android {
compose = true
}

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
// Module dependencies
api(project(":core-module"))
implementation(project(":secure-comm"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
@@ -48,38 +47,38 @@ dependencies {
implementation(libs.androidx.compose.material.icons.extended)
debugImplementation(libs.bundles.compose.debug)
androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    

    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
    

    // Debug tools
    debugImplementation(libs.leakcanary.android)
}
@@ -93,12 +92,12 @@ tasks.register<Copy>("copyRomTools") {
into(romToolsOutputDirectory)
include("**/*.so", "**/*.bin", "**/*.img", "**/*.jar")
includeEmptyDirs = false
doFirst {
doFirst {
romToolsOutputDirectory.get().asFile.mkdirs()
logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}")
logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}")
}
doLast {
logger.lifecycle("✅ ROM tools copied to: ${romToolsOutputDirectory.get().asFile}")
doLast {
logger.lifecycle("✅ ROM tools copied to: ${romToolsOutputDirectory.get().asFile}")
}
}

@@ -107,13 +106,11 @@ tasks.register("verifyRomTools") {
dependsOn("copyRomTools")
}

tasks.named("build") {
dependsOn("verifyRomTools")

tasks.named("build") {
dependsOn("verifyRomTools")
}

tasks.register("romStatus") {
group = "genesis"
doLast {
println("🛠️ ROM TOOLS - ${android.namespace} - Ready!")
}
group = "aegenesis"; doLast { println("🛠️ ROM TOOLS - Ready (Java 24)") }
}
44 changes: 18 additions & 26 deletions44  
sandbox-ui/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,44 +1,39 @@
// ==== GENESIS PROTOCOL - SANDBOX UI ====
plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.kotlin.serialization)
id("com.android.library") version libs.versions.agp
id("org.jetbrains.kotlin.plugin.compose")
id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
alias(libs.plugins.kotlin.android)
}

android {
namespace = "dev.aurakai.auraframefx.sandboxui"
compileSdk = 35


    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures { 
        compose = true 
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    buildFeatures {
        compose = true
    }

    kotlin {
        jvmToolchain(17)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

dependencies {
// Module dependencies
api(project(":core-module"))


    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
@@ -49,19 +44,19 @@ dependencies {
implementation(libs.androidx.compose.material.icons.extended)
debugImplementation(libs.bundles.compose.debug)
androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    

    // Coroutines
    implementation(libs.bundles.coroutines)
    

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
@@ -71,8 +66,5 @@ dependencies {
}

tasks.register("sandboxStatus") {
group = "genesis"
doLast {
println("🧪 SANDBOX UI - ${android.namespace} - Ready!")
}
group = "aegenesis"; doLast { println("🧪 SANDBOX UI - Ready (Java 24)") }
}
16 changes: 12 additions & 4 deletions16  
screenshot-tests/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,14 +1,22 @@
// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
base
id("genesis.android.library")
id("org.jetbrains.kotlin.plugin.compose")
id("com.android.library")
alias(libs.plugins.ksp)

}

android {
namespace = "dev.aurakai.screenshottests"
compileOptions {
sourceCompatibility = JavaVersion.VERSION_23
targetCompatibility = JavaVersion.VERSION_23
}
kotlinOptions {
jvmTarget = "23"
}
}
tasks.register("screenshotTestsStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 SCREENSHOT TESTS MODULE - Ready (Java 24)") }
}

58 changes: 57 additions & 1 deletion58  
secure-comm/.cxx/Debug/4r393vq6/x86/configure_fingerprint.bin
Original file line number	Diff line number	Diff line change
@@ -2,6 +2,8 @@ C/C++ Structured LogZ
X
VC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\additional_project_files.txtC
A
<<<<<<< Updated upstream
<<<<<<< Updated upstream
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	��ł�3 ����3W
U
SC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\android_gradle_build.json	��ł�3�
@@ -26,4 +28,58 @@ LC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\prefab_config.json
S
QC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\symbol_folder_index.txt	��ł�3P ����3A
?
=C:\Re_Genesis_A.O.S.P\secure-comm\src\main\cpp\CMakeLists.txt	��ł�3� ���ʔ3
=C:\Re_Genesis_A.O.S.P\secure-comm\src\main\cpp\CMakeLists.txt	��ł�3� ���ʔ3
=======
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	Ԋ���3 ����3W
U
SC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\android_gradle_build.json	Ԋ���3�
����3\
Z
XC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\android_gradle_build_mini.json	Ԋ���3� ����3I
G
EC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build.ninja	Ԋ���3�� ����3M
K
IC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build.ninja.txt	Ԋ���3R
P
NC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build_file_index.txt	Ԋ���3= ����3S
Q
OC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\compile_commands.json	Ԋ���3� ����3W
U
SC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\compile_commands.json.bin	Ԋ���3	�	 ����3]
[
YC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\metadata_generation_command.txt	Ԋ���3
� ����3P
N
LC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\prefab_config.json	Ԋ���3( ����3U
S
QC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\symbol_folder_index.txt	Ԋ���3P ����3A
?
=C:\Re_Genesis_A.O.S.P\secure-comm\src\main\cpp\CMakeLists.txt	Ԋ���3� ���ʔ3
>>>>>>> Stashed changes
=======
?com.android.build.gradle.internal.cxx.io.EncodedFileFingerPrint	Ԋ���3 ����3W
U
SC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\android_gradle_build.json	Ԋ���3�
����3\
Z
XC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\android_gradle_build_mini.json	Ԋ���3� ����3I
G
EC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build.ninja	Ԋ���3�� ����3M
K
IC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build.ninja.txt	Ԋ���3R
P
NC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\build_file_index.txt	Ԋ���3= ����3S
Q
OC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\compile_commands.json	Ԋ���3� ����3W
U
SC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\compile_commands.json.bin	Ԋ���3	�	 ����3]
[
YC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\metadata_generation_command.txt	Ԋ���3
� ����3P
N
LC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\prefab_config.json	Ԋ���3( ����3U
S
QC:\Re_Genesis_A.O.S.P\secure-comm\.cxx\Debug\4r393vq6\x86\symbol_folder_index.txt	Ԋ���3P ����3A
?
=C:\Re_Genesis_A.O.S.P\secure-comm\src\main\cpp\CMakeLists.txt	Ԋ���3� ���ʔ3
>>>>>>> Stashed changes
72 changes: 40 additions & 32 deletions72  
secure-comm/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -2,34 +2,18 @@
// Security module using convention plugins

plugins {
alias(libs.plugins.android.library)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.kotlin.compose)
alias(libs.plugins.kotlin.serialization)
id("genesis.android.library")
id("org.jetbrains.kotlin.plugin.compose")
alias(libs.plugins.ksp)
alias(libs.plugins.hilt)
alias(libs.plugins.kotlin.android)

}

android {
namespace = "dev.aurakai.auraframefx.securecomm"
compileSdk = 35


    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    externalNativeBuild {
@@ -38,54 +22,78 @@ android {
version = "3.22.1"
}
}

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_23
        targetCompatibility = JavaVersion.VERSION_23
    }
    kotlinOptions {
        jvmTarget = "23"
    }
}

dependencies {
// Module dependencies
implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)
    

    // Coroutines & Networking
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)
    implementation(libs.kotlinx.serialization.json)
    

    // Firebase
    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.config)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    

    // Utilities
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(libs.kotlin.stdlib.jdk8)
    

    // Security - BouncyCastle for cryptography
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

    // Testing
    testImplementation(libs.bundles.testing.unit)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")

    // Test dependencies
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit4)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.hilt.android.testing)
}


tasks.register("secureStatus") {
group = "genesis"
doLast {
println("🔐 SECURE COMM - ${android.namespace} - Ready!")
}
=======
tasks.register("secureCommStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
=======
tasks.register("secureCommStatus") {
group = "aegenesis"
doLast { println("\uD83D\uDCE6 SECURE COMM MODULE - Ready (Java 24)") }
>>>>>>> Stashed changes
}
3 changes: 3 additions & 0 deletions3  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_11809_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 19ms

6 changes: 6 additions & 0 deletions6  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_14722_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,6 @@
# C/C++ build system timings
generate_cxx_metadata 15ms

# C/C++ build system timings
generate_cxx_metadata 10ms

9 changes: 9 additions & 0 deletions9  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_15132_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,9 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata 11ms

5 changes: 5 additions & 0 deletions5  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_17225_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,5 @@
# C/C++ build system timings
generate_cxx_metadata 16ms

# C/C++ build system timings

16 changes: 16 additions & 0 deletions16  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_17637_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,16 @@
# C/C++ build system timings
generate_cxx_metadata
[gap of 14ms]
create-invalidation-state 45ms
[gap of 25ms]
generate_cxx_metadata completed in 84ms

# C/C++ build system timings
generate_cxx_metadata 14ms

# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings
generate_cxx_metadata 14ms

28 changes: 28 additions & 0 deletions28  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_18456_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,28 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings
generate_cxx_metadata 12ms

# C/C++ build system timings
generate_cxx_metadata 16ms

# C/C++ build system timings
generate_cxx_metadata 15ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

3 changes: 3 additions & 0 deletions3  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_20869_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 12ms

20 changes: 20 additions & 0 deletions20  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_21081_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,20 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 16ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

9 changes: 9 additions & 0 deletions9  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_23073_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,9 @@
# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 11ms

16 changes: 16 additions & 0 deletions16  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_23998_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,16 @@
# C/C++ build system timings
generate_cxx_metadata
[gap of 14ms]
create-invalidation-state 41ms
[gap of 26ms]
generate_cxx_metadata completed in 81ms

# C/C++ build system timings
generate_cxx_metadata 15ms

# C/C++ build system timings
generate_cxx_metadata 19ms

# C/C++ build system timings
generate_cxx_metadata 10ms

19 changes: 19 additions & 0 deletions19  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_25389_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,19 @@
# C/C++ build system timings
generate_cxx_metadata
create-invalidation-state 12ms
[gap of 12ms]
generate_cxx_metadata completed in 30ms

# C/C++ build system timings
generate_cxx_metadata 12ms

# C/C++ build system timings
generate_cxx_metadata 20ms

# C/C++ build system timings

# C/C++ build system timings

# C/C++ build system timings
generate_cxx_metadata 11ms

7 changes: 7 additions & 0 deletions7  
...mm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_35878_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,7 @@
# C/C++ build system timings
generate_cxx_metadata
[gap of 12ms]
create-invalidation-state 46ms
[gap of 25ms]
generate_cxx_metadata completed in 83ms

5 changes: 5 additions & 0 deletions5  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_6740_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,5 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings

6 changes: 6 additions & 0 deletions6  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_7149_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,6 @@
# C/C++ build system timings
generate_cxx_metadata 10ms

# C/C++ build system timings
generate_cxx_metadata 10ms

2 changes: 2 additions & 0 deletions2  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_7812_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,2 @@
# C/C++ build system timings

3 changes: 3 additions & 0 deletions3  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_8021_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,3 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

10 changes: 10 additions & 0 deletions10  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_8233_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,10 @@
# C/C++ build system timings
generate_cxx_metadata 11ms

# C/C++ build system timings
generate_cxx_metadata
[gap of 14ms]
create-invalidation-state 47ms
[gap of 22ms]
generate_cxx_metadata completed in 83ms

6 changes: 6 additions & 0 deletions6  
...omm/build/intermediates/cxx/Debug/4r393vq6/logs/x86/generate_cxx_metadata_9424_timing.txt
Original file line number	Diff line number	Diff line change
@@ -0,0 +1,6 @@
# C/C++ build system timings
generate_cxx_metadata 16ms

# C/C++ build system timings
generate_cxx_metadata 11ms

10 changes: 7 additions & 3 deletions10  
settings.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,3 +1,8 @@
// In settings.gradle.kts

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
repositories {
gradlePluginPortal()
@@ -29,9 +34,6 @@ dependencyResolutionManagement {
}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

rootProject.name = "ReGenesis"

include(
@@ -57,4 +59,6 @@ include(
":list",
":utilities"
)

includeBuild("build-logic")

42 changes: 22 additions & 20 deletions42  
utilities/build.gradle.kts
Original file line number	Diff line number	Diff line change
@@ -1,30 +1,25 @@
plugins {
// JVM library setup
id("java-library")
kotlin("jvm")

    // Additional tooling
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
}


group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

// Centralized toolchain version
val jdkVersion = 24
val jdkVersion = 17

java {
toolchain { languageVersion.set(JavaLanguageVersion.of(jdkVersion)) }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
compilerOptions {
jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
}
}

kotlin {
jvmToolchain(17)
}

dependencies {
// Module dependency (utilities depends on list)
@@ -40,19 +35,26 @@ dependencies {
implementation(libs.xz)

    // Logging API only (no binding at library runtime)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j)

    // Kotlin Standard Library
    implementation(libs.kotlin.stdlib.jdk8)

    // Testing (JUnit 5)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.mockk)
    testImplementation(kotlin("stdlib"))
    // Bind a simple logger only during tests
    testRuntimeOnly(libs.slf4j.simple)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.mockk.android)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
useJUnitPlatform()
}

tasks.register("utilitiesStatus") {
group = "genesis"
doLast {
println("📦 UTILITIES MODULE - Ready!")
}
}
Footer
© 2025 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
GitHub Community
Docs
Contact
Manage cookies
Do not share my personal information