package com.codesubmission.home.ui.challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.challenge.tabpage.CompleteChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InProgressChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InterestChallengeTabScreen
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch

@Composable
fun HomeChallengeScreen(
    homeState: HomeState,
    viewModel: HomeViewModel,
    onReplyClick: () -> Unit = {},
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
) {
    val tabList = listOf(
        ChallengeTabItem.Interest,
        ChallengeTabItem.InProgress,
        ChallengeTabItem.Complete,
    )

    val pagerState = rememberPagerState(
        pageCount = { tabList.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    Surface(
        color = TrueWhite
    ) {
        Column {
            // Top Challenge Tab Layout
            ChallengeTabRow(
                coroutineScope = homeState.coroutineScope,
                pageList = tabList.map { stringResource(it.titleRes) },
                pagerState = pagerState,
                onTabClick = { selectedIndex ->
                    homeState.coroutineScope.launch {
                        viewModel.getMyChallenge(
                            when(selectedIndex) {
                                0 -> MyChallengeType.LIKE.type
                                1 -> MyChallengeType.PROGRESS.type
                                2 -> MyChallengeType.COMPLETE.type
                                else -> MyChallengeType.LIKE.type
                            }
                        )
                        pagerState.animateScrollToPage(selectedIndex)
                    }
                }
            )
            // Challenge Tab Contents
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { index ->
                when(tabList[index]) {
                    ChallengeTabItem.Interest -> InterestChallengeTabScreen(
                        itemList = viewModel.myLikeChallengeList.value,
                        onItemClick = onChallengeItemClick,
                        onItemLikeClick = { challengeId ->
                            viewModel.reqChallengeLike(challengeId)
                        },
                    )
                    ChallengeTabItem.InProgress -> InProgressChallengeTabScreen(
                        itemList = viewModel.myProgressChallengeList.value,
                        onItemClick = onChallengeItemClick,
                    )
                    ChallengeTabItem.Complete -> CompleteChallengeTabScreen(
                        itemList = viewModel.myCompleteChallengeList.value,
                        onItemClick = onChallengeItemClick,
                        onItemLikeClick = { challengeId ->
                            viewModel.reqChallengeLike(challengeId)
                        },
                    )
                }
            }
        }
    }
}