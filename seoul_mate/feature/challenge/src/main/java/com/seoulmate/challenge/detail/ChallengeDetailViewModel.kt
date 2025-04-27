package com.seoulmate.challenge.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.DefaultChallengeItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.domain.usecase.GetChallengeItemDetailUseCase
import com.seoulmate.domain.usecase.ReqAttractionStampUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import com.seoulmate.domain.usecase.ReqChallengeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getChallengeItemDetailUseCase: GetChallengeItemDetailUseCase,
    private val reqChallengeStatusUseCase: ReqChallengeStatusUseCase,
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
    private val reqAttractionStampUseCase: ReqAttractionStampUseCase,
): ViewModel()  {

    var isLoading = mutableStateOf(false)
    var challengeItem = mutableStateOf(DefaultChallengeItemData.item)
    var startedChallenge = mutableStateOf(false)

    // fetch Challenge Item
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
                    Log.d("@@@@@", "myChallenge : ${UserInfo.myChallengeList}")
                    Log.d("@@@@@", "getMyChallengeId : ${UserInfo.getMyChallengeId()}")
                    Log.d("@@@@@", "item id  : ${item.id}")
                    startedChallenge.value = UserInfo.getMyChallengeId().contains(item.id)

                    ChallengeDetailInfo.id = item.id
                    ChallengeDetailInfo.commentList = item.comments.map { commentItem ->
                        ChallengeCommentItem(
                            id = commentItem.id,
                            comment = commentItem.comment,
                            createdAt = commentItem.createdAt,
                            modifiedAt = commentItem.modifiedAt,
                        )
                    }
                }
            }
        }
    }

    // Challenge Status PROGRESS
    fun reqProgressChallengeStatus() {
        viewModelScope.launch {
            reqChallengeStatusUseCase(
                id = challengeItem.value.id,
                status = "PROGRESS",
            ).collectLatest {
                // TODO chan
                UserInfo.myChallengeList = listOf(
                    MyChallengeItemData(
                        id = challengeItem.value.id,
                        name = "",
                        title = challengeItem.value.title,
                        likes = 0,
                        attractionCount = challengeItem.value.attractions.size,
                        myStampCount = 0,
                        mainLocation = challengeItem.value.mainLocation,
                        challengeThemeId = 0,
                        challengeThemeName = "",
                    )
                )

                getChallengeItem(
                    id = challengeItem.value.id,
                    language = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG",
                )
            }
        }
    }

    // Challenge Like or UnLike
    fun reqChallengeLike() {
        viewModelScope.launch {
            reqChallengeLikeUseCase(
                id = challengeItem.value.id,
            ).collectLatest { item ->
                Log.d("@@@@@", "item : $item")
            }
        }
    }

    // Attraction Stamp
    fun reqAttractionStamp(
        attractionId: Int,
    ) {
        viewModelScope.launch {
            reqAttractionStampUseCase(
                id = attractionId,
            ).collectLatest { isSuccess ->
                Log.d("@@@@@", "isSuccess : $isSuccess")
            }
        }
    }



}