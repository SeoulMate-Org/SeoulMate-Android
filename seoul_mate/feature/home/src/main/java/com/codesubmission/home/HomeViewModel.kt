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
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.data.model.challenge.ChallengeStampResponseData
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.domain.usecase.GetChallengeCulturalEventUseCase
import com.seoulmate.domain.usecase.GetChallengeListRankUseCase
import com.seoulmate.domain.usecase.GetChallengeListStampUseCase
import com.seoulmate.domain.usecase.GetChallengeSeoulMasterUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeItemListUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeAfterRefreshTokenType {
    data object LikeChallenge: HomeAfterRefreshTokenType()
    data object PullToRefresh: HomeAfterRefreshTokenType()
    data object MyChallengeLike: HomeAfterRefreshTokenType()
    data object MyChallengeProgress: HomeAfterRefreshTokenType()
    data object MyChallengeComplete: HomeAfterRefreshTokenType()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getChallengeThemeItemListUseCase: GetChallengeThemeItemListUseCase,
    private val getChallengeListRankUseCase: GetChallengeListRankUseCase,
    private val getChallengeCulturalEventUseCase: GetChallengeCulturalEventUseCase,
    private val getChallengeSeoulMasterUseCase: GetChallengeSeoulMasterUseCase,
    private val getChallengeListStampUseCase: GetChallengeListStampUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : ViewModel() {

    var challengeRankList = mutableStateOf(ChallengeInfo.rankList)
    var challengeThemeList = mutableStateOf(ChallengeInfo.challengeThemeList)
    var needUserLogin = mutableStateOf(false)
    var isTimerOver = mutableStateOf(false)
    var isRefreshing = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var afterRefreshToken = mutableStateOf<HomeAfterRefreshTokenType?>(null)
    var afterRefreshTokenChallengeId = mutableStateOf<Int?>(null)

    var myLikeChallengeList = mutableStateOf(UserInfo.myLikeChallengeList)
    var myProgressChallengeList = mutableStateOf(UserInfo.myProgressChallengeList)
    var myCompleteChallengeList = mutableStateOf(UserInfo.myCompleteChallengeList)

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun getLastLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
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
                            afterRefreshTokenChallengeId.value = challengeId
                            afterRefreshToken.value = HomeAfterRefreshTokenType.LikeChallenge
                            needRefreshToken.value = true
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
                        when(type) {
                            MyChallengeType.LIKE.type -> {
                                afterRefreshToken.value = HomeAfterRefreshTokenType.MyChallengeLike
                            }
                            MyChallengeType.PROGRESS.type -> {
                                afterRefreshToken.value = HomeAfterRefreshTokenType.MyChallengeProgress
                            }
                            MyChallengeType.COMPLETE.type -> {
                                afterRefreshToken.value = HomeAfterRefreshTokenType.MyChallengeComplete
                            }
                        }
                        needRefreshToken.value = true
                    }
                }
            }
        }

    }

    fun startTimer() {
        viewModelScope.launch {
            isTimerOver.value = false
            delay(1000 * 60 * 5)
            isTimerOver.value = true
        }
    }

    // TODO chan 시간 비교
    fun reqHomeChallengeItems() {
        // Fetch Home Challenge Items
        viewModelScope.launch {
            isRefreshing.value = true

            // fetch Challenge Theme Item List
            val deferredList = mutableListOf<Deferred<CommonDto<List<ChallengeThemeItemData>>?>>()
            for(i in 1..9) {
                deferredList.add(
                    async {
                        var returnValue: CommonDto<List<ChallengeThemeItemData>>? = null
                        getChallengeThemeItemListUseCase(i, UserInfo.getLanguageCode()).collectLatest {
                            returnValue = it
                        }
                        return@async returnValue
                    }
                )
            }

            val themeList = mutableListOf<List<ChallengeThemeItemData>>()
            deferredList.forEach { item ->
                val valueDeferred = item.await()
                valueDeferred?.let {
                    if(it.code in 200..299) {
                        themeList.add(it.response ?: listOf())
                    } else if (it.code == 403) {
                        afterRefreshToken.value = HomeAfterRefreshTokenType.PullToRefresh
                        needRefreshToken.value = true
                        return@launch
                    }
                }
            }
            ChallengeInfo.challengeThemeList = themeList.toList()

            // fetch Challenge Rank Item List
            val deferredRankList = async {
                var returnValue: CommonDto<List<ChallengeRankItemData>>? = null
                getChallengeListRankUseCase(UserInfo.getLanguageCode()).collectLatest {
                    returnValue = it
                }
                return@async returnValue
            }
            deferredRankList.await()?.let {
                if(it.code in 200..299) {
                    ChallengeInfo.rankList = it.response ?: listOf()
                } else if (it.code == 403) {
                    afterRefreshToken.value = HomeAfterRefreshTokenType.PullToRefresh
                    needRefreshToken.value = true
                    return@launch
                }
            }

            // fetch Challenge Cultural Event Item List
            val deferredCulturalEventList = async {
                var returnValue: CommonDto<List<ChallengeCulturalEventData>>? = null
                getChallengeCulturalEventUseCase(UserInfo.getLanguageCode()).collectLatest {
                    returnValue = it
                }
                return@async returnValue
            }
            deferredCulturalEventList.await()?.let {
                if(it.code in 200..299) {
                    ChallengeInfo.challengeCulturalList = it.response ?: listOf()
                } else if (it.code == 403) {
                    afterRefreshToken.value = HomeAfterRefreshTokenType.PullToRefresh
                    needRefreshToken.value = true
                    return@launch
                }
            }

            // fetch Challenge Seoul Master Item List
            val deferredSeoulMasterList = async {
                var returnValue: CommonDto<List<ChallengeCulturalEventData>>? = null
                getChallengeSeoulMasterUseCase(UserInfo.getLanguageCode()).collectLatest {
                    returnValue = it
                }
                return@async returnValue
            }
            deferredSeoulMasterList.await()?.let {
                if(it.code in 200..299) {
                    ChallengeInfo.challengeSeoulMasterList = it.response ?: listOf()
                } else if (it.code == 403) {
                    afterRefreshToken.value = HomeAfterRefreshTokenType.PullToRefresh
                    needRefreshToken.value = true
                    return@launch
                }
            }

            if (UserInfo.isUserLogin()) {
                // fetch Challenge Stamp Item List
                val deferredChallengeStampList = async {
                    var returnValue: CommonDto<ChallengeStampResponseData?>? = null
                    getChallengeListStampUseCase(
                        attractionId = null,
                        language = UserInfo.getLanguageCode(),
                    ).collectLatest {
                        returnValue = it
                    }
                    return@async returnValue
                }
                deferredChallengeStampList.await()?.let {
                    if (it.code in 200..299) {
                        ChallengeInfo.challengeStampData = it.response
                    } else if(it.code == 403) {
                        afterRefreshToken.value = HomeAfterRefreshTokenType.PullToRefresh
                        needRefreshToken.value = true
                        return@launch
                    }
                }
            }

            isRefreshing.value = false

        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
                    UserInfo.refreshToken = it.refreshToken
                    UserInfo.accessToken = it.accessToken

                    needRefreshToken.value = false
                }
            }
        }
    }
}