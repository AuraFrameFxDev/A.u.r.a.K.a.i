// settings.gradle.kts

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        // maven("https://jitpack.io") // JitPack for YukiHookAPI if needed by any plugin itself
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // Added JitPack for YukiHookAPI, LSPosed, etc.
        // maven("https://api.xposed.info/") // For Xposed/LSPosed if direct dependencies need it and aren't on JitPack/MavenCentral
    }
}

rootProject.name = "Re_Genesis_A.O.S.P"

// ====== CORE & INFRASTRUCTURE ======
include(":app")
include(":core-module")
include(":build-logic") // Convention plugins
include(":utilities")   // Common utilities, potentially Xposed APIs
// include(":instrumentation-tests") // For shared instrumentation test code

// ====== FEATURES ======
include(":feature-module")        // Generic feature module, if used as a template or base
include(":collab-canvas")         // Collaborative drawing/whiteboarding
include(":colorblendr")           // Color manipulation/theming tools
include(":datavein-oracle-native") // Native components for DataVein/OracleDrive
include(":oracle-drive-integration") // High-level OracleDrive integration
include(":romtools")              // Tools for ROM modification/management
include(":sandbox-ui")            // Isolated UI experiments/prototyping
include(":secure-comm")           // Secure communication components

// ====== UI & THEME RELATED ======
// include(":theme-engine")       // If you have a dedicated theme engine module
// include(":component-library")  // Shared UI components

// ====== AI & BACKEND RELATED ======
// include(":ai-core")            // Core AI logic, agent definitions
// include(":ai-backend-bindings") // Bindings to your Python backend
// include(":genesis-consciousness-matrix") // Specific module for the matrix

// ====== SPECIALIZED FUNCTIONALITY ======
// include(":xposed-module-template") // If you have a template for Xposed modules
// include(":lsposed-bridge")        // Specific LSPosed interaction layer

// ====== TESTING & DEBUGGING ======
// include(":debug-tools")        // Custom debug utilities module
include(":benchmark")             // For Jetpack Benchmark

// ====== DATA & DOMAIN ======
// include(":domain")             // Pure Kotlin domain logic
// include(":data")               // Data layer, repositories, sources

// ====== LIST (currently being worked on) ======
include(":list")
// ====== PROTOTYPING & MISC ======
// Placeholder for potential future modules that don't fit elsewhere yet
// include(":prototyping-area")
// include(":module-a") // Example modules from initial setup, clean up if not needed
// include(":module-b")
// include(":module-c")
// include(":module-d")
// include(":module-e")
// include(":module-f")
// include(":jvm-test") // If you have specific JVM test modules
