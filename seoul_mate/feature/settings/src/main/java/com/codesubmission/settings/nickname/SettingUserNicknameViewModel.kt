package com.codesubmission.settings.nickname

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.user.UserNicknameDto
import com.seoulmate.data.repository.UserRepository
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.SaveUserInfoUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingUserNicknameViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
): ViewModel() {

    var isCompleted = mutableStateOf(false)
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var duplicationNickname = mutableStateOf(false)

    fun reqUserNickname(
        strNickname: String,
    ) {
        viewModelScope.launch {
            var nicknameCompleted = false
            val deferredNickname = async {
                var response: CommonDto<UserNicknameDto>? = null
                userRepository.reqUserNickname(strNickname).collectLatest {
                    response = it
                }
                return@async response
            }

            deferredNickname.await()?.let { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        UserInfo.nickName = it.nickname

                        nicknameCompleted = true
                    }
                } else if (response.code == 400) {
                    // Nickname Duplication
                    duplicationNickname.value = true
                } else if (response.code == 403) {
                    // Refresh Token
                    needRefreshToken.value = true
                } else {

                }
            }

            if (nicknameCompleted) {
                updateUserInfoUseCase(
                    userId = UserInfo.id,
                    nickName = UserInfo.nickName,
                    accessToken = UserInfo.accessToken,
                    refreshToken = UserInfo.refreshToken,
                    loginType = UserInfo.loginType,
                )
                isCompleted.value = true
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