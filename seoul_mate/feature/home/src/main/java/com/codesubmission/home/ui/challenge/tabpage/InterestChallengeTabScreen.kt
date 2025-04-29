package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.challenge.ChallengeItemData

@Composable
fun InterestChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize().padding(vertical = 15.dp),
    itemList: List<ChallengeItemData>,
    onItemClick: (challengeId: Int) -> Unit = {},
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
                onItemClick = onItemClick
            )
        }
    }
}