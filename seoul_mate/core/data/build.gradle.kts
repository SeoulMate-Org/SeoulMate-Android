import java.util.Properties

plugins {
    alias(libs.plugins.seoulmate.android.library)
    alias(libs.plugins.seoulmate.hilt)
//    alias(libs.plugins.seoulmate.kotlin.realm)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.seoulmate.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // 개발여부설정 : false
            buildConfigField ("boolean", "DEV", "false")
            buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
        }
        getByName("debug") {
            // 개발여부설정 : true
            buildConfigField("boolean", "DEV", "true")
            buildConfigField("String", "BASE_URL", properties.getProperty("DEV_BASE_URL"))
        }
    }
}

dependencies {

}