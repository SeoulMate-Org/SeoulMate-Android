package com.seoulmate.data

import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ChallengeThemeData
import com.seoulmate.data.model.MyChallengeItemData

object ChallengeInfo {
    var themeList = listOf<ChallengeThemeData>()
    var allItemList = listOf<ChallengeItemData>()
    var myChallengeList = listOf<MyChallengeItemData>()
}