package com.codesubmission.home.ui.challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.challenge.tabpage.CompleteChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InProgressChallengeTabScreen
import com.codesubmission.home.ui.challenge.tabpage.InterestChallengeTabScreen
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun HomeChallengeScreen(
    homeState: HomeState,
    onReplyClick: () -> Unit = {},
    onChallengeItemClick: (item: ChallengeItemData) -> Unit = {},
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
                pagerState = pagerState
            )
            // Challenge Tab Contents
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { index ->
                when(tabList[index]) {
                    ChallengeTabItem.Interest -> InterestChallengeTabScreen(
                        itemList = listOf(
                            ChallengeItemData(
                                id = 0,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 1,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 2,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 3,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 4,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 5,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 6,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 7,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 8,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 9,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 10,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 11,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),

                        ),
                        onItemClick = onChallengeItemClick
                    )
                    ChallengeTabItem.InProgress -> InProgressChallengeTabScreen(
                        itemList = listOf()
                    )
                    ChallengeTabItem.Complete -> CompleteChallengeTabScreen(
                        itemList = listOf()
                    )
                }
            }
        }
    }
}