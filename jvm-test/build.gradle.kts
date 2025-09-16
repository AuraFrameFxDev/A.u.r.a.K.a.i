plugins {
    id("org.jetbrains.kotlin.jvm")
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(24) }
}

kotlin {
    jvmToolchain(23)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_23)
    }
}
