package com.seoulmate.ui.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.datastore.repository.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferDataStoreRepository: PreferDataStoreRepository,
): ViewModel() {

    private val _languageFlow = MutableSharedFlow<String>()
    val languageFlow = _languageFlow.asSharedFlow()

    var isFirstEnter = mutableStateOf(true)

    fun reqSplashInit() {
        loadLanguage()
        loadIsFirstEnter()
    }

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

    fun loadIsFirstEnter() {
        viewModelScope.launch {
            preferDataStoreRepository.loadIsFirstEnter().collectLatest {
                Log.d("SplashViewModel", "loadIsFirstEnter: $it")
                isFirstEnter.value = it
            }
        }
    }

    fun updateIsFirstEnter(isFirst: Boolean) {
        viewModelScope.launch {
            preferDataStoreRepository.updateIsFirstEnter(isFirst)
        }

    }
}