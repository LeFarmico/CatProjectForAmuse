import Versions.coil_version
import Versions.compose_version
import Versions.ktor_version

object BuildPlugins {

    // Core
    const val androidTools = "com.android.tools.build:gradle:7.2.2"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:1.7.21"
}

object Deps {

    // Core
    const val core = "androidx.core:core-ktx:1.9.0"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    const val activityCompose = "androidx.activity:activity-compose:1.6.1"

    // Compose
    const val composeUI = "androidx.compose.ui:ui:$compose_version"
    const val composeMaterial = "androidx.compose.material:material:$compose_version"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview:$compose_version"
    const val composeUITooling = "androidx.compose.ui:ui-tooling:$compose_version"
    const val composeUITestManifest = "androidx.compose.ui:ui-test-manifest:$compose_version"

    // Testing
    const val junit = "junit:junit:4.13.2"
    const val junitExt = "androidx.test.ext:junit:1.1.4"
    const val espresso = "androidx.test.espresso:espresso-core:3.5.0"
    const val composeUITestJunit = "androidx.compose.ui:ui-test-junit4:$compose_version"

    // Ktor
    const val ktorClientCore = "io.ktor:ktor-client-core:$ktor_version"
    const val ktorClientCio = "io.ktor:ktor-client-cio:$ktor_version"
    const val ktorSerialization = "io.ktor:ktor-serialization-kotlinx-json:$ktor_version"
    const val ktorClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktor_version"

    // Coil
    const val coil = "io.coil-kt:coil-compose:$coil_version"
}

object Versions {
    const val compose_version = "1.4.0-alpha02"
    const val ktor_version = "2.1.3"
    const val coil_version = "2.2.2"
}