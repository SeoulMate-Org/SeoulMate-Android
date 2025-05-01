package com.seoulmate.challenge.detail

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.CommonDto
import com.seoulmate.data.dto.challenge.ChallengeStatusDto
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.DefaultChallengeItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.domain.usecase.GetChallengeItemDetailUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.ReqAttractionLikeUseCase
import com.seoulmate.domain.usecase.ReqAttractionStampUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import com.seoulmate.domain.usecase.ReqChallengeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase
) : ViewModel() {

    var lastLocation = mutableStateOf<Location?>(null)
    var isShowLoading = mutableStateOf(false)
    var challengeItem = mutableStateOf(DefaultChallengeItemData.item)
    var startedChallenge = mutableStateOf(false)
    var needUserLogin = mutableStateOf(false)
    var isStamped = mutableStateOf(false)
    var fineLocationPermissionGranted = mutableStateOf(false)
    var attractionDistanceItemList = mutableStateOf(listOf<Float?>())

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    // fetch Challenge Item
    fun getChallengeItem(
        id: Int,
        language: String = "KOR",
    ) {
        viewModelScope.launch {
            isShowLoading.value = true

            // Challenge Detail
            val deferredChallengeDetail = async {
                var response: CommonDto<ChallengeItemData?>? = null
                getChallengeItemDetailUseCase(
                    id = id,
                    language = language,
                ).collectLatest {
                    response = it
                }
                return@async response
            }.await()

            deferredChallengeDetail?.let { challengeDetailResponse ->
                if (challengeDetailResponse.code in 200..299) {
                    challengeDetailResponse.response?.let {
                        challengeItem.value = it
                        Log.d("@@@@@", "myChallenge : ${UserInfo.myLikeChallengeList}")
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

                        run breaker@{
                            ChallengeDetailInfo.attractions.forEach {
                                if (it.isStamped) {
                                    isStamped.value = true
                                    return@breaker
                                }
                            }
                        }

                    }
                } else if (challengeDetailResponse.code == 403) {

                } else {

                }
            }

            if (fineLocationPermissionGranted.value) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    lastLocation.value = location

                    location?.let {
                        UserInfo.myLocationX = it.longitude
                        UserInfo.myLocationY = it.latitude
                        Log.d("@@@@@", "myLocationX : ${UserInfo.myLocationX} myLocationY : ${UserInfo.myLocationY}")
                        attractionDistanceItemList.value = ChallengeDetailInfo.attractions.map { attractionItem ->
                            if (attractionItem.locationX == null || attractionItem.locationY == null) {
                                null
                            } else {
                                it.distanceTo(Location("").apply {
                                    latitude = (attractionItem.locationY ?: "0.0").toDouble()
                                    longitude = (attractionItem.locationX ?: "0.0").toDouble()
                                })
                            }

                        }
                    }

                }

            }

            isShowLoading.value = false

        }
    }

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    // Challenge Status PROGRESS
    fun reqProgressChallengeStatus(
        challengeType: MyChallengeType = MyChallengeType.PROGRESS,
    ) {
        viewModelScope.launch {
            isShowLoading.value = true

            // put Challenge Status
            val deferredChallengeStatus = async {
                var response: CommonDto<ChallengeStatusDto?>? = null
                reqChallengeStatusUseCase(
                    id = challengeItem.value.id,
                    status = challengeType.type,
                ).collectLatest {
                    response = it
                }
                return@async response
            }

            deferredChallengeStatus.await()?.let {
                if (it.code in 200..299) {
                    startedChallenge.value = true

                } else if (it.code == 403) {

                }
            }

            if (startedChallenge.value) {
                // fetch My Challenge List
                val deferredMyChallengeList = async {
                    var returnResponse: CommonDto<List<MyChallengeItemData>>? = null
                    getMyChallengeItemListUseCase(
                        type = challengeType.type,
                        language = UserInfo.getLanguageCode()
                    ).collectLatest {
                        returnResponse = it
                    }
                    return@async returnResponse
                }

                deferredMyChallengeList.await()?.let {
                    if (it.code in 200..299) {
                        it.response?.let { response ->
                            UserInfo.myProgressChallengeList = response
                        }
                    } else if (it.code == 403) {

                    } else {

                    }
                }
            }

            isShowLoading.value = false

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
                        ChallengeInfo.challengeCulturalList = ChallengeInfo.challengeCulturalList.map { item ->
                            if (item.id == challengeItem.value.id) {
                                item.copy(
                                    isLiked = it.isLiked
                                )
                            } else {
                                item
                            }
                        }
                        ChallengeInfo.setChallengeStampList(
                            ChallengeInfo.getChallengeStampList().map { item ->
                                if (item.id == challengeItem.value.id) {
                                    item.copy(
                                        isLiked = it.isLiked
                                    )
                                } else {
                                    item
                                }
                            }
                        )
                        ChallengeInfo.rankList = ChallengeInfo.rankList.map { item ->
                            if (item.id == challengeItem.value.id) {
                                item.copy(
                                    isLiked = it.isLiked
                                )
                            } else {
                                item
                            }
                        }
                        // TODO chan
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
                            ChallengeDetailInfo.attractions = challengeItem.value.attractions
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