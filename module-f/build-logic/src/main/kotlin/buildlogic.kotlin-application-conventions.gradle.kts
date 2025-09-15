/*
 * Application conventions for AeGenesis project
 * Applies common conventions and configures application-specific settings
 */

plugins {
    id("application")
    id("org.jetbrains.kotlin.jvm")
}

// Note: Do NOT use the application { ... } DSL in precompiled script plugins.
// Set mainClass in your module's build.gradle.kts, e.g.:
// application {
//     mainClass.set("com.example.MainKt")
// }

// Configure test tasks
tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// Configure run task to use standard input
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Configure JAR manifest
tasks.named<Jar>("jar") {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}
