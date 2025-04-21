package com.seoulmate.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.datastore.repository.Language
import com.seoulmate.domain.usecase.GetAddressesFromGeocodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAddressesFromGeocodeUseCase: GetAddressesFromGeocodeUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
): ViewModel() {
    var language = mutableStateOf("")

    private val _languageFlow = MutableSharedFlow<String>()
    val languageFlow = _languageFlow.asSharedFlow()

    private fun getAddresses(query: String) {
        viewModelScope.launch {
            getAddressesFromGeocodeUseCase.getAddresses(query = query).collectLatest {
                it?.let {
                    Log.d("MainViewModel", "getAddresses: $it")
                }
            }
        }
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

}