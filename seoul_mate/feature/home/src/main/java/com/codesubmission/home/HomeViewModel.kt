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
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
) : ViewModel() {

    var lastLocation = mutableStateOf<Location?>(null)
    var challengeRankList = mutableStateOf(ChallengeInfo.rankList)
    var challengeThemeList = mutableStateOf(ChallengeInfo.challengeThemeList)
    var needUserLogin = mutableStateOf(false)

    var myLikeChallengeList = mutableStateOf(UserInfo.myLikeChallengeList)
    var myProgressChallengeList = mutableStateOf(UserInfo.myProgressChallengeList)
    var myCompleteChallengeList = mutableStateOf(UserInfo.myCompleteChallengeList)

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            lastLocation.value = location

            location?. let {
                UserInfo.myLocationX = it.longitude
                UserInfo.myLocationY = it.latitude
            }
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

                            ChallengeInfo.rankList = challengeRankList.value.map { rankItem ->
                                if (rankItem.id == challengeId) {
                                    rankItem.copy(isLiked = isLiked)
                                } else {
                                    rankItem
                                }
                            }
                            challengeRankList.value = ChallengeInfo.rankList

                            ChallengeInfo.challengeThemeList = challengeThemeList.value.map { challengeList ->
                                challengeList.map { themeItem ->
                                    if (themeItem.id == challengeId) {
                                        themeItem.copy(isLiked = isLiked)
                                    } else {
                                        themeItem
                                    }
                                }
                            }
                            challengeThemeList.value = ChallengeInfo.challengeThemeList
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

    fun getMyChallenge(type: String) {
        if (UserInfo.isUserLogin()) {
            viewModelScope.launch {
                getMyChallengeItemListUseCase(
                    type = type,
                    language = UserInfo.getLanguageCode(),
                ).collectLatest { response ->
                    if (response.code in 200..299) {
                        response.response?.let {
                            when(type) {
                                MyChallengeType.LIKE.type -> {
                                    myLikeChallengeList.value = it
                                    UserInfo.myLikeChallengeList = it
                                }
                                MyChallengeType.PROGRESS.type -> {
                                    myProgressChallengeList.value = it
                                    UserInfo.myProgressChallengeList = it
                                }
                                MyChallengeType.COMPLETE.type -> {
                                    myCompleteChallengeList.value = it
                                    UserInfo.myCompleteChallengeList = it
                                }
                            }
                        }
                    } else if (response.code == 403) {
                        // Refresh Token
                    }
                }
            }
        }

    }
}