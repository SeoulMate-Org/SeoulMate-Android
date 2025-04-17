plugins {
    alias(libs.plugins.seoulmate.android.library)
    alias(libs.plugins.seoulmate.hilt)
}

android {
    namespace = "com.seoulmate.datastore"

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
    api(libs.androidx.datastore.preferences)
    api(libs.androidx.datastore.preferences.core)
}