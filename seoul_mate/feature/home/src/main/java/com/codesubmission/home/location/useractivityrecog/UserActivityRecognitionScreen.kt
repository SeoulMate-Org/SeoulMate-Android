package com.codesubmission.home.location.useractivityrecog

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.codesubmission.home.location.utils.CUSTOM_INTENT_USER_ACTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun UserActivityRecognitionScreen() {
    UserActivityRecognitionContent()
}

@SuppressLint("InlinedApi")
@RequiresPermission(
    anyOf = [
        Manifest.permission.ACTIVITY_RECOGNITION,
        "com.google.android.gms.permission.ACTIVITY_RECOGNITION",
    ],
)
@Composable
fun UserActivityRecognitionContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val manager = remember {
        UserActivityTransitionManager(context)
    }
    var currentUserActivity by remember {
        mutableStateOf("Unknown")
    }

    // Calling deregister on dispose
    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            scope.launch(Dispatchers.IO) {
                manager.deregisterActivityTransitions()
            }
        }
    }

    // Register a local broadcast to receive activity transition updates
    UserActivityBroadcastReceiver(systemAction = CUSTOM_INTENT_USER_ACTION) { userActivity ->
        currentUserActivity = userActivity
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    manager.registerActivityTransitions()
                }

            },
        ) {
            Text(text = "Register for activity transition updates")
        }
        Button(
            onClick = {
                currentUserActivity = ""
                scope.launch(Dispatchers.IO) {
                    manager.deregisterActivityTransitions()
                }
            },
        ) {
            Text(text = "Deregister for activity transition updates")
        }
        if (currentUserActivity.isNotBlank()) {
            Text(
                text = "CurrentActivity is = $currentUserActivity",
            )
        }
    }
}