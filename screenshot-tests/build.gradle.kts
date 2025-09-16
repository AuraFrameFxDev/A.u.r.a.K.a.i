// Placeholder module for screenshot-tests to satisfy task paths like :screenshot-tests:assemble.
// This applies the 'base' plugin which provides the 'assemble' lifecycle task without requiring Android/Java configuration.
plugins {
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
