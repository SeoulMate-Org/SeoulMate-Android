package com.seoulmate.challenge.detail.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.challenge.R
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.SeoulMateTheme

@Composable
fun TopChallengeDetailInfo(
    item: ChallengeItemData,
) {
    Column {
        // Image
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3.7f / 2.3f)
                .background(color = CoolGray25),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(item.imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Challenge Detail Image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(15.dp))
        // Info
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
        ) {
            // Challenge Title
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = "\uD83E\uDDED 인사동 복합 감각 정복 챌린지 (챌린지명)",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray50,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            // Title
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = item.title,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = CoolGray500,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(35.dp))
            // Detail Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite,
                    count = 10,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my,
                    count = 0,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_fill_location,
                    count = 1,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_fill_reply,
                    count = 30,
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
            // Description
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = "관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 ",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray300,
                ),
            )
        }
    }
}

@Composable
private fun ChallengeDetailItem(
    @DrawableRes icon: Int = com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite,
    count: Int = 0,
) {
    Row{
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(icon),
            contentDescription = "Challenge Detail Info",
            tint = CoolGray300,
        )
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = count.toString(),
            style = TextStyle(
                fontSize = 13.sp,
                color = CoolGray300,
            )
        )
    }
}

@Preview
@Composable
private fun PreviewTopChallengeDetailInfo() {
    SeoulMateTheme {
        TopChallengeDetailInfo(
            item = ChallengeItemData(
                id = 0,
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
            ),
        )
    }
}