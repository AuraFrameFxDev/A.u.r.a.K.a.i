plugins {
    `kotlin-dsl`
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    id("com.diffplug.spotless") version "6.25.0"
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
        ktlint("1.2.1") // Hardcoded ktlint version; update as needed
            .editorConfigOverride(
                mapOf(
                    "indent_size" to "4",
                    "max_line_length" to "120"
                )
            )
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint("1.2.1") // Hardcoded ktlint version; update as needed
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
    testRuntimeOnly(libs.junit.platform.launcher)

    // Test AndroidX Dependencies
    testImplementation("androidx.activity:activity:1.5.1")
    testImplementation("androidx.annotation:annotation-experimental:1.3.1")
    testImplementation("androidx.fragment:fragment:1.5.1")
    testImplementation("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    testImplementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")
    testImplementation("androidx.savedstate:savedstate:1.2.0")
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
