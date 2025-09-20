// build-logic/src/main/kotlin/genesis.android.hilt.kts

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

// Hilt requires dependencies to be added
dependencies {
    "implementation"(libs.hilt.android)
    "ksp"(libs.hilt.compiler)
}
