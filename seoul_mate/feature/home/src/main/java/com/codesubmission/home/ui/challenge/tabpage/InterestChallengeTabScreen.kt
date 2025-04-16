package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeListItem
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun InterestChallengeTabScreen(
    modifier: Modifier = Modifier.fillMaxSize().padding(vertical = 15.dp),
    itemList: List<ChallengeItemData>,
    onItemClick: (item: ChallengeItemData) -> Unit = {},
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