package com.seoulmate.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.domain.usecase.GetLoginInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginInfoUseCase: GetLoginInfoUseCase,
) : ViewModel() {

    fun getLoginInfo(token: String, loginType: String,) {
        viewModelScope.launch {
            getLoginInfoUseCase.getLoginInfo(token = token, loginType = loginType).collectLatest {
                it?.let {
                    Log.d("LoginViewModel", "getLoginInfo: $it")
                }
            }
        }
    }

}