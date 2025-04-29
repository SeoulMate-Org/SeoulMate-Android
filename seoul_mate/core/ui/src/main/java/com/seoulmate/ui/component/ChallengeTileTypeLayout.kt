package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.theme.TrueWhite

// TODO chan make layout height params
@Composable
fun ChallengeTileTypeLayout(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(92.dp)
        .padding(horizontal = 15.dp)
        .padding(top = 10.dp),
    item: ChallengeItemData,
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
                    .data(item.imgUrl)
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
                // Theme List
                var strTheme = ""
                item.themeList.forEach { theme ->
                    strTheme += " $theme"
                }
                PpsText(
                    modifier = Modifier.wrapContentWidth(),
                    text = strTheme,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                // Title
                PpsText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = item.title,
                )

            }
            // Interest Button
            ChallengeInterestButton(
                isInterest = item.isInterest,
                size = 32.dp
            ) {

            }
        }
    }
}