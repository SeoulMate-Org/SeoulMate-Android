package com.seoulmate.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ChallengeLocationItemData
import com.seoulmate.data.model.ChallengeThemeData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.UserData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetAllChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeListUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val myChallengeList: List<MyChallengeItemData> = listOf(),
    val myCommentList: List<ChallengeCommentItem>?,
    val myChallengeLocationItemList: List<ChallengeLocationItemData>? = null,
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
    private val getChallengeListLocationUseCase: GetChallengeListLocationUseCase,
): ViewModel() {

    private val _splashInitDataFlow = MutableSharedFlow<SplashInitData>()
    val splashInitDataFlow = _splashInitDataFlow.asSharedFlow()

    var isShowLoading = mutableStateOf<Boolean?>(null)
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
                        locationX = UserInfo.myLocationX,
                        locationY = UserInfo.myLocationY,
                    ),
                    language = languageCode,
                )

                if (grantedLocationPermission.value) {
                    combine(myChallenge, myCommentList, myChallengeLocationList) { myChallengeList, myCommentList, challengeLocationItemList ->
                        SplashChallengeInitData(myChallengeList, myCommentList, challengeLocationItemList)
                    }.collectLatest { data ->
                        UserInfo.myChallengeList = data.myChallengeList
                        UserInfo.myCommentList = data.myCommentList ?: listOf()
                        UserInfo.myChallengeLocationList = data.myChallengeLocationItemList ?: listOf()
                        isShowLoading.value = false
                    }
                } else {
                    combine(myChallenge, myCommentList) { myChallengeList, myCommentList ->
                        SplashChallengeInitData(myChallengeList, myCommentList)
                    }.collectLatest { data ->
                        UserInfo.myChallengeList = data.myChallengeList
                        UserInfo.myCommentList = data.myCommentList ?: listOf()
                        isShowLoading.value = false
                    }
                }
            }


        }
    }

}