package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codesubmission.home.R
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeItemData

@Composable
fun CompleteChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    itemList: List<MyChallengeItemData>,
    onItemClick: (challengeId: Int) -> Unit = {},
    onItemLikeClick: (challengeId: Int) -> Unit = {},
) {
    Surface(
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            // Empty Challenge List Item
            EmptyChallenge(
                titleRes = R.string.empty_complete_challenge,
                infoRes = R.string.empty_complete_challenge_sub,
                isShowButton = false,
            )
        } else {
            // Challenge List
            ChallengeItemList(
                itemList = itemList,
                onItemClick = onItemClick,
                onItemLikeClick = onItemLikeClick,
            )
        }
    }
}