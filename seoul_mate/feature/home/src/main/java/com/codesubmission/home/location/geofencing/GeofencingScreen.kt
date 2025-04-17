package com.codesubmission.home.location.geofencing

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.codesubmission.home.location.utils.CUSTOM_INTENT_GEOFENCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("InlinedApi")
@Composable
fun GeofencingScreen(
    context: Context,
    locationPermissionList: List<String>,
    backgroundLocationPermission: String?,
) {
    var canPass = true
    locationPermissionList.forEach { permission ->
        if (ActivityCompat.checkSelfPermission(
                context,
                permission,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            canPass = false
        }
    }

    backgroundLocationPermission?.let {
        if (canPass) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    it,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                canPass = false
            }
        }
    }

    if (canPass) {
        GeofencingControls()
    }

}

@Composable
private fun GeofencingControls() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val geofenceManager = remember { GeofenceManager(context) }
//    var geofenceTransitionEventInfo by remember {
//        mutableStateOf("")
//    }

//    DisposableEffect(LocalLifecycleOwner.current) {
//        onDispose {
//            scope.launch(Dispatchers.IO) {
//                geofenceManager.deregisterGeofence()
//            }
//        }
//    }

    // Register a local broadcast to receive activity transition updates
    GeofenceBroadcastReceiver(systemAction = CUSTOM_INTENT_GEOFENCE) { event ->
//        geofenceTransitionEventInfo = event
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GeofenceList(geofenceManager)
        Button(
            onClick = {
                if (geofenceManager.geofenceList.isNotEmpty()) {
                    geofenceManager.registerGeofence()
                } else {
                    Toast.makeText(
                        context,
                        "Please add at least one geofence to monitor",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
        ) {
            Text(text = "Register Geofences")
        }

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    geofenceManager.deregisterGeofence()
                }
            },
        ) {
            Text(text = "Deregister Geofences")
        }
        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = geofenceTransitionEventInfo)
    }

}

@Composable
fun GeofenceList(geofenceManager: GeofenceManager) {
    // for geofences
    val checkedGeoFence1 = remember { mutableStateOf(false) }
    val checkedGeoFence2 = remember { mutableStateOf(false) }
    val checkedGeoFence3 = remember { mutableStateOf(false) }
    val checkedGeoFence4 = remember { mutableStateOf(false) }
    val checkedGeoFence5 = remember { mutableStateOf(false) }

    Text(text = "Available Geofence")
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence1.value,
            onCheckedChange = { checked ->
                if (checked) {
                    geofenceManager.addGeofence(
                        "home",
                        location = Location("").apply {
                            latitude = 37.4857202
                            longitude = 126.9312537
                        },
                    )
                } else {
                    geofenceManager.removeGeofence("home")
                }
                checkedGeoFence1.value = checked
            },
        )
        Text(text = "HOME")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence2.value,
            onCheckedChange = { checked ->
                if (checked) {
                    geofenceManager.addGeofence(
                        "subway",
                        location = Location("").apply {
                            latitude = 37.484281
                            longitude = 126.9298899
                        },
                    )
                } else {
                    geofenceManager.removeGeofence("subway")
                }
                checkedGeoFence2.value = checked
            },
        )
        Text(text = "SUBWAY")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence3.value,
            onCheckedChange = { checked ->
                if (checked) {
                    geofenceManager.addGeofence(
                        "woongjin",
                        location = Location("").apply {
                            latitude = 37.5686656
                            longitude = 126.9801286
                        },
                    )
                } else {
                    geofenceManager.removeGeofence("woongjin")
                }
                checkedGeoFence3.value = checked
            },
        )
        Text(text = "WOONGJIN")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence4.value,
            onCheckedChange = { checked ->
                if (checked) {
                    geofenceManager.addGeofence(
                        "하이커",
                        location = Location("").apply {
                            latitude = 37.5686076
                            longitude = 126.9816627
                        },
                    )
                } else {
                    geofenceManager.removeGeofence("하이커")
                }
                checkedGeoFence4.value = checked
            },
        )
        Text(text = "하이커")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence5.value,
            onCheckedChange = { checked ->
                if (checked) {
                    geofenceManager.addGeofence(
                        "hapjeong",
                        location = Location("").apply {
                            latitude = 37.5492836
                            longitude = 126.9135368
                        },
                    )
                } else {
                    geofenceManager.removeGeofence("hapjeong")
                }
                checkedGeoFence5.value = checked
            },
        )
        Text(text = "HAPJWONG")
    }
}
