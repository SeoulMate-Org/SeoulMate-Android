package com.seoulmate.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.model.UserData
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val getUserInfoUseCase: GetUserInfoUseCase,
): ViewModel() {
    var language = mutableStateOf("")

    private val _languageFlow = MutableSharedFlow<String>()
    val languageFlow = _languageFlow.asSharedFlow()

    var isShowLoading = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)

    fun reqMainInit() {
//        viewModelScope.launch {
//            isShowLoading.value = true
//
//            delay(3000)
//            val userDataFlow = getUserInfoUseCase()
//
//            userDataFlow.collectLatest {
//                userData.value = it
//
//                isShowLoading.value = false
//            }
//        }
    }

    fun loadLanguage() {
        viewModelScope.launch {
            preferDataStoreRepository.loadLanguage().collectLatest {
                Log.d("SplashViewModel", "loadLanguage: $it")
                language.value = it
                _languageFlow.emit(it)
            }
        }
    }

    fun updateLanguage(languageCode: String) {
        viewModelScope.launch {
            preferDataStoreRepository.updateLanguage(languageCode)
            language.value = languageCode
            _languageFlow.emit(languageCode)
        }
    }

    fun refreshUserToken() {

    }

}