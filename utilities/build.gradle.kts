plugins {
    `kotlin-dsl`
    alias(libs.plugins.ksp)
    alias(libs.plugins.spotless)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

// Configure Java toolchain
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // Using Java 17 as per project standard
    }
}

// Configure Kotlin compilation
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}

// Configure test tasks
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// Configure Spotless for code formatting
spotless {
    kotlin {
        target("**/*.kt")
        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "indent_size" to "4",
                    "max_line_length" to "120"
                )
            )
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(libs.versions.ktlint.get())
    }
}

dependencies {
    // Kotlin Standard Library
    implementation(kotlin("stdlib-jdk8"))
    
    // Coroutines & Serialization
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)

    // Logging (API only - implementation bound at application level)
    implementation(libs.slf4j.api)
    runtimeOnly(libs.slf4j.simple)

    // Testing
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.testing.unit)
    testImplementation(kotlin("test-junit5"))
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.slf4j.simple)
}

// Status task for CI/CD visibility
tasks.register("utilitiesStatus") {
    group = "aegenesis"
    doLast { 
        println("""
        \uD83D\uDCE6 UTILITIES MODULE STATUS
        ============================
        • Kotlin: ${libs.versions.kotlin.get()}
        • Java: ${JavaVersion.current()}
        • JVM Target: 17
        • Version: $version
        ============================
        """.trimIndent())
    }
}

// Ensure the status task runs during build
tasks.named("build") {
    dependsOn("utilitiesStatus")
}
