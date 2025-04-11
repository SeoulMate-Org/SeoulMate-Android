package com.seoulmate.navigator

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult

interface ActivityNavigator {
    fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent = { this },
        withFinish: Boolean = true,
    )

    fun containResultNavigateFrom(
        activity: Activity,
        activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean = true,
    )
}

interface MainActivityNavigator : ActivityNavigator

interface LoginActivityNavigator : ActivityNavigator