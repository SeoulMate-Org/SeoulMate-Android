package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seoulmate.data.model.ChallengeItemData

@Composable
fun InProgressChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    itemList: List<ChallengeItemData>,
) {
    Surface(
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            // Empty Challenge List Item
            EmptyChallenge()
        } else {
            // Challenge List
            ChallengeItemList(
                itemList = itemList,
            )
        }
    }
}