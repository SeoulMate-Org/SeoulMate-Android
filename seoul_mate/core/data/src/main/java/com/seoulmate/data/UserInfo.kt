package com.seoulmate.data

import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.ChallengeLocationItemData
import com.seoulmate.data.model.MyChallengeItemData

object UserInfo {
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var loginType: String = ""
    var localeLanguage: String = ""
    var myChallengeList = listOf<MyChallengeItemData>()
    var myCommentList = listOf<ChallengeCommentItem>()
    var myChallengeLocationList = listOf<ChallengeLocationItemData>()
    var myLocationX: Double = 0.0
    var myLocationY: Double = 0.0

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()

    fun getMyChallengeId(): List<Int> = myChallengeList.map { it.id }
}