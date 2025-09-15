// ==== GENESIS PROTOCOL - FEATURE MODULE ====
// Primary feature module using convention plugins


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
    }
}

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    // Note: Hilt plugin removed to avoid Android BaseExtension issues, using manual dependencies instead
}


android {
    namespace = "dev.aurakai.auraframefx.feature"
    compileSdk = 36
    compileSdkPreview = "CANARY"

    defaultConfig {
        minSdk = 34
    }

    // Configure Java compilation
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
    
    // Configure Kotlin compilation using the new compilerOptions DSL
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }
}

dependencies {
    api(project(":core-module"))
    implementation(libs.androidx.core.ktx)
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
    implementation(platform(libs.firebase.bom))
    implementation(libs.timber)
    implementation(libs.coil.compose)
    implementation(fileTree("../Libs") { include("*.jar") })
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.kotlin.stdlib.jdk8)}

    tasks.register("featureStatus") {
        group = "aegenesis"
        doLast { println("🚀 FEATURE MODULE - ${android.namespace} - Ready (Java 8)!") }
    }
