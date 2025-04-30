package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.MyChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeTileTypeLayout(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 15.dp)
        .padding(top = 10.dp),
    item: MyChallengeItemData,
    onItemClick: (challengeId: Int) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .background(color = TrueWhite)
            .clickable {
                onItemClick(item.id)
            },
    ) {
        Row(
            modifier = Modifier
                .background(color = TrueWhite),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Challenge Item
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Top)
                    .height(76.dp)
                    .width(76.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .aspectRatio(1.0f),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Map List Card Image",
                contentScale = ContentScale.Crop,
            )

            // Content Challenge Item
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Theme NAme
                PpsText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = item.challengeThemeName,
                )
                // Title
                PpsText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = item.title,
                )

                // Count Row
                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray600,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.likes.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_fill_reply),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray600,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.myStampCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_fill_location),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray600,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.attractionCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        )
                    )
                }

            }
            // Interest Button
            ChallengeInterestButton(
                isInterest = item.isLiked,
                size = 32.dp
            ) {

            }
        }
    }
}