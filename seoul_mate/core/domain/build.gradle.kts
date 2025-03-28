plugins {
    alias(libs.plugins.seoulmate.android.library)
    alias(libs.plugins.seoulmate.hilt)
}

android {
    namespace = "com.seoulmate.domain"

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
    
    implementation(libs.moshi.kotlin)

}