plugins {
    alias(libs.plugins.seoulmate.android.library)
    alias(libs.plugins.seoulmate.android.compose.library)
}

android {
    namespace = "com.seoulmate.ui"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.core.data)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.adaptive.layout)
    api(libs.androidx.compose.material3.adaptive.navigation)
    api(libs.androidx.compose.material3.windowSizeClass)
    api(libs.androidx.compose.runtime.tracing)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.constraintlayout.compose)
    api(libs.androidx.lifecycle.viewModelCompose)
    api(libs.androidx.lifecycle.runtimeCompose)
    api(libs.accompanist.permissions)
    api("androidx.compose.animation:animation:1.7.8")

    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.animation.graphics.android)

}