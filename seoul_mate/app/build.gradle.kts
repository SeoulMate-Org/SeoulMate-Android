import java.util.Properties

plugins {
    alias(libs.plugins.seoulmate.android.application)
    alias(libs.plugins.seoulmate.android.compose.application)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.seoulmate.android.firebase)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    
    namespace = "com.seoulmate"

    defaultConfig {
        applicationId = "com.seoulmate"
        versionCode = 5
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

            buildConfigField("String", "NAVER_MAP_KEY", properties.getProperty("NAVER_MAP_KEY"))
            buildConfigField("String", "FACEBOOK_CLIENT_TOKEN", properties.getProperty("FACEBOOK_CLIENT_TOKEN"))
            buildConfigField("String", "FACEBOOK_APP_ID", properties.getProperty("FACEBOOK_APP_ID"))
            buildConfigField("String", "FACEBOOK_FB_SCHEME", properties.getProperty("FACEBOOK_FB_SCHEME"))
            manifestPlaceholders["NAVER_MAP_KEY"] = properties["NAVER_MAP_KEY"] as String

            resValue("string", "FACEBOOK_APP_ID", properties.getProperty("FACEBOOK_APP_ID"))
            resValue("string", "FACEBOOK_CLIENT_TOKEN", properties.getProperty("FACEBOOK_CLIENT_TOKEN"))

            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            buildConfigField("String", "NAVER_MAP_KEY", properties.getProperty("NAVER_MAP_KEY"))
            buildConfigField("String", "FACEBOOK_CLIENT_TOKEN", properties.getProperty("FACEBOOK_CLIENT_TOKEN"))
            buildConfigField("String", "FACEBOOK_APP_ID", properties.getProperty("FACEBOOK_APP_ID"))
            buildConfigField("String", "FACEBOOK_FB_SCHEME", properties.getProperty("FACEBOOK_FB_SCHEME"))
            manifestPlaceholders["NAVER_MAP_KEY"] = properties["NAVER_MAP_KEY"] as String

            resValue("string", "FACEBOOK_APP_ID", properties.getProperty("FACEBOOK_APP_ID"))
            resValue("string", "FACEBOOK_CLIENT_TOKEN", properties.getProperty("FACEBOOK_CLIENT_TOKEN"))
        }
    }

    // Specifies one flavor dimension.
//    flavorDimensions += "version"
//    productFlavors {
//        create("dev") {
//            dimension = "version"
//            applicationIdSuffix = ".dev"
//            versionNameSuffix = "-dev"
//        }
//        create("live") {
//            dimension = "version"
//            applicationIdSuffix = ".live"
//            versionNameSuffix = "-live"
//        }
//    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)
    implementation(projects.navigator)
    implementation(projects.feature.interest)
    implementation(projects.feature.home)
    implementation(projects.feature.places)
    implementation(projects.feature.login)
    implementation(projects.feature.challenge)
    implementation(projects.feature.settings)

    // 네이버 지도 SDK
    implementation(libs.naver.map.compose)
    implementation(libs.naver.map.location)
    implementation(libs.naver.map.sdk)

    implementation(libs.facebook.login)

    implementation(libs.androidx.activity)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.coil.kt)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.moshi.kotlin)
    implementation(libs.android.gms.location)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}