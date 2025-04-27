package com.seoulmate.data

import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.MyChallengeItemData

object UserInfo {
    var nickName: String = ""
    var accessToken: String = ""
    var refreshToken: String = ""
    var loginType: String = ""
    var localeLanguage: String = ""
    var myChallengeList = listOf<MyChallengeItemData>()
    var myCommentList = listOf<ChallengeCommentItem>()

    fun isUserLogin(): Boolean = accessToken.isNotEmpty()

    fun getMyChallengeId(): List<Int> = myChallengeList.map { it.id }
}