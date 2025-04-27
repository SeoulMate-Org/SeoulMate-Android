package com.seoulmate.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetLoginInfoUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.SaveUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginMyData(
    val myChallengeList: List<MyChallengeItemData>,
    val myCommentList: List<ChallengeCommentItem>?
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginInfoUseCase: GetLoginInfoUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
) : ViewModel() {

    var isSuccessLogin = mutableStateOf(false)
    var finishedFetchMyData = mutableStateOf(false)
    var isNewUser = mutableStateOf(false)
    var isFirstEnter = mutableStateOf<Boolean?>(null)

    private val languageCode = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"


    fun getLoginInfo(token: String, loginType: String) {
        viewModelScope.launch {
            getLoginInfoUseCase.getLoginInfo(
                token = token,
                loginType = loginType,
                languageCode = languageCode,
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

    fun getMyData() {
        viewModelScope.launch {
            val myChallengeList = getMyChallengeItemListUseCase(
                type = "PROGRESS",
                language = languageCode,
            )
            val myCommentList = getMyCommentListUseCase(
                languageCode = languageCode,
            )

//            combine(myChallengeList, myCommentList) { challengeList, commentList ->
//                LoginMyData(challengeList, commentList)
//            }.collectLatest { data ->
//                UserInfo.myChallengeList = data.myChallengeList
//                UserInfo.myCommentList = data.myCommentList ?: listOf()
//
//                finishedFetchMyData.value = true
//
//            }

//            myChallengeList.collectLatest { challengeList ->
//                UserInfo.myChallengeList = challengeList
//                finishedFetchMyData.value = true
//            }

            myCommentList.collectLatest { commentList ->
                UserInfo.myCommentList = commentList ?: listOf()
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

}