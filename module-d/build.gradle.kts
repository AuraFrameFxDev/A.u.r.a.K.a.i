// GENESIS PROTOCOL - MODULE D
plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.module.d"
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Compose dependencies are now included by the genesis.android.compose convention plugin
}

tasks.register("moduleDStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE D - Ready (Java 24)") }
}
