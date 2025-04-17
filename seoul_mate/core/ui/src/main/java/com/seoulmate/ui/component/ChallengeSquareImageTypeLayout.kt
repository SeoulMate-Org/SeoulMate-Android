package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.theme.White

@Composable
fun ChallengeSquareImageTypeLayout(
    modifier: Modifier = Modifier.height(280.dp).width(320.dp),
    item: ChallengeItemData,
    fontSize: TextUnit = 18.sp,
    textColor: Color = White,
) {
    ConstraintLayout(
        modifier = modifier,
    ){
        val (image, info) = createRefs()

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
                },
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(item.imgUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Challenge Image",
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .constrainAs(info) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Title Challenge Item
            Text(
                modifier = Modifier.weight(1f),
                text = item.title,
                fontSize = fontSize,
                style = TextStyle(
                    color = textColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            // Interest Icon
            ChallengeInterestButton(
                isInterest = item.isInterest,
                size = 32.dp
            ) {

            }
        }

    }
}