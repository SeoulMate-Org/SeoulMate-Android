package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeRankingTileTypeLayout(
    modifier: Modifier = Modifier.fillMaxWidth().wrapContentHeight(),
    backColor: Color = TrueWhite,
    fontColor: Color = CoolGray600,
    index: Int = 0,
    item: ChallengeRankItemData,
    onItemClick: (item: ChallengeRankItemData) -> Unit = {},
    onItemLikeClick: (item: ChallengeRankItemData) -> Unit = {},
) {
    Card(
        modifier = modifier
            .heightIn(min = 80.dp)
            .noRippleClickable {
                onItemClick(item)
            },
        shape = RoundedCornerShape(12.dp),
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
                style = MaterialTheme.typography.titleLarge.copy(
                    color = CoolGray600,
                )
            )
            Spacer(modifier = Modifier.width(15.dp))
            // Image
            AsyncImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = CoolGray50),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_placeholder)
                    .build(),
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                // Title
                PpsText(
                    modifier = Modifier,
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color2B2B2B,
                        lineHeight = 17.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                // Progress Count
                PpsText(
                    modifier = Modifier.padding(top = 3.dp),
                    text = stringResource(R.string.home_challenge_ranking_join_count, item.progressCount),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = CoolGray300,
                    ),
                )
            }

            Spacer(modifier = Modifier.width(15.dp))
            // Interest Icon
            ChallengeInterestButton(
                isInterest = if (UserInfo.isUserLogin()) item.isLiked ?: false else false,
                size = 32.dp
            ) {
                onItemLikeClick(item)
            }
        }
    }
}