package com.seoulmate.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeStampResponseData
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.model.user.UserInfoData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
import com.seoulmate.domain.usecase.GetChallengeListRankUseCase
import com.seoulmate.domain.usecase.GetChallengeListStampUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeItemListUseCase
import com.seoulmate.domain.usecase.GetLoginInfoUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.GetMyPageUserInfoUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.SaveUserInfoUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginInfoUseCase: GetLoginInfoUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
    private val getChallengeListLocationUseCase: GetChallengeListLocationUseCase,
    private val getChallengeThemeItemListUseCase: GetChallengeThemeItemListUseCase,
    private val getChallengeListRankUseCase: GetChallengeListRankUseCase,
    private val getChallengeListStampUseCase: GetChallengeListStampUseCase,
    private val getMyPageUserInfoUseCase: GetMyPageUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
) : ViewModel() {

    var isShowLoading = mutableStateOf<Boolean?>(null)
    var isSuccessLogin = mutableStateOf(false)
    var finishedFetchMyData = mutableStateOf<Boolean?>(null)
    var finishedFetchHomeItems = mutableStateOf(false)
    var isNewUser = mutableStateOf(false)
    var isFirstEnter = mutableStateOf<Boolean?>(null)
    var needRefreshToken = mutableStateOf<Boolean?>(null)

    fun getLoginInfo(token: String, loginType: String) {
        viewModelScope.launch {
            isShowLoading.value = true
            getLoginInfoUseCase.getLoginInfo(
                token = token,
                loginType = loginType,
                language = UserInfo.getLanguageCode(),
            ).collectLatest {
                it?.let {
                    Log.d("LoginViewModel", "getLoginInfo: $it")
                    saveUserInfoUseCase(
                        nickName = it.nickName,
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        loginType = it.loginType,
                    )
                    with(UserInfo) {
                        this.nickName = it.nickName
                        this.accessToken = it.accessToken
                        this.refreshToken = it.refreshToken
                        this.loginType = it.loginType
                    }
                    isNewUser.value = it.isNewUser
                    isSuccessLogin.value = true
                }
            }
        }
    }

    fun getMyData(grantedLocationPermission: Boolean = false) {
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

            if (grantedLocationPermission) {
                deferredMyLikeChallenge.await()?.let {
                    if (it.code in 200..299) {
                        UserInfo.myLikeChallengeList = it.response ?: listOf()
                    } else if (it.code == 403) {
                        needRefreshToken.value = true
                        return@launch
                    }
                }

                deferredMyProgressChallenge.await()?.let {
                    if (it.code in 200..299) {
                        UserInfo.myProgressChallengeList = it.response ?: listOf()
                    } else if (it.code == 403) {
                        needRefreshToken.value = true
                        return@launch
                    }
                }

                deferredMyCompleteChallenge.await()?.let {
                    if (it.code in 200..299) {
                        UserInfo.myCompleteChallengeList = it.response ?: listOf()
                    } else if (it.code == 403) {
                        needRefreshToken.value = true
                        return@launch
                    }
                }

                deferredMyChallengeLocation.await()?.let {
                    if (it.code in 200..299) {
                        ChallengeInfo.challengeLocationData = it.response
                    } else if (it.code == 403) {
                        needRefreshToken.value = true
                        return@launch
                    }
                }
                isShowLoading.value = false
                finishedFetchMyData.value = true
            } else {
                deferredMyChallengeLocation.await()?.let {
                    if (it.code in 200..299) {
                        ChallengeInfo.challengeLocationData = it.response
                    } else if (it.code == 403) {
                        needRefreshToken.value = true
                        return@launch
                    }
                }
                isShowLoading.value = false
                finishedFetchMyData.value = true
            }
        }

    }

    fun loadIsFirstEnter() {
        viewModelScope.launch {
            preferDataStoreRepository.loadIsFirstEnter().collect {
                if (isFirstEnter.value == null) {
                    isFirstEnter.value = it
                }
                if (it) {
                    updateIsFirstEnter()
                }
            }
        }
    }

    private fun updateIsFirstEnter() {
        viewModelScope.launch {
            preferDataStoreRepository.updateIsFirstEnter(false)
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

            // Fetch Challenge Rank Item List
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
                } else if (it.code == 403) {
                    needRefreshToken.value = true
                    return@launch
                }
            }

            // fetch MyPage Info
            val deferredMyPageInfo = async {
                var returnValue: CommonDto<UserInfoData?>? = null
                getMyPageUserInfoUseCase().collectLatest {
                    returnValue = it
                }
                return@async returnValue
            }
            deferredMyPageInfo.await()?.let {
                if (it.code in 200..299) {
                    it.response?.let { myPageResponse ->
                        UserInfo.myPageInfo = myPageResponse
                    }
                } else if (it.code == 403) {
                    needRefreshToken.value = true
                    return@launch
                } else {

                }
            }

            finishedFetchHomeItems.value = true

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