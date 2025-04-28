package com.seoulmate.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.ChallengeLocationItemData
import com.seoulmate.data.model.ChallengeStampItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.UserData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
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
    val responseMyChallengeLocationItemList: CommonDto<List<ChallengeLocationItemData>>? = null,
    val responseChallengeStampItemList: CommonDto<List<ChallengeStampItemData>>? = null,
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

                UserInfo.localeLanguage = splashInitData.language

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
        val languageCode = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"

        viewModelScope.launch {
            if (UserInfo.isUserLogin()) {
                val myChallenge = getMyChallengeItemListUseCase(
                    type = MyChallengeType.PROGRESS.type,
                    language = languageCode,
                )
                val myCommentList = getMyCommentListUseCase(
                    language = languageCode,
                )
                val myChallengeLocationList = getChallengeListLocationUseCase(
                    locationRequest = MyLocationReqData(
//                        locationX = UserInfo.myLocationX,
//                        locationY = UserInfo.myLocationY,
                        locationY = 37.5686076,
                        locationX = 126.9816627,

                    ),
                    language = languageCode,
                )
                val challengeStampList = getChallengeListStampUseCase(
                    attractionId = null,
                    language = languageCode,
                )

                if (grantedLocationPermission.value) {
                    combine(myChallenge, myCommentList, myChallengeLocationList, challengeStampList) { myChallengeList, myCommentList, myChallengeLocationList, challengeStapList ->
                        SplashChallengeInitData(myChallengeList, myCommentList, myChallengeLocationList, challengeStapList)
                    }.collectLatest { data ->
                        data.responseMyChallengeList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myChallengeList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
                                return@collectLatest
                            }
                        }
                        data.responseMyCommentList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myCommentList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
                                return@collectLatest
                            }
                        }
                        data.responseMyChallengeLocationItemList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myChallengeLocationList = it.response ?: listOf()
                            } else if(it.code == 401) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
                                return@collectLatest
                            }
                        }
                        data.responseChallengeStampItemList?.let {
                            if (it.code in 200..299) {
                                UserInfo.challengeStampList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
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
                                UserInfo.myChallengeList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
                                return@collectLatest
                            }
                        }
                        data.responseMyCommentList?.let {
                            if (it.code in 200..299) {
                                UserInfo.myCommentList = it.response ?: listOf()
                            } else if(it.code == 403) {
                                needRefreshToken.value = true
                                return@collectLatest
                            } else {

                                isShowLoading.value = false
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
        val languageCode = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"

        viewModelScope.launch {

            val deferredList = mutableListOf<Deferred<CommonDto<List<ChallengeStampItemData>>?>>()
            for(i in 1..9) {
                deferredList.add(
                    async {
                        var returnValue: CommonDto<List<ChallengeStampItemData>>? = null
                        getChallengeThemeItemListUseCase(i, languageCode).collectLatest {
                            returnValue = it
                        }
                        return@async returnValue
                    }
                )
            }

            val themeList = mutableListOf<List<ChallengeStampItemData>>()
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
            UserInfo.challengeThemeList = themeList.toList()

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