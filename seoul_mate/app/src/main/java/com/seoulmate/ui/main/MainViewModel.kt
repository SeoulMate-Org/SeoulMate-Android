package com.seoulmate.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.model.UserData
import com.seoulmate.data.repository.PreferDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
): ViewModel() {
    private val _languageFlow = MutableSharedFlow<String>()
    val languageFlow = _languageFlow.asSharedFlow()

    var isShowLoading = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)

    fun loadLanguage() {
        viewModelScope.launch {
            preferDataStoreRepository.loadLanguage().collectLatest {
                Log.d("SplashViewModel", "loadLanguage: $it")
                _languageFlow.emit(it)
            }
        }
    }

    fun updateLanguage(languageCode: String) {
        viewModelScope.launch {
            preferDataStoreRepository.updateLanguage(languageCode)
            _languageFlow.emit(languageCode)
        }
    }

}