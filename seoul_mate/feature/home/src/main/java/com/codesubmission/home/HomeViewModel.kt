package com.codesubmission.home

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.seoulmate.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    var lastLocation = mutableStateOf<Location?>(null)

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            lastLocation.value = location

            UserInfo.myLocationX = location?.latitude ?: 0.0
            UserInfo.myLocationY = location?.longitude ?: 0.0
        }
    }

    fun getLocationDistance(myLocation: Location, point: Location): Float =
        myLocation.distanceTo(point)
}