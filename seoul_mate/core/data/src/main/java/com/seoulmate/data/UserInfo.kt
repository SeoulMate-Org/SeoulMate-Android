package com.seoulmate.data

import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.user.UserInfoData

object UserInfo {
    var id : Int = 0
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var loginType: String = ""
    var localeLanguage: String = ""
    var myLikeChallengeList = listOf<MyChallengeItemData>()
    var myProgressChallengeList = listOf<MyChallengeItemData>()
    var myCompleteChallengeList = listOf<MyChallengeItemData>()
    var myCommentList = listOf<ChallengeCommentItem>()
    var myLocationX: Double = 0.0
    var myLocationY: Double = 0.0
    var lastStampedAttractionId: Int = -1
    var myPageInfo: UserInfoData? = null

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()

    fun getMyChallengeId(): List<Int> = myProgressChallengeList.map { it.id }

    fun getLanguageCode() = if(localeLanguage == "ko") "KOR" else "ENG"

    fun logOut() {
        nickName = ""
        accessToken = ""
        refreshToken = ""
        myLikeChallengeList = listOf()
        myProgressChallengeList = listOf()
        myCompleteChallengeList = listOf()
        myCommentList = listOf()
        myPageInfo = null
    }
}