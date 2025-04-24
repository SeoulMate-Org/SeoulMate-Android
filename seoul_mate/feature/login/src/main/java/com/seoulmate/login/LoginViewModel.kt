package com.seoulmate.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetLoginInfoUseCase
import com.seoulmate.domain.usecase.SaveUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginInfoUseCase: GetLoginInfoUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
) : ViewModel() {

    var isSuccessLogin = mutableStateOf(false)
    var isFirstEnter = mutableStateOf<Boolean?>(null)

    // TODO chan  devices / saved
    fun getLoginInfo(token: String, loginType: String, localeCode: String = "KOR") {
        viewModelScope.launch {
            var languageCode = localeCode

            joinAll(
                launch {
                    preferDataStoreRepository.loadLanguage().collectLatest {
                        Log.d("LoginViewModel", "loadLanguage: $it")
                        if (it.isNotEmpty() && it == "en") {
                            languageCode = "US"
                        }
                    }
                },
                launch {
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
                            isSuccessLogin.value = true
                        }
                    }
                },
            )


        }
    }

    fun loadIsFirstEnter() {
        viewModelScope.launch {
            preferDataStoreRepository.loadIsFirstEnter().collectLatest {
                isFirstEnter.value = it
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