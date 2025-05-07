package com.codesubmission.settings.withdraw

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.domain.usecase.DeleteUserInfoUseCase
import com.seoulmate.domain.usecase.DeleteUserUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
): ViewModel() {

    var isShowLoading = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)

    var succeedDeleteUser = mutableStateOf(false)
    var succeedClearUserInfo = mutableStateOf(false)

    fun deleteUser() {
        viewModelScope.launch {
            isShowLoading.value = true
            deleteUserUseCase(UserInfo.id).collectLatest {
                if (it.code in 200..299) {
                    it.response?.let {
                        succeedDeleteUser.value = true
                    }
                } else if (it.code == 403) {
                    needRefreshToken.value = true
                    isShowLoading.value = false
                } else {
                    isShowLoading.value = false
                }
            }
        }
    }

    fun removeUserInfo() {
        viewModelScope.launch {
            UserInfo.logOut()
            deleteUserInfoUseCase()
            succeedClearUserInfo.value = true
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