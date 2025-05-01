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
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.data.model.request.MyLocationReqData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetChallengeListLocationUseCase
import com.seoulmate.domain.usecase.GetChallengeListRankUseCase
import com.seoulmate.domain.usecase.GetChallengeThemeItemListUseCase
import com.seoulmate.domain.usecase.GetLoginInfoUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.SaveUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginMyData(
    val responseMyChallengeList: CommonDto<List<MyChallengeItemData>>? = null,
    val responseMyCommentList: CommonDto<List<ChallengeCommentItem>>? = null,
    val responseChallengeLocationItemList: CommonDto<ChallengeLocationItemData>? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginInfoUseCase: GetLoginInfoUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
    private val getChallengeListLocationUseCase: GetChallengeListLocationUseCase,
    private val getChallengeThemeItemListUseCase: GetChallengeThemeItemListUseCase,
    private val getChallengeListRankUseCase: GetChallengeListRankUseCase,
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
            val myChallengeList = getMyChallengeItemListUseCase(
                type = MyChallengeType.LIKE.type,
                language = UserInfo.getLanguageCode(),
            )
            val myCommentList = getMyCommentListUseCase(
                language = UserInfo.getLanguageCode(),
            )
            val myChallengeLocationList = getChallengeListLocationUseCase(
                locationRequest = MyLocationReqData(
//                    locationX = UserInfo.myLocationX,
//                    locationY = UserInfo.myLocationY,
                    locationY = 37.5686076,
                    locationX = 126.9816627,
                ),
                language = UserInfo.getLanguageCode(),
            )

            if(grantedLocationPermission) {
                combine(myChallengeList, myCommentList, myChallengeLocationList) { challengeList, commentList, myChallengeLocationList ->
                    LoginMyData(challengeList, commentList, myChallengeLocationList)
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
                        }
                    }
                    data.responseChallengeLocationItemList?.let {
                        if (it.code in 200..299) {
                            ChallengeInfo.challengeLocationData = it.response
                        }
                    }

                    isShowLoading.value = false
                    finishedFetchMyData.value = true
                }
            } else {
                combine(myChallengeList, myCommentList) { challengeList, commentList ->
                    LoginMyData(challengeList, commentList)
                }.collectLatest { data ->
                    data.responseMyChallengeList?.let {
                        if (it.code in 200..299) {
                            UserInfo.myLikeChallengeList = it.response ?: listOf()
                        }
                    }
                    data.responseMyCommentList?.let {
                        if (it.code in 200..299) {
                            UserInfo.myCommentList = it.response ?: listOf()
                        } 
                    }

                    isShowLoading.value = false
                    finishedFetchMyData.value = true
                }
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