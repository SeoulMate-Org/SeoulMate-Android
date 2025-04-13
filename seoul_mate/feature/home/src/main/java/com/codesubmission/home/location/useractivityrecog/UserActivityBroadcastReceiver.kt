package com.codesubmission.home.location.useractivityrecog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.codesubmission.home.location.useractivityrecog.UserActivityTransitionManager.Companion.getActivityType
import com.codesubmission.home.location.useractivityrecog.UserActivityTransitionManager.Companion.getTransitionType
import com.google.android.gms.location.ActivityTransitionResult

@Composable
fun UserActivityBroadcastReceiver(
    systemAction: String,
    systemEvent: (userActivity: String) -> Unit,
) {
    val context = LocalContext.current
    val currentSystemOnEvent by rememberUpdatedState(systemEvent)

    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val result = intent?.let { ActivityTransitionResult.extractResult(it) } ?: return
                var resultStr = ""
                for (event in result.transitionEvents) {
                    resultStr += "${getActivityType(event.activityType)} " +
                            "- ${getTransitionType(event.transitionType)}"
                }
                Log.d("UserActivityReceiver", "onReceive: $resultStr")
                currentSystemOnEvent(resultStr)
            }

        }
        context.registerReceiver(broadcast, intentFilter)
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}