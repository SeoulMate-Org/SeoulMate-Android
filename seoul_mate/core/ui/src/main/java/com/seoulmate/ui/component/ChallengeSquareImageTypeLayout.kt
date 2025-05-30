package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeLocationData
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black40
import com.seoulmate.ui.theme.Blue50
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeSquareImageTypeLayout(
    item: ChallengeLocationData,
    imageSize: Dp = 160.dp,
    imageCornerRadius: Dp = 16.dp,
    fontSize: TextUnit = 16.sp,
    textColor: Color = Black40,
    isShowNowPop: Boolean = false,
    onItemCLick: (challengeId: Int) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .width(imageSize)
            .wrapContentHeight()
            .background(color = TrueWhite)
            .noRippleClickable {
                onItemCLick(item.id)
            },
    ) {
        // Challenge Image + interest Icon + Now POP
        ConstraintLayout(
            modifier = Modifier
                .size(imageSize)
                .clip(RoundedCornerShape(imageCornerRadius)),
        ) {
            val (image, interest, nowPop) = createRefs()
            // Image
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
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
            // Icon interest
            ChallengeInterestButton(
                modifier = Modifier.constrainAs(interest) {
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                },
                isInterest = if (UserInfo.isUserLogin()) item.isLiked else false,
            )
            // NowPOP
            if (item.distance > 0) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(bottomEnd = imageCornerRadius))
                        .background(color = Blue500)
                        .constrainAs(nowPop) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(R.drawable.ic_distance),
                            contentDescription = "Distance Icon",
                            tint = Blue50,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        PpsText(
                            modifier = Modifier,
                            text = "${item.distance}km",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Blue50,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Challenge Name
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = item.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color2B2B2B,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        // Challenge Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
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
    }
}
