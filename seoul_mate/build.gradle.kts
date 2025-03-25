// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    // 아래의 에러 발생 대응 25.03.25
    // Unable to make progress running work. There are items queued for execution but none of them can be started
    gradle.startParameter.excludedTaskNames.addAll(
        gradle.startParameter.taskNames.filter { it.contains("testClasses") }
    )
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.room) apply false
}