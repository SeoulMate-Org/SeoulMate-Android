package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeRankingTileTypeLayout(
    modifier: Modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    backColor: Color = TrueWhite,
    fontColor: Color = CoolGray600,
    index: Int = 0,
    item: ChallengeItemData,
    onItemClick: (item: ChallengeItemData) -> Unit = {},
) {
    Card(
        modifier = modifier
            .noRippleClickable {
                onItemClick(item)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardColors(
            contentColor = backColor,
            containerColor = backColor,
            disabledContentColor = CoolGray25,
            disabledContainerColor = CoolGray25,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            // Show Ranking Index
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = (index + 1).toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = CoolGray600,
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
            // Image
            AsyncImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = CoolGray25),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imgUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_empty_challenge)
                    .build(),
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(15.dp))
            // Title
            PpsText(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                text = item.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = fontColor,
                ),
            )
            Spacer(modifier = Modifier.width(15.dp))
            // Interest Icon
            ChallengeInterestButton(
                isInterest = item.isInterest,
                size = 32.dp
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewChallengeRankingTileTypeLayout() {
    SeoulMateTheme {
        ChallengeRankingTileTypeLayout(
            item = ChallengeItemData(
                id = 0,
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                isInterest = true,
            ),
        )
    }
}