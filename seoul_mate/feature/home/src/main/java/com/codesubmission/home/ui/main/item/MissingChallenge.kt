package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codesubmission.home.R
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeStampItemData
import com.seoulmate.ui.component.LinearStampIndicator
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue200
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.MainMissingChallengeGradientStart
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@Composable
fun MissingChallenge(
    onItemClick: (challengeId: Int) -> Unit = {},
    startChallengeClick: (challengeId: Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(listOf(MainMissingChallengeGradientStart, TrueWhite)),
                shape = RectangleShape,
                alpha = 1f,
            )
    ) {
        // Title
        Row(
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                PpsText(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(R.string.missing_challenge_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = White,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                PpsText(
                    modifier = Modifier.padding(start = 20.dp, bottom = 16.dp, top = 4.dp),
                    text = stringResource(R.string.missing_challenge_sub_title),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray25,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Image(
                modifier = Modifier.size(138.dp),
                painter = painterResource(com.seoulmate.ui.R.drawable.ic_home_missing),
                contentDescription = "Main Top Title Image"
            )
        }
        // Item Row
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = ChallengeInfo.getChallengeStampList(),
                key = { index, item -> item.id },
            ) { index, item ->
                Row {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                        MissionChallengeItem(
                            item = item,
                            onItemClick = onItemClick,
                            startChallengeClick = startChallengeClick,
                        )
                    }
                    if (index == ChallengeInfo.getChallengeStampList().lastIndex) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun MissionChallengeItem(
    item: ChallengeStampItemData,
    onItemClick: (challengeId: Int) -> Unit = {},
    startChallengeClick: (challengeId: Int) -> Unit = {},
) {
    val totalAttractionItemCount = item.attractionCount
    val stampedAttractionItemCount = item.myStampCount

    Column(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight()
            .noRippleClickable {
                onItemClick(item.id)
            },
    ) {
        ConstraintLayout {
            val (img, attractionRow) = createRefs()

            // Image
            AsyncImage(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = CoolGray50)
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .placeholder(com.seoulmate.ui.R.drawable.ic_placeholder)
                    .build(),
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
            )
            // Attraction Row
            if (totalAttractionItemCount > 0) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .constrainAs(attractionRow) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        for (index in 0 until totalAttractionItemCount) {
                            LinearStampIndicator(
                                modifier = Modifier.weight(1f),
                                isCompleted = index < stampedAttractionItemCount,
                                height = 6.dp,
                                horizontalPadding = 2.dp,
                            )
                        }
                    }
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "${item.myStampCount ?: 0}/${item.attractionCount}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = White,
                        ),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Start Challenge Button
        PpsButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            borderColor = Blue200,
            stringRes = R.string.start_challenge,
            color = TrueWhite,
            fontColor = Blue500,
            cornerRound = 12.dp
        ){
            startChallengeClick(item.id)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Challenge Name
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
        // Like / Location
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            if (item.likes > 0) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite),
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
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_fill_location),
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