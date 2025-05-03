package com.seoulmate.ui.splash

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.user.UserData
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeStampResponseData
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetChallengeCulturalEventUseCase
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
import com.seoulmate.domain.usecase.GetChallengeListRankUseCase
import com.seoulmate.domain.usecase.GetChallengeListStampUseCase
import com.seoulmate.domain.usecase.GetChallengeSeoulMasterUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeItemListUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.GetUserInfoUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashInitData(
    val language: String,
    val userData: UserData?,
    val isFirstEnter: Boolean,
    val lastStampedAttractionId: Int,
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
    private val getChallengeListLocationUseCase: GetChallengeListLocationUseCase,
    private val getChallengeListStampUseCase: GetChallengeListStampUseCase,
    private val getChallengeThemeItemListUseCase: GetChallengeThemeItemListUseCase,
    private val getChallengeListRankUseCase: GetChallengeListRankUseCase,
    private val getChallengeCulturalEventUseCase: GetChallengeCulturalEventUseCase,
    private val getChallengeSeoulMasterUseCase: GetChallengeSeoulMasterUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
): ViewModel() {

    private val _splashInitDataFlow = MutableSharedFlow<SplashInitData>()
    val splashInitDataFlow = _splashInitDataFlow.asSharedFlow()

    var isShowLoading = mutableStateOf<Boolean?>(null)
    var finishedFetchUserInfo = mutableStateOf(false)
    var finishedGetLocation = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var grantedLocationPermission = mutableStateOf(false)

    fun reqSplashInit() {
        viewModelScope.launch {
            isShowLoading.value = true

            val languageFlow = preferDataStoreRepository.loadLanguage()
            val isFirstEnterFlow = preferDataStoreRepository.loadIsFirstEnter()
            val lastLocationRequest = preferDataStoreRepository.loadLastStampedAttractionId()
            val userDataFlow = getUserInfoUseCase()

            combine(languageFlow, isFirstEnterFlow, userDataFlow, lastLocationRequest) { language, isFirstEnter, userData, lastLocationRequest ->
                SplashInitData(language, userData, isFirstEnter, lastLocationRequest)
            }.collectLatest { splashInitData ->
                _splashInitDataFlow.emit(splashInitData)

                if(splashInitData.language.isNotEmpty()) {
                    UserInfo.localeLanguage = splashInitData.language
                }

                if (splashInitData.lastStampedAttractionId >= 0) {
                    UserInfo.lastStampedAttractionId = splashInitData.lastStampedAttractionId
                }

                splashInitData.userData?.let {
                    with(UserInfo) {
                        nickName = it.nickName
                        accessToken = it.accessToken
                        refreshToken = it.refreshToken
                        loginType = it.loginType
                    }
                }
            }

        }
    }

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun getLocation() {
        viewModelScope.launch {
            if (grantedLocationPermission.value) {
                async {
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?. let {
                            UserInfo.myLocationX = it.longitude
                            UserInfo.myLocationY = it.latitude
                        }
                    }
                }.await()
            }
            finishedGetLocation.value = true
        }

    }

    fun reqInit() {
        viewModelScope.launch {
            // My Like Challenge
            val deferredMyLikeChallenge = async {
                var returnResult: CommonDto<List<MyChallengeItemData>>? = null
                getMyChallengeItemListUseCase(
                    type = MyChallengeType.LIKE.type,
                    language = UserInfo.getLanguageCode(),
                ).collectLatest {
                    returnResult = it
                }
                return@async returnResult
            }
            // My Progress Challenge
            val deferredMyProgressChallenge = async {
                var returnResult: CommonDto<List<MyChallengeItemData>>? = null
                getMyChallengeItemListUseCase(
                    type = MyChallengeType.PROGRESS.type,
                    language = UserInfo.getLanguageCode(),
                ).collectLatest {
                    returnResult = it
                }
                return@async returnResult
            }
            // My Completed Challenge
            val deferredMyCompleteChallenge = async {
                var returnResult: CommonDto<List<MyChallengeItemData>>? = null
                getMyChallengeItemListUseCase(
                    type = MyChallengeType.COMPLETE.type,
                    language = UserInfo.getLanguageCode(),
                ).collectLatest {
                    returnResult = it
                }
                return@async returnResult
            }
            // My ChallengeLocation
            val deferredMyChallengeLocation = async {
                var returnResult: CommonDto<ChallengeLocationItemData>? = null
                getChallengeListLocationUseCase(
                    locationRequest = MyLocationReqData(
                        locationX = UserInfo.myLocationY,
                        locationY = UserInfo.myLocationX,
                    ),
                    language = UserInfo.getLanguageCode(),
                ).collectLatest {
                    returnResult = it
                }
                return@async returnResult
            }

            if (UserInfo.isUserLogin()) {
                if (grantedLocationPermission.value) {
                    deferredMyLikeChallenge.await()?.let {
                        if (it.code in 200..299) {
                            UserInfo.myLikeChallengeList = it.response ?: listOf()
                        } else if(it.code == 403) {
                            needRefreshToken.value = true
                            return@launch
                        }
                    }

                    deferredMyProgressChallenge.await()?.let {
                        if (it.code in 200..299) {
                            UserInfo.myProgressChallengeList = it.response ?: listOf()
                        } else if(it.code == 403) {
                            needRefreshToken.value = true
                            return@launch
                        }
                    }

                    deferredMyCompleteChallenge.await()?.let {
                        if (it.code in 200..299) {
                            UserInfo.myCompleteChallengeList = it.response ?: listOf()
                        } else if(it.code == 403) {
                            needRefreshToken.value = true
                            return@launch
                        }
                    }

                    deferredMyChallengeLocation.await()?.let {
                        if (it.code in 200..299) {
                            ChallengeInfo.challengeLocationData = it.response
                        } else if(it.code == 401) {
                            needRefreshToken.value = true
                            return@launch
                        }
                    }
                    finishedFetchUserInfo.value = true
                } else {
                    deferredMyChallengeLocation.await()?.let {
                        if (it.code in 200..299) {
                            ChallengeInfo.challengeLocationData = it.response
                        } else if(it.code == 401) {
                            needRefreshToken.value = true
                            return@launch
                        }
                    }
                    finishedFetchUserInfo.value = true
                }
            } else {
                finishedFetchUserInfo.value = true
            }
        }
    }

    // Fetch Home Challenge Items
    fun reqHomeChallengeItems() {

        viewModelScope.launch {
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
                    needRefreshToken.value = true
                    return@launch
                }
            }

            if (UserInfo.isUserLogin()) {
                // fetch Challenge Stamp Item List
                val deferredChallengeStampList = async {
                    var returnValue: CommonDto<ChallengeStampResponseData?>? = null
                    getChallengeListStampUseCase(
                        attractionId = if(UserInfo.lastStampedAttractionId >=0) {
                            UserInfo.lastStampedAttractionId
                        } else {
                            null
                        },
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
                        needRefreshToken.value = true
                        return@launch
                    }
                }
            }


            isShowLoading.value = false

        }
    }


    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
                    UserInfo.refreshToken = it.refreshToken
                    UserInfo.accessToken = it.accessToken

                    updateUserInfoUseCase(
                        nickName = UserInfo.nickName,
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        loginType = UserInfo.loginType,
                    )

                    needRefreshToken.value = false
                }
            }
        }
    }

}