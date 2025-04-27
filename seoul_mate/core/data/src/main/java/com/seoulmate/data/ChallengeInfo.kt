package com.seoulmate.data

import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ChallengeThemeData

object ChallengeInfo {
    var themeList = listOf<ChallengeThemeData>()
    var allItemList = listOf<ChallengeItemData>()
}

object ChallengeDetailInfo {
    var id = 0
    var commentList = listOf<ChallengeCommentItem>()
}