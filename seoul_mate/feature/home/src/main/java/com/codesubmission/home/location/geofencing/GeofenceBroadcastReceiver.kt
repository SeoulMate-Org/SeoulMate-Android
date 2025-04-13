package com.codesubmission.home.location.geofencing

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import com.codesubmission.home.R
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

@Composable
fun GeofenceBroadcastReceiver(
    systemAction: String,
    systemEvent: (userActivity: String) -> Unit,
) {
    val TAG = "GeofenceReceiver"
    val context = LocalContext.current
    val currentSystemOnEvent by rememberUpdatedState(systemEvent)

    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val geofencingEvent = intent?.let { GeofencingEvent.fromIntent(it) } ?: return

                if (geofencingEvent.hasError()) {
                    val errorMessage =
                        GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
                    Log.e(TAG, "onReceive: $errorMessage")
                    return
                }

                context?.let {
                    var geofenceList = ""
                    geofencingEvent.triggeringGeofences?.forEach { geofence ->
                        geofenceList += geofence.requestId
                    }
                    val strContent = "!! ${geofencingEvent.triggeringLocation?.latitude} , ${geofencingEvent.triggeringLocation?.longitude}, [ $geofenceList ] "

                    val builder = NotificationCompat.Builder(it, "CHANNEL_ID")
                        .setSmallIcon(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite)
                        .setContentTitle("SeoulMate 😎")
                        .setContentText(strContent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    with(NotificationManagerCompat.from(it)) {
                        if (ActivityCompat.checkSelfPermission(
                                it,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }

                        //TODO chan change notify id
                        notify(999, builder.build())
                    }
                }

            }
        }
        context.registerReceiver(broadcast, intentFilter, Context.RECEIVER_EXPORTED)
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}
