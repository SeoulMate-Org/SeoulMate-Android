package com.seoulmate.challenge.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.DefaultChallengeItemData
import com.seoulmate.domain.usecase.GetChallengeItemDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getChallengeItemDetailUseCase: GetChallengeItemDetailUseCase,
): ViewModel()  {

    var isLoading = mutableStateOf(false)
    var challengeItem = mutableStateOf(DefaultChallengeItemData.item)

    fun getChallengeItem(
        id: Int,
        language: String = "KOR",
    ) {
        viewModelScope.launch {
            isLoading.value = true

            getChallengeItemDetailUseCase(
                id = id,
                language = language,
            ).collectLatest { item ->
                isLoading.value = false

                item?.let {
                    challengeItem.value = it
                }
            }
        }
    }
}