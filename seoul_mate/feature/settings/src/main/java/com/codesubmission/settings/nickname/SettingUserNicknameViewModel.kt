package com.codesubmission.settings.nickname

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingUserNicknameViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {

    var isCompleted = mutableStateOf(false)

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
                } else if (response.code == 403) {
                    // Refresh Token
                }
            }

        }
    }
}