package com.seoulmate.challenge.detail.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.AttractionItem
import com.seoulmate.ui.component.ChallengeInterestButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun AttractionItemTile(
    item: AttractionItem,
    onItemClick: (item: AttractionItem) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .background(color = TrueWhite)
            .clickable {
                onItemClick(item)
            },
    ) {
        Row(
            modifier = Modifier
                .background(color = TrueWhite),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Attraction Image
            ConstraintLayout(
                modifier = Modifier
                    .size(76.dp)
                    .background(color = TrueWhite, shape = RoundedCornerShape(10.dp))
            ) {
                val (img, stamp) = createRefs()

                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = CoolGray25)
                        .aspectRatio(1.0f)
                        .constrainAs(img) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Attraction Item Image",
                    contentScale = ContentScale.Crop,
                )

                if (item.isStamped) {
                   Image(
                       modifier = Modifier.size(24.dp)
                           .constrainAs(stamp) {
                               bottom.linkTo(parent.bottom)
                               end.linkTo(parent.end)
                           },
                       painter = painterResource(com.seoulmate.ui.R.drawable.ic_stamp),
                       contentDescription = "Stamp Icon",
                       contentScale = ContentScale.Fit
                   )
                }
            }
            // Attraction Content
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // Name
                PpsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.name ?: item.id.toString(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                // Count Row
                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray600,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.likes.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray600,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.stampCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray600,
                        )
                    )
                }
            }
            // Interest Button
            ChallengeInterestButton(
                isInterest = item.isLiked,
                size = 32.dp
            ) {

            }
        }
    }
}