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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray200
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
                .aspectRatio(3.7f / 2.3f),
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
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            // Challenge Name
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = item.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color2B2B2B
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            // Title
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = item.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color2B2B2B,
                ),
                maxLines = 2,
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
                    count = item.likedCount,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my,
                    count = item.progressCount,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_fill_location,
                    count = item.attractionCount,
                )
                Spacer(modifier = Modifier.width(15.dp))
                //
                ChallengeDetailItem(
                    icon = com.seoulmate.ui.R.drawable.ic_fill_reply,
                    count = item.commentCount,
                )
                Spacer(modifier = Modifier.width(15.dp))
            }
            // Description
            PpsText(
                modifier = Modifier.wrapContentSize()
                    .padding(top = 8.dp),
                text = item.description,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray300,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
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
            tint = CoolGray200,
        )
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = count.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
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
                name = "First Challenge",
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
            ),
        )
    }
}