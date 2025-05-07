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
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun AttractionTileTypeLayout (
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp, vertical = 8.dp),
    item: AttractionDetailData,
    onItemClick: (attractionId: Int) -> Unit = {},
    onItemLikeClick: (attractionId: Int) -> Unit = {},
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
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = CoolGray50)
                    .aspectRatio(1.0f),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .placeholder(com.seoulmate.ui.R.drawable.ic_placeholder)
                    .build(),
                contentDescription = "Map List Card Image",
                contentScale = ContentScale.Crop,
            )

            // Content Challenge Item
            Column(
                modifier = Modifier.weight(1f).padding(start = 8.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                // Name
                PpsText(
                    modifier = Modifier.padding(top = 4.dp),
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                // Count Row
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (item.likes > 0) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite),
                            contentDescription = "Attraction Favorite",
                            tint = CoolGray100,
                        )
                        PpsText(
                            modifier = Modifier.padding(start = 5.dp),
                            text = item.likes.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CoolGray300,
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (item.stampCount > 0) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my),
                            contentDescription = "Attraction Favorite",
                            tint = CoolGray100,
                        )
                        PpsText(
                            modifier = Modifier.padding(start = 5.dp),
                            text = item.stampCount.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CoolGray300,
                            )
                        )
                    }

                }

            }
            // Interest Button
            ChallengeInterestButton(
                isInterest = if (UserInfo.isUserLogin()) item.isLiked ?: false else false,
                size = 32.dp
            ) {
                onItemLikeClick(item.id)
            }
        }
    }
}