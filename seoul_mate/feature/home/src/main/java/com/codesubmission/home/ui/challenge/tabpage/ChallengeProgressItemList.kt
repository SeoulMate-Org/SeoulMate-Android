package com.codesubmission.home.ui.challenge.tabpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.home.R
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.ui.component.ChallengeTileProgressTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeProgressItemList(
    modifier: Modifier = Modifier.background(color = TrueWhite),
    itemList: List<MyChallengeItemData>,
    onItemClick: (challengeId: Int) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            Spacer(modifier = Modifier.height(25.dp))
            PpsText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = stringResource(R.string.challenge_count, itemList.size),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray300,
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        itemsIndexed(
            items = itemList,
            key = { _, item -> item.id }
        ) { index, item ->
            if (index > 0) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 5.dp)
                        .background(color = CoolGray25),
                )
            }

            ChallengeTileProgressTypeLayout(
                item = item,
                onItemClick = onItemClick,
            )
        }
    }
}