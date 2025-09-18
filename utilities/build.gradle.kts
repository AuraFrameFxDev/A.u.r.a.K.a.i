// build.gradle.kts for the 'utilities' module

plugins {
    alias(libs.plugins.kotlin.jvm) // Use kotlin-jvm for a standard JVM module
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.spotless)
}

group = "dev.aurakai.auraframefx.utilities"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

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
    // Coroutines & Serialization
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // File operations and compression
    implementation(libs.commons.io)
    implementation(libs.commons.compress)
    implementation(libs.xz)
    runtimeOnly(libs.slf4j.simple)

    // Testing
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.slf4j.simple)
}

tasks.register("utilitiesStatus") {
    group = "aegenesis"
    doLast {
        println("""
        \uD83D\uDCE6 UTILITIES MODULE STATUS
        ============================
        • Kotlin: ${libs.versions.kotlin.get()}
        • Java: ${java.toolchain.languageVersion.get()}
        • JVM Target: 24
        • Version: $version
        ============================
        """.trimIndent())
    }
}

tasks.named("build") {
    dependsOn("utilitiesStatus")
}
