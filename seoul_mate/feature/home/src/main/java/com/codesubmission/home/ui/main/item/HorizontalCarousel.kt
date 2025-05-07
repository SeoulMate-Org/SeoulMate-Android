package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.component.ChallengeBigSquareImageTypeLayout
import com.seoulmate.ui.theme.SeoulMateTheme

@Composable
fun HorizontalCarousel(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .height(280.dp),
    itemList: List<ChallengeCulturalEventData>,
    screenWidth: Dp,
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { itemList.size }
    )

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        key = { itemList[it].id },
        contentPadding = PaddingValues(horizontal = screenWidth * 0.2f),
        pageSpacing = screenWidth * 0.1f,
    ) { pageIndex ->
        val pageItem = itemList[pageIndex]
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            ChallengeBigSquareImageTypeLayout(
                item = pageItem,
                onChallengeItemClick = onChallengeItemClick,
                onChallengeLikeClick = onChallengeLikeClick,
            )

        }
    }
}
