package com.seoulmate.ui.splash

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
import com.seoulmate.data.model.UserData
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeStampResponseData
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetChallengeCulturalEventUseCase
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
import com.seoulmate.domain.usecase.GetChallengeListRankUseCase
import com.seoulmate.domain.usecase.GetChallengeListStampUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeItemListUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.GetUserInfoUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
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
)

data class SplashChallengeInitData(
    val responseMyChallengeList: CommonDto<List<MyChallengeItemData>>? = null,
    val responseMyCommentList: CommonDto<List<ChallengeCommentItem>>? = null,
    val responseMyChallengeLocationItemList: CommonDto<ChallengeLocationItemData>? = null,
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
): ViewModel() {

    private val _splashInitDataFlow = MutableSharedFlow<SplashInitData>()
    val splashInitDataFlow = _splashInitDataFlow.asSharedFlow()

    var isShowLoading = mutableStateOf<Boolean?>(null)
    var finishedFetchUserInfo = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var grantedLocationPermission = mutableStateOf(false)

    fun reqSplashInit() {
        viewModelScope.launch {
            isShowLoading.value = true

            val languageFlow = preferDataStoreRepository.loadLanguage()
            val isFirstEnterFlow = preferDataStoreRepository.loadIsFirstEnter()
            val userDataFlow = getUserInfoUseCase()

            combine(languageFlow, isFirstEnterFlow, userDataFlow) { language, isFirstEnter, userData ->
                SplashInitData(language, userData, isFirstEnter)
            }.collectLatest { splashInitData ->
                _splashInitDataFlow.emit(splashInitData)

                if(splashInitData.language.isNotEmpty()) {
                    UserInfo.localeLanguage = splashInitData.language
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

    fun reqInit() {

        viewModelScope.launch {
            if (UserInfo.isUserLogin()) {
                val myChallenge = getMyChallengeItemListUseCase(
                    type = MyChallengeType.LIKE.type,
                    language = UserInfo.getLanguageCode(),
                )
                val myCommentList = getMyCommentListUseCase(
                    language = UserInfo.getLanguageCode(),
                )
                val myChallengeLocationList = getChallengeListLocationUseCase(
                    locationRequest = MyLocationReqData(
                        locationX = UserInfo.myLocationY,
                        locationY = UserInfo.myLocationX,
                    ),
                    language = UserInfo.getLanguageCode(),
                )

                if (grantedLocationPermission.value) {
                    combine(myChallenge, myCommentList, myChallengeLocationList) { myChallengeList, myCommentList, myChallengeLocationList ->
                        SplashChallengeInitData(myChallengeList, myCommentList, myChallengeLocationList)
                    }.collectLatest { data ->
                        data.responseMyChallengeList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myLikeChallengeList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            }
                        }
                        data.responseMyCommentList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myCommentList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            }
                        }
                        data.responseMyChallengeLocationItemList?.let {
                            if (it.code in 200..299) {
                                ChallengeInfo.challengeLocationData = it.response
                            } else if(it.code == 401) {
                                needRefreshToken.value = true
                                return@collectLatest
                            }
                        }
                        finishedFetchUserInfo.value = true
                    }
                } else {
                    combine(myChallenge, myCommentList) { myChallengeList, myCommentList ->
                        SplashChallengeInitData(myChallengeList, myCommentList)
                    }.collectLatest { data ->
                        data.responseMyChallengeList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myLikeChallengeList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            }
                        }
                        data.responseMyCommentList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myCommentList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            }
                        }
                        finishedFetchUserInfo.value = true
                    }
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
                    needRefreshToken.value = true
                    return@launch
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

                    needRefreshToken.value = false
                }
            }
        }
    }

}