package com.codesubmission.home.ui.challenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codesubmission.home.HomeAfterRefreshTokenType
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.challenge.tabpage.CompleteChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InProgressChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InterestChallengeTabScreen
import com.seoulmate.data.UserInfo
import com.seoulmate.data.dto.challenge.MyChallengeType
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun HomeChallengeScreen(
    homeState: HomeState,
    viewModel: HomeViewModel,
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onChangeScreen: (screen: Screen) -> Unit = { _ -> },
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

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.needRefreshToken.value = null

            when(viewModel.afterRefreshToken.value) {
                HomeAfterRefreshTokenType.MyChallengeLike -> {
                    viewModel.afterRefreshToken.value = null

                    viewModel.getMyChallenge(MyChallengeType.LIKE.type)
                }
                HomeAfterRefreshTokenType.MyChallengeProgress -> {
                    viewModel.afterRefreshToken.value = null

                    viewModel.getMyChallenge(MyChallengeType.PROGRESS.type)
                }
                HomeAfterRefreshTokenType.MyChallengeComplete -> {
                    viewModel.afterRefreshToken.value = null

                    viewModel.getMyChallenge(MyChallengeType.COMPLETE.type)
                }
                else -> {}
            }
        }
    }

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
                modifier = Modifier.background(color = White),
                state = pagerState,
                userScrollEnabled = false,
            ) { index ->
                when(tabList[index]) {
                    ChallengeTabItem.Interest -> InterestChallengeTabScreen(
                        itemList = UserInfo.myLikeChallengeList,
                        onItemClick = onChallengeItemClick,
                        onItemLikeClick = { challengeId ->
                            viewModel.reqChallengeLike(challengeId)
                        },
                        goMainHome = {
                            onChangeScreen(Screen.HomeMain)
                        }
                    )
                    ChallengeTabItem.InProgress -> InProgressChallengeTabScreen(
                        itemList = UserInfo.myProgressChallengeList,
                        onItemClick = onChallengeItemClick,
                        goMainHome = {
                            onChangeScreen(Screen.HomeMain)
                        }
                    )
                    ChallengeTabItem.Complete -> CompleteChallengeTabScreen(
                        itemList = UserInfo.myCompleteChallengeList,
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