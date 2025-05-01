package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codesubmission.home.R
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeItemData

@Composable
fun InProgressChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    itemList: List<MyChallengeItemData>,
    onItemClick: (challengeId: Int) -> Unit = {},
) {
    Surface(
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            // Empty Challenge List Item
            EmptyChallenge(
                titleRes = R.string.empty_progress_challenge,
            )
        } else {
            // Challenge List
            ChallengeProgressItemList(
                itemList = itemList,
                onItemClick = onItemClick,
            )
        }
    }
}