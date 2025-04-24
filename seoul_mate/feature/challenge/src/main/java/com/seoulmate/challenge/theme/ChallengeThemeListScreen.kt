package com.seoulmate.challenge.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.challenge.R
import com.seoulmate.challenge.theme.item.ChallengeThemeTabRow
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeHomeTileTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeThemeListScreen(
    coroutineScope: CoroutineScope,
    onBackClick: () -> Unit = {},
) {
    val testRankingList = listOf(
        ChallengeItemData(
            id = 0,
            title = "First Challenge Title",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
            isInterest = true,
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
            isInterest = true,
        ),
        ChallengeItemData(
            id = 4,
            title = "Second Challenge Title",
            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 5,
            title = "Third Challenge Title",
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 6,
            title = "First Challenge Title",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
        ),
    )

    val testList = listOf(
        "핵심 관광지 정복",
        "인생샷&인증샷",
        "도보 여행",
        "핵심 관광지 정복",
        "인생샷&인증샷",
        "도보 여행",
    )

    val pagerState = rememberPagerState(
        pageCount = { testList.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(R.string.title_theme),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Back Icon",
                            tint = Black,
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = TrueWhite,
                    scrolledContainerColor = TrueWhite,
                    navigationIconContentColor = CoolGray900,
                    titleContentColor = CoolGray900,
                    actionIconContentColor = CoolGray900,
                    subtitleContentColor = CoolGray900,
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(color = TrueWhite),
        ) {
            ChallengeThemeTabRow(
                coroutineScope = coroutineScope,
                pageList = testList,
                pagerState = pagerState,
            )

            // Challenge Tab Contents
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { index ->
//                when (testList[index]) {
//
//                }
                LazyColumn {
                    items(
                        count = testRankingList.size,
                        key = { index -> testRankingList[index].id }
                    ) { index ->
                        ChallengeHomeTileTypeLayout(
                            modifier = Modifier.fillMaxWidth(),
                            item = testRankingList[index],
                        )
                    }
                }
            }
        }
    }
}