package com.seoulmate.challenge.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.DefaultChallengeItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.domain.usecase.GetChallengeItemDetailUseCase
import com.seoulmate.domain.usecase.ReqAttractionLikeUseCase
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
    private val reqAttractionLikeUseCase: ReqAttractionLikeUseCase,
): ViewModel()  {

    var isLoading = mutableStateOf(false)
    var challengeItem = mutableStateOf(DefaultChallengeItemData.item)
    var startedChallenge = mutableStateOf(false)
    var needUserLogin = mutableStateOf(false)
    var isStamped = mutableStateOf(false)

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
                    Log.d("@@@@@", "item id  : ${it.id}")
                    startedChallenge.value = UserInfo.getMyChallengeId().contains(it.id)

                    ChallengeDetailInfo.id = it.id
                    ChallengeDetailInfo.title = it.title
                    ChallengeDetailInfo.attractions = it.attractions
                    ChallengeDetailInfo.commentList = it.comments.map { commentItem ->
                        ChallengeCommentItem(
                            id = commentItem.id,
                            nickname = commentItem.nickname,
                            comment = commentItem.comment,
                            createdAt = commentItem.createdAt,
                            modifiedAt = commentItem.modifiedAt,
                        )
                    }

                    run breaker@ {
                        ChallengeDetailInfo.attractions.forEach {
                            if (it.isStamped) {
                                isStamped.value = true
                                return@breaker
                            }
                        }
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
            ).collectLatest { response ->
                if (response.code in 200..299) {
                    response.response?.let {
                        challengeItem.value = challengeItem.value.copy(
                            isInterest = it.isLiked
                        )
                    }
                } else if (response.code == 403) {
                    if (UserInfo.isUserLogin()) {
                        // Refresh Token
                    } else {
                        // Need Login
                        needUserLogin.value = true
                    }
                }
            }
        }
    }

    // Attraction Like or UnLike
    fun reqAttractionLike(
        attractionId: Int
    ) {
        viewModelScope.launch {
            reqAttractionLikeUseCase(attractionId)
                .collectLatest { response ->
                    if (response.code in 200..299) {
                        response.response?.let {
                            challengeItem.value = challengeItem.value.copy(
                                attractions = challengeItem.value.attractions.map { item ->
                                    if (item.id == attractionId) {
                                        item.copy(
                                            isLiked = it.isLiked
                                        )
                                    } else {
                                        item
                                    }
                                }
                            )
                        }
                    } else if (response.code == 403) {
                        if (UserInfo.isUserLogin()) {
                            // Refresh Token
                        } else {
                            // Need Login
                            needUserLogin.value = true
                        }
                    }
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