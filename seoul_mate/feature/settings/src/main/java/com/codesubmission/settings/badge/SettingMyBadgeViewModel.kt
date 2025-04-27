package com.codesubmission.settings.badge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.UserInfo
import com.seoulmate.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingMyBadgeViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    fun refresh() {
        viewModelScope.launch {
            loginRepository.getToken(
                UserInfo.refreshToken,
            ).collectLatest { response ->
                response?.let {
                    UserInfo.accessToken = it.accessToken
                    UserInfo.refreshToken = it.refreshToken
                }
            }
        }
    }
}