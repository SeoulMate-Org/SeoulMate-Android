package com.seoulmate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.White

@Composable
fun ChallengeBigSquareImageTypeLayout(
    modifier: Modifier = Modifier.wrapContentSize(),
    item: ChallengeCulturalEventData,
    fontSize: TextUnit = 18.sp,
    textColor: Color = White,
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .noRippleClickable {
                onChallengeItemClick(item.id)
            },
    ){
        val (image, info, dim) = createRefs()

        // Image Challenge Item
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
                .aspectRatio(1.0f)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .constrainAs(dim) {
                    bottom.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clip(
                shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.img_rounded_bottom_dim),
                contentDescription = "Challenge Dim",
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp)
                .constrainAs(info) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Title Challenge Item
            PpsText(
                modifier = Modifier.weight(1f),
                text = item.title,
                style = TextStyle(
                    fontSize = fontSize,
                    color = textColor,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            // Interest Icon
            IconButton(
                onClick = {
                    onChallengeLikeClick(item.id)
                }
            ) {
                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(
                        if (item.isLiked) R.drawable.ic_bottom_nav_fill_favorite
                        else R.drawable.ic_bottom_nav_favorite
                    ),
                    contentDescription = "Challenge Like",
                    contentScale = ContentScale.Fit,
                )
            }
        }

    }
}
