package com.seoulmate.challenge.theme.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeThemeData
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
    pageList: List<ChallengeThemeData>,
    pagerState: PagerState,
) {
    val currentSelectedTabIndex = pagerState.currentPage
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentSelectedTabIndex,
        indicator = { tabPositions ->
            if (tabPositions.isNotEmpty()) {
                val tabPosition = tabPositions[currentSelectedTabIndex]
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPosition)
                        .height(2.dp)
                        .background(Blue500)
                )
            }
        },
        divider = {},
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
                    text = if (UserInfo.localeLanguage == "ko") pageItem.nameKor else pageItem.title,
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