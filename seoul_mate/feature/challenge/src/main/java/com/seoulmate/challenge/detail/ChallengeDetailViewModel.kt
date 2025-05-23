package com.seoulmate.challenge.detail

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
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
import com.seoulmate.data.repository.PreferDataStoreRepository
import com.seoulmate.domain.usecase.DeleteChallengeStatusUseCase
import com.seoulmate.domain.usecase.GetChallengeItemDetailUseCase
import com.seoulmate.domain.usecase.GetMyChallengeItemListUseCase
import com.seoulmate.domain.usecase.RefreshTokenUseCase
import com.seoulmate.domain.usecase.ReqAttractionLikeUseCase
import com.seoulmate.domain.usecase.ReqAttractionStampUseCase
import com.seoulmate.domain.usecase.ReqChallengeLikeUseCase
import com.seoulmate.domain.usecase.ReqChallengeStatusUseCase
import com.seoulmate.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ChallengeDetailAfterRefreshTokenType {
    data object ChallengeDetail : ChallengeDetailAfterRefreshTokenType()
    data object ProgressChallengeStatus: ChallengeDetailAfterRefreshTokenType()
    data object LikeChallenge: ChallengeDetailAfterRefreshTokenType()
    data object LikeAttraction: ChallengeDetailAfterRefreshTokenType()
    data object StampAttraction: ChallengeDetailAfterRefreshTokenType()
}

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getChallengeItemDetailUseCase: GetChallengeItemDetailUseCase,
    private val reqChallengeStatusUseCase: ReqChallengeStatusUseCase,
    private val reqChallengeLikeUseCase: ReqChallengeLikeUseCase,
    private val reqAttractionStampUseCase: ReqAttractionStampUseCase,
    private val reqAttractionLikeUseCase: ReqAttractionLikeUseCase,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getMyChallengeItemListUseCase: GetMyChallengeItemListUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val preferDataStoreRepository: PreferDataStoreRepository,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val deleteChallengeStatusUseCase: DeleteChallengeStatusUseCase,
) : ViewModel() {

    var isShowLoading = mutableStateOf(false)
    var challengeItem = mutableStateOf(DefaultChallengeItemData.item)
    var startedChallengeStatus = mutableStateOf(false)
    var finishedLeaveChallenge = mutableStateOf(false)
    var needUserLogin = mutableStateOf(false)
    var isStamped = mutableStateOf(false)
    var finishedAttractionStamp = mutableStateOf(false)
    var impossibleStamp = mutableStateOf(false)
    var fineLocationPermissionGranted = mutableStateOf(false)
    var attractionDistanceItemList = mutableStateOf(listOf<Float?>())
    var needRefreshToken = mutableStateOf<Boolean?>(null)
    var afterRefreshToken = mutableStateOf<ChallengeDetailAfterRefreshTokenType?>(null)
    var afterRefreshTokenAttractionId = mutableStateOf<Int?>(null)

    var targetAttractionId = mutableStateOf<Int?>(null)


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
                        startedChallengeStatus.value = UserInfo.getMyChallengeId().contains(it.id)
//                        UserInfo.getMyChallengeId().forEach { id ->
//                            if (id == it.id) {
//                                startedChallenge.value = true
//                            }
//                        }

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
                                isMine = commentItem.isMine,
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
                    // Refresh Token
                    afterRefreshToken.value = ChallengeDetailAfterRefreshTokenType.ChallengeDetail
                    needRefreshToken.value = true
                    isShowLoading.value = false
                    return@launch
                } else {

                }
            }

            if (fineLocationPermissionGranted.value) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->

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
                        ChallengeDetailInfo.attractionDistance = attractionDistanceItemList.value
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
                    startedChallengeStatus.value = true

                } else if (it.code == 403) {
                    if (UserInfo.isUserLogin()) {
                        // Refresh Token
                        afterRefreshToken.value = ChallengeDetailAfterRefreshTokenType.ProgressChallengeStatus
                        needRefreshToken.value = true
                    } else {
                        // Need Login
                        needUserLogin.value = true
                    }
                    isShowLoading.value = false
                    return@launch
                }
            }

            if (startedChallengeStatus.value) {
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
                    }
                }
            }

            isShowLoading.value = false

        }
    }

    // Challenge Status DELETE
    fun leaveChallenge(
        id: Int,
    ) {
        viewModelScope.launch {
            isShowLoading.value = true

            deleteChallengeStatusUseCase(
                id = id,
            ).collectLatest {
                isShowLoading.value = false

                if (it.code in 200..299) {
                    startedChallengeStatus.value = false
                    finishedLeaveChallenge.value = true
                }
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
                        afterRefreshToken.value = ChallengeDetailAfterRefreshTokenType.LikeChallenge
                        needRefreshToken.value = true
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
                            afterRefreshTokenAttractionId.value = attractionId
                            afterRefreshToken.value = ChallengeDetailAfterRefreshTokenType.LikeAttraction
                            needRefreshToken.value = true
                        } else {
                            // Need Login
                            needUserLogin.value = true
                        }
                    }
                }
        }
    }

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    // Attraction Stamp
    fun reqAttractionStamp() {
        viewModelScope.launch {
            var isSucceedStamp = false
            isShowLoading.value = true
            targetAttractionId.value = null

            // TODO TEST
//            run test@ {
//                ChallengeDetailInfo.attractions.forEach {
//                    if (!it.isStamped) {
//                        targetAttractionId.value = it.id
//                        return@test
//                    }
//                }
//            }

            // Find Possible Stamp Attraction Id
            async {
                if (fineLocationPermissionGranted.value) {
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->

                        location?.let {
                            UserInfo.myLocationX = it.longitude
                            UserInfo.myLocationY = it.latitude

                            ChallengeDetailInfo.attractions.map { attractionItem ->
                                val attractionLocation = Location("").apply {
                                    latitude = (attractionItem.locationY ?: "0.0").toDouble()
                                    longitude = (attractionItem.locationX ?: "0.0").toDouble()
                                }
                                if (location.distanceTo(attractionLocation) <= 51) {
                                    targetAttractionId.value = attractionItem.id
                                }
                            }
                        }

                    }
                }
            }.await()

            // Stamp Attraction
            if (targetAttractionId.value == null) {
                impossibleStamp.value = true
            } else {
                targetAttractionId.value?.let { attractionId ->
                    reqAttractionStampUseCase(
                        id = attractionId,
                    ).collectLatest { it ->
                        if (it.code in 200..299) {
                            it.response?.let { response ->
                                response.isProcessed?.let { isProcessed ->
                                    isSucceedStamp = true
                                    ChallengeDetailInfo.completedStampThemeId = challengeItem.value.challengeThemeId
                                    if (isProcessed) finishedAttractionStamp.value = true
                                }
                            }
                        } else if (it.code == 403) {
                            isSucceedStamp = false
                            targetAttractionId.value = null
                            if (UserInfo.isUserLogin()) {
                                // Refresh Token
                                afterRefreshTokenAttractionId.value = attractionId
                                afterRefreshToken.value = ChallengeDetailAfterRefreshTokenType.StampAttraction
                                needRefreshToken.value = true
                            } else {
                                // Login
                                needUserLogin.value = true
                            }
                        }
                    }
                }
            }

            // Update Last Stamped Attraction Id
            if (isSucceedStamp) {
                targetAttractionId.value?.let {
                    async {
                        preferDataStoreRepository.updateLastStampedAttractionId(it)
                    }.await()
                }
            }

            // IF Attraction Stamp Last > Finished Challenge
            if (challengeItem.value.attractionCount - 1 == challengeItem.value.attractions.count{ it.isStamped } &&
                isSucceedStamp) {

                // put Challenge Status
                val deferredChallengeStatus = async {
                    var response: CommonDto<ChallengeStatusDto?>? = null
                    reqChallengeStatusUseCase(
                        id = challengeItem.value.id,
                        status = MyChallengeType.COMPLETE.type,
                    ).collectLatest {
                        response = it
                    }
                    return@async response
                }

                deferredChallengeStatus.await()?.let {
                    if (it.code in 200..299) {
                        Log.d("@@@@@", "Challenge Status Complete")
                    }
                }
            }

            isShowLoading.value = false
        }
    }

    fun refreshToken() {
        viewModelScope.launch {
            refreshTokenUseCase(UserInfo.refreshToken).collectLatest { response ->
                response?.let {
                    UserInfo.refreshToken = it.refreshToken
                    UserInfo.accessToken = it.accessToken

                    updateUserInfoUseCase(
                        userId = UserInfo.id,
                        nickName = UserInfo.nickName,
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        loginType = UserInfo.loginType,
                    )

                    needRefreshToken.value = false
                }
            }
        }
    }





}