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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeBigSquareImageTypeLayout
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun HorizontalCarousel(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .height(280.dp),
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
            ChallengeBigSquareImageTypeLayout(item = pageItem)

        }
    }
}

@Preview
@Composable
private fun HorizontalCarouselPreview() {
    SeoulMateTheme {
        HorizontalCarousel(
            itemList = listOf(
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
        )
    }
}
