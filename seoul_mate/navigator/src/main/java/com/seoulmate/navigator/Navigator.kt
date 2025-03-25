package com.seoulmate.navigator

import android.app.Activity
import android.content.Intent

interface Navigator {
    fun navigateFrom(
        activity: Activity,
        intentBuilder: Intent.() -> Intent = { this },
        withFinish: Boolean = true,
    )

    fun containResultNavigateFrom(
        activity: Activity,
//        activityResultLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        intentBuilder: Intent.() -> Intent,
        withFinish: Boolean = true,
    )
}