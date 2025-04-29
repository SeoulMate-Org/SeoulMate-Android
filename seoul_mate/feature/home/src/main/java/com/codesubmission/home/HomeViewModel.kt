package com.codesubmission.home

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
) : ViewModel() {

    var lastLocation = mutableStateOf<Location?>(null)
    var challengeRankList = mutableStateOf(ChallengeInfo.rankList)
    var challengeThemeList = mutableStateOf(ChallengeInfo.challengeThemeList)
    var needUserLogin = mutableStateOf(false)

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

    fun reqChallengeLike(challengeId: Int) {
        viewModelScope.launch {
            reqChallengeLikeUseCase(challengeId).collectLatest { response ->
                response?.let {
                    if (it.code in 200..299) {
                        it.response?.let { likedResponse ->
                            val isLiked = likedResponse.isLiked

                            challengeRankList.value = challengeRankList.value.map { rankItem ->
                                if (rankItem.id == challengeId) {
                                    rankItem.copy(isLiked = isLiked)
                                } else {
                                    rankItem
                                }
                            }

                            challengeThemeList.value.forEach { themeList ->
                                themeList.map { themeItem ->
                                    if (themeItem.id == challengeId) {
                                        themeItem.copy(isLiked = isLiked)
                                    } else {
                                        themeItem
                                    }
                                }
                            }
                        }

                    } else if (it.code == 403) {
                        if (UserInfo.isUserLogin()) {
                            // Refresh Token
                        } else {
                            // Login
                            needUserLogin.value = true
                            Log.d("@@@@", "HomeView Like Need Login")
                        }
                    }
                }

            }
        }
    }
}