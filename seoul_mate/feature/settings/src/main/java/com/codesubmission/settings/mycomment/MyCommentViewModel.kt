package com.codesubmission.settings.mycomment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.domain.usecase.DeleteCommentUseCase
import com.seoulmate.domain.usecase.GetMyCommentListUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCommentViewModel @Inject constructor(
    private val getMyCommentListUseCase: GetMyCommentListUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
): ViewModel() {

    var isShowLoading = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)

    var myCommentList = mutableStateOf<List<ChallengeCommentItem>>(listOf())
    var completedDelete = mutableStateOf(false)

    fun reqMyCommentList() {
        viewModelScope.launch {
            isShowLoading.value = true
            getMyCommentListUseCase(UserInfo.getLanguageCode()).collectLatest { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        myCommentList.value = it
                    }
                } else if (response.code == 403) {
                    needRefreshToken.value = true
                } else {

                }
            }
            isShowLoading.value = false
        }

    }

    fun deleteComment(
        commentId: Int,
    ) {
        viewModelScope.launch {
            deleteCommentUseCase(
                commentId
            ).collectLatest {
                completedDelete.value = true
            }
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
                    UserInfo.refreshToken = it.refreshToken
                    UserInfo.accessToken = it.accessToken

                    updateUserInfoUseCase(
                        userId = UserInfo.id,
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