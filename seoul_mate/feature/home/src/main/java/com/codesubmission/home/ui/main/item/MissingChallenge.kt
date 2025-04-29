package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codesubmission.home.R
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.ui.component.LinearStampIndicator
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color2B2B2B
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.MainMissingChallengeGradientStart
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@Composable
fun MissingChallenge(

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
                    modifier = Modifier.padding(start = 20.dp, bottom = 16.dp),
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
                painter = painterResource(com.seoulmate.ui.R.drawable.ic_home_missing),
                contentDescription = "Main Top Title Image"
            )
        }
        // Item Row
        LazyRow {
            itemsIndexed(
                items = UserInfo.myChallengeLocationList,
                key = { index, item -> item.id },
            ) { index, item ->
                MissionChallengeItem(item = UserInfo.myChallengeLocationList[index])
            }
        }
    }
}

@Composable
fun MissionChallengeItem(
    item: ChallengeLocationItemData,
) {
    Column(
        modifier = Modifier.width(160.dp),
    ) {
        ConstraintLayout {
            val (img, attractionRow) = createRefs()

            // Image
            AsyncImage(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = CoolGray25)
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
                    .placeholder(com.seoulmate.ui.R.drawable.ic_empty_challenge)
                    .build(),
                contentDescription = "Challenge Image",
                contentScale = ContentScale.Crop,
            )
            // Attraction Row
            if (item.attractionCount > 0) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .constrainAs(attractionRow) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        for (i in 1..item.attractionCount) {
                            LinearStampIndicator(
                                modifier = Modifier.weight(1f),
                                isCompleted = i <= (item.myStampCount ?: 0),
                            )
                            if (i > 1) {
                                Spacer(modifier = Modifier.width(4.dp))
                            }
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
        Spacer(modifier = Modifier.padding(top = 8.dp))
        // Start Challenge Button
        PpsButton(
            modifier = Modifier.weight(1f),
            stringRes = R.string.start_challenge,
            color = TrueWhite,
            fontColor = Blue500,
        ){

        }
        Spacer(modifier = Modifier.padding(top = 8.dp))
        // Challenge Title
        PpsText(
            modifier = Modifier,
            text = item.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color2B2B2B
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        // Like / Location
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
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