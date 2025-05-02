package com.codesubmission.settings.nickname

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.repository.UserRepository
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingUserNicknameViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshTokenUseCase: RefreshTokenUseCase,
): ViewModel() {

    var isCompleted = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var duplicationNickname = mutableStateOf(false)

    fun reqUserNickname(
        strNickname: String,
    ) {
        viewModelScope.launch {
            userRepository.reqUserNickname(strNickname).collectLatest { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        UserInfo.nickName = it.nickname
                        isCompleted.value = true
                    }
                } else if (response.code == 400) {
                    // Nickname Duplication
                    duplicationNickname.value = true
                } else if (response.code == 403) {
                    // Refresh Token
                    needRefreshToken.value = true
                }
            }

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