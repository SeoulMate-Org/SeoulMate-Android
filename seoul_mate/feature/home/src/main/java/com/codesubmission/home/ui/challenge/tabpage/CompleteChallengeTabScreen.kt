package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeListItem

@Composable
fun CompleteChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    itemList: List<ChallengeItemData>,
) {
    Surface(
        modifier = modifier
    ) {
        if (itemList.isEmpty()) {
            // Empty Challenge List Item
            Box(
                modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)
            )
        } else {
            // Challenge List
            LazyColumn() {
                itemsIndexed(
                    items = itemList,
                    key = { _, item -> item.id }
                ) { index, item ->
                    ChallengeListItem(
                        item = item
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}