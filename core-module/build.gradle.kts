plugins {
    id("genesis.android.library")
    id("genesis.android.compose")
    id("genesis.android.hilt")
}

android {
    namespace = "dev.aurakai.auraframefx.core"
}

dependencies {
    api(project(":list"))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)

    // Hilt dependencies are now handled by the genesis.android.hilt convention

    // Testing dependencies
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
    implementation(libs.androidx.core.ktx)
}
