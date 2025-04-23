package com.seoulmate.challenge.theme.item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChallengeThemeTabRow(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(41.dp)
        .padding(horizontal = 15.dp),
    coroutineScope: CoroutineScope,
    pageList: List<String>,
    pagerState: PagerState,
) {
    val currentSelectedTabIndex = pagerState.currentPage
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentSelectedTabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    tabPositions[currentSelectedTabIndex]
                ),
                height = 2.dp,
                color = Blue500,
            )
        },
        containerColor = TrueWhite,
        contentColor = TrueWhite,
    ) {
        pageList.forEachIndexed { index, pageItem->
            val isSelected = currentSelectedTabIndex == index
            Tab(
                selected = isSelected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            ) {
                PpsText(
                    modifier = Modifier.height(39.dp),
                    text = pageItem,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if(isSelected) Blue500 else CoolGray500,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}