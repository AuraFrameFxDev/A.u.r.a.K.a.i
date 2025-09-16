plugins {
    kotlin("jvm")
}

group = "dev.aurakai.auraframefx.jvmtest"
version = "1.0.0"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(24)) }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "24"
}

tasks.register("jvmTestStatus") {
    group = "aegenesis"
    doLast { println("\uD83D\uDCE6 JVM TEST MODULE - Ready (Java 24)") }
}

