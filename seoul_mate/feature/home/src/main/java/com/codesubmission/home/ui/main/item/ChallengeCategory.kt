package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.ui.component.ChallengeHomeTileTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.RoundedTag
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray75
import com.seoulmate.ui.theme.CoolGray900
import kotlinx.coroutines.launch

@Composable
fun ChallengeCategory(
    modifier: Modifier,
    themeItemList: List<List<ChallengeThemeItemData>>,
    onMoreClick: () -> Unit = {},
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { ChallengeInfo.themeList.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )
    val pageState = rememberLazyListState(
        initialFirstVisibleItemIndex = pagerState.currentPage,
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        // Title + More Button
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Title
            PpsText(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home_challenge_category_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = CoolGray900,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            // More Button
            Button(
                onClick = onMoreClick,
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                PpsText(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(com.seoulmate.ui.R.string.str_more),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray400,
                    )
                )
            }
        }
        // Category
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = pageState,
        ) {
            itemsIndexed(
                items = ChallengeInfo.themeList,
                key = { index, item -> item.id }
            ) { index, item ->
                Box(modifier = Modifier.padding(end = 8.dp, start = if(index == 0) 20.dp else 0.dp),) {
                    RoundedTag(
                        title = if(UserInfo.localeLanguage == "ko") item.nameKor else item.title,
                        isSelected = index == pagerState.currentPage
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                }
            }
        }
        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(horizontal = 20.dp)
        ) { index ->
            ChallengeCategoryPagerItem(
                pagerIndex = index,
                themeItemList = themeItemList,
                onChallengeLikeClick = onChallengeLikeClick,
                onChallengeItemClick = onChallengeItemClick,
            )
        }

        PagerIndicator(
            modifier = Modifier.padding(top = 16.dp),
            pageCount = pagerState.pageCount,
            currentPageIndex = pagerState.currentPage,
        )
    }
}

@Composable
private fun ChallengeCategoryPagerItem(
    pagerIndex: Int,
    themeItemList: List<List<ChallengeThemeItemData>>,
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (themeItemList[pagerIndex].size > 3) {
            for (i in 0..2) {
                Box(
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    ChallengeHomeTileTypeLayout(
                        modifier = Modifier,
                        item = themeItemList[pagerIndex][i],
                        onChallengeLikeClick = onChallengeLikeClick,
                        onChallengeItemClick = onChallengeItemClick,
                    )
                }
            }
        }

    }
}

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color = if (currentPageIndex == iteration) CoolGray700 else CoolGray75

                if (currentPageIndex == iteration) {
                    Box(
                        modifier = modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color)
                            .height(8.dp)
                            .width(16.dp)
                    )
                } else {
                    Box(
                        modifier = modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }

            }
        }
    }
}
