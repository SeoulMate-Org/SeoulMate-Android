import java.util.Properties

plugins {
    alias(libs.plugins.seoulmate.android.library)
    alias(libs.plugins.seoulmate.hilt)
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
            buildConfigField("String", "NAVER_MAP_BASE_URL", properties.getProperty("NAVER_MAP_URL"))
            buildConfigField("String", "GOOGLE_CLOUD_CLIENT_ID", properties.getProperty("GOOGLE_CLOUD_CLIENT_ID"))
        }
        getByName("debug") {
            // 개발여부설정 : true
            buildConfigField("boolean", "DEV", "true")
            buildConfigField("String", "BASE_URL", properties.getProperty("DEV_BASE_URL"))
            buildConfigField("String", "NAVER_MAP_BASE_URL", properties.getProperty("NAVER_MAP_URL"))
            buildConfigField("String", "GOOGLE_CLOUD_CLIENT_ID", properties.getProperty("GOOGLE_CLOUD_CLIENT_ID"))
        }
    }
}

dependencies {
    implementation(projects.core.datastore)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp.profiler)
    implementation("com.google.code.gson:gson:2.13.1")

    implementation(libs.android.gms.location)

    implementation(libs.android.google.id)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
}