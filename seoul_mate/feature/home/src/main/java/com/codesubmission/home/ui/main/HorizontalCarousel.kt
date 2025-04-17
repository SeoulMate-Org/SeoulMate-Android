package com.codesubmission.home.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeSquareImageTypeLayout

@Composable
fun HorizontalCarousel(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(400.dp),
    itemList: List<ChallengeItemData>,
) {
    val contentPadding = 40.dp
    val pageSpacing = 10.dp
    val pagerState = rememberPagerState(
        pageCount = { itemList.size }
    )

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        key = { itemList[it].id },
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
    ) { pageIndex ->
        val pageItem = itemList[pageIndex]
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            ChallengeSquareImageTypeLayout(item = pageItem)

        }
    }
}
