package com.codesubmission.home.location.geofencing

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.animation.EnterTransition
import com.codesubmission.home.GeofenceBroadcastReceiver
import com.codesubmission.home.location.utils.CUSTOM_INTENT_GEOFENCE
import com.codesubmission.home.location.utils.CUSTOM_REQUEST_CODE_GEOFENCE
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_DWELL
import com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_ENTER
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class GeofenceManager(context: Context) {
    private val TAG = "@@@@GeofenceManager"
    private val client = LocationServices.getGeofencingClient(context)
    val geofenceList = mutableMapOf<String, Geofence>()

    private val geofencingPendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        PendingIntent.getBroadcast(
            context,
            CUSTOM_REQUEST_CODE_GEOFENCE,
            intent,
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                PendingIntent.FLAG_CANCEL_CURRENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT
            } else {
                PendingIntent.FLAG_MUTABLE
            }
        )
    }

    fun addGeofence(
        key: String,
        location: Location,
        radiusInMeters: Float = 100.0f,
    ) {
        Log.d(TAG, "addGeofence key: $key")
        geofenceList[key] = createGeofence(key, location, radiusInMeters)
    }

    fun removeGeofence(key: String) {
        geofenceList.remove(key)
    }

    @SuppressLint("MissingPermission")
    fun registerGeofence() {
        client.addGeofences(createGeofencingRequest(), geofencingPendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "registerGeofence: SUCCESS")
                createGeofencingRequest().geofences.forEach {
                    Log.d(TAG, "registerGeofence: ${it.requestId}, ${it.latitude} , ${it.longitude}")
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "registerGeofence: Failure\n$exception")
            }
    }

    suspend fun deregisterGeofence() = kotlin.runCatching {
        client.removeGeofences(geofencingPendingIntent).await()
        geofenceList.clear()
    }

    private fun createGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GEOFENCE_TRANSITION_ENTER)
            addGeofences(geofenceList.values.toList())
        }.build()
    }

    private fun createGeofence(
        key: String,
        location: Location,
        radiusInMeters: Float,
    ): Geofence {
        return Geofence.Builder()
            .setRequestId(key)
            .setCircularRegion(location.latitude, location.longitude, radiusInMeters)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(GEOFENCE_TRANSITION_ENTER)
            .setLoiteringDelay(1000 * 60)
//            .setNotificationResponsiveness(1000 * 60 * 3)
            .build()
    }
}