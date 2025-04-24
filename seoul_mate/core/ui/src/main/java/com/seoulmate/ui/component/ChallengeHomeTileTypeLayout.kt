package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.Blue50
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.White

@Composable
fun ChallengeHomeTileTypeLayout(
    modifier: Modifier,
    item: ChallengeItemData,
) {
    Row(
        modifier = modifier
            .height(92.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        // Image Challenge Item
        AsyncImage(
            modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = CoolGray50),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(item.imgUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_empty_challenge)
                .build(),
            contentDescription = "Challenge Image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(8.dp))
        // Challenge Info
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Title
            PpsText(
                modifier = Modifier,
                text = item.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color2B2B2B,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Like / Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_bottom_nav_fill_favorite),
                    contentDescription = "Challenge Interest Icon",
                    tint = CoolGray100,
                )
                PpsText(
                    modifier = Modifier,
                    text = "12",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGray300,
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_fill_location),
                    contentDescription = "Challenge Interest Icon",
                    tint = CoolGray100,
                )
                PpsText(
                    modifier = Modifier,
                    text = "5",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGray300,
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Major Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(4.dp),
                            color = Blue50
                        ),
                ) {
                    PpsText(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = stringResource(R.string.str_major_location),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Blue500,
                        )
                    )
                }
                PpsText(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "한남동/이태원",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGray600,
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Interest Icon
        ChallengeInterestButton(
            isInterest = item.isInterest,
            size = 32.dp
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewChallengeHomeTileTypeLayout() {
    SeoulMateTheme {
        ChallengeHomeTileTypeLayout(
            modifier = Modifier.width(200.dp),
            item = ChallengeItemData(
                id = 11,
                title = "Third Challenge Title",
                imgUrl = "https://abcdabcd",
            )
        )
    }

}