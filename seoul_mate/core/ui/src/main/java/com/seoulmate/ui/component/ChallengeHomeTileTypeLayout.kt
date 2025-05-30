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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeThemeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue50
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600

@Composable
fun ChallengeHomeTileTypeLayout(
    modifier: Modifier,
    item: ChallengeThemeItemData,
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
) {

    Row(
        modifier = modifier
            .wrapContentHeight()
            .noRippleClickable {
                onChallengeItemClick(item.id)
            },
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
                .data(item.imageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_placeholder)
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
                    lineHeight = 17.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(2.dp))
            // Like / Location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                if (item.likes > 0) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_bottom_nav_fill_favorite),
                        contentDescription = "Challenge Interest Icon",
                        tint = CoolGray100,
                    )
                    PpsText(
                        modifier = Modifier,
                        text = item.likes.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray300,
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

                if (item.attractionCount > 0) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_fill_location),
                        contentDescription = "Challenge Interest Icon",
                        tint = CoolGray100,
                    )
                    PpsText(
                        modifier = Modifier,
                        text = item.attractionCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray300,
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.height(5.dp))
            // Major Location
            if (item.mainLocation.isNotEmpty()) {
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
                        text = item.mainLocation ?: "",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Interest Icon
        ChallengeInterestButton(
            isInterest = if (UserInfo.isUserLogin()) item.isLiked else false,
            size = 32.dp
        ) {
            onChallengeLikeClick(item.id)
        }
    }
}
