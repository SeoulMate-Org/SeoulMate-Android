package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.component.ChallengeTileTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.TrueWhite

// Challenge Item Tile
@Composable
fun ChallengeItemList(
    modifier: Modifier = Modifier.background(color = TrueWhite),
    itemList: List<ChallengeItemData>,
    onItemClick: (challengeId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            PpsText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                text = "전체 (${itemList.size})개",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray300,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        itemsIndexed(
            items = itemList,
            key = { _, item -> item.id }
        ) { index, item ->
            ChallengeTileTypeLayout(
                item = item,
                onItemClick = onItemClick
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 10.dp)
                    .background(color = CoolGray25),
            )
        }
    }
}