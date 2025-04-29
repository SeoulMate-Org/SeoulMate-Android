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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black40
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeSquareImageTypeLayout(
    item: ChallengeItemData,
    imageSize: Dp = 160.dp,
    imageCornerRadius: Dp = 16.dp,
    fontSize: TextUnit = 16.sp,
    textColor: Color = Black40,
    isShowNowPop: Boolean = false,
    onItemCLick: (ChallengeItemData) -> Unit = {},
) {

    Column(
        modifier = Modifier
            .width(imageSize)
            .wrapContentHeight()
            .background(color = TrueWhite)
            .noRippleClickable {
                onItemCLick(item)
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
                    .background(color = CoolGray25),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imgUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_empty_challenge)
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
                isInterest = item.isInterest,
            )
            // NowPOP
            if (isShowNowPop) {
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
                    PpsText(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(R.string.str_now_pop),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = TrueWhite,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Challenge Title
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = item.title,
            style = TextStyle(
                fontSize = fontSize,
                color = textColor,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        // Challenge Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewChallengeSquareImageTypeLayout() {
    SeoulMateTheme {
        ChallengeSquareImageTypeLayout(
            item = ChallengeItemData(
                id = 11,
                title = "Third Challenge Title",
                imgUrl = "https://abcdabcd",
            ),
            isShowNowPop = true,
        )

    }
}