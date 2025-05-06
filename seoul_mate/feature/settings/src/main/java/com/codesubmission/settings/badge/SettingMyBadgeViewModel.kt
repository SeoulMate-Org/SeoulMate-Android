package com.codesubmission.settings.badge

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.model.challenge.ChallengeMyBadgeData
import com.seoulmate.data.repository.LoginRepository
import com.seoulmate.domain.usecase.GetMyBadgeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingMyBadgeViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val getMyBadgeUseCase: GetMyBadgeUseCase,
) : ViewModel() {

    var badgeList = mutableStateOf(
        mutableListOf<ChallengeMyBadgeData?>(
            null,null,null,
            null,null,null,
            null,null,null,
        )
    )

    fun reqMyBadge() {
        viewModelScope.launch {
            val returnList = mutableListOf<ChallengeMyBadgeData?>()
            if (UserInfo.isUserLogin()) {
                val deferredList = ChallengeInfo.themeList.map {
                    async {
                        var returnValue: CommonDto<List<ChallengeMyBadgeData>?>? = null
                        getMyBadgeUseCase(
                            themeId = it.id,
                            languageCode = UserInfo.getLanguageCode(),
                        ).collectLatest {
                            returnValue = it
                        }
                        return@async returnValue
                    }
                }

                deferredList.forEachIndexed { index, deferred ->
                    deferred.await()?.let {
                        if(it.code in 200..299) {
                            it.response?.let { response ->
                                returnList.add(response[0])
                            }
                        }
                    }
                }

                badgeList.value = returnList

            }


        }
    }

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