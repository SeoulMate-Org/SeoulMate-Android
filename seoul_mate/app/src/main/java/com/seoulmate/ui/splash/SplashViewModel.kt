package com.seoulmate.ui.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ChallengeThemeData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.UserData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetAllChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeListUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
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
    val themeList: List<ChallengeThemeData>,
    val itemList: List<ChallengeItemData>,
    val myChallengeList: List<MyChallengeItemData> = listOf(),
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getChallengeThemeListUseCase: GetChallengeThemeListUseCase,
    private val getAllChallengeItemListUseCase: GetAllChallengeItemListUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
): ViewModel() {

    private val _splashInitDataFlow = MutableSharedFlow<SplashInitData>()
    val splashInitDataFlow = _splashInitDataFlow.asSharedFlow()

    var isShowLoading = mutableStateOf<Boolean?>(null)

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

            val themeFlow = getChallengeThemeListUseCase()
            val allItemFlow = getAllChallengeItemListUseCase(
                page = 0, size = 10,
            )
            // TODO chan Language Setting
            val myChallenge = getMyChallengeItemListUseCase(
                type = MyChallengeType.PROGRESS.type,
                language = "KOR",
            )

            if (UserInfo.isUserLogin()) {
                combine(themeFlow, allItemFlow, myChallenge) { themeList, allItemList, myChallengeList ->
                    SplashChallengeInitData(themeList, allItemList, myChallengeList)
                }.collectLatest { data ->
                    ChallengeInfo.themeList = data.themeList
                    ChallengeInfo.allItemList = data.itemList
                    ChallengeInfo.myChallengeList = data.myChallengeList
                    isShowLoading.value = false
                }
            } else {
                combine(themeFlow, allItemFlow) { themeList, allItemList ->
                    SplashChallengeInitData(themeList, allItemList)
                }.collectLatest { data ->
                    ChallengeInfo.themeList = data.themeList
                    ChallengeInfo.allItemList = data.itemList
                    isShowLoading.value = false
                }
            }


        }
    }

}