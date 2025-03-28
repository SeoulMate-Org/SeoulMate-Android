package com.seoulmate

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.domain.usecase.GetAddressesFromGeocodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAddressesFromGeocodeUseCase: GetAddressesFromGeocodeUseCase,
): ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    fun onEvents(events: MainEvents) {
        when(events) {
            is MainEvents.GetAddresses -> {
//                state = state.copy(
//                    isLoading = true
//                )
                getAddresses(events.query)
            }
        }
    }

    private fun getAddresses(query: String) {
        viewModelScope.launch {
            getAddressesFromGeocodeUseCase.getAddresses(query = query).collectLatest {
                it?.let {
                    Log.d("MainViewModel", "getAddresses: $it")
                }
            }
        }
    }
}