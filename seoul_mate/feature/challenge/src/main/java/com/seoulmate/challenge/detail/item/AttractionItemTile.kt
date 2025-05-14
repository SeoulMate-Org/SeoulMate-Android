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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.ui.component.ChallengeInterestButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray200
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import kotlin.math.round

@Composable
fun AttractionItemTile(
    item: AttractionItem,
    grantedPermission: Boolean = false,
    distance: String? = null,
    onItemClick: (item: AttractionItem) -> Unit = {},
    onItemLikeClick: (item: AttractionItem) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 12.dp)
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = CoolGray50)
                        .aspectRatio(1.0f)
                        .constrainAs(img) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .placeholder(com.seoulmate.ui.R.drawable.ic_placeholder)
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
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900,
                        lineHeight = 17.sp,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                // Count Row
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (item.likes > 0) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite),
                            contentDescription = "Attraction Favorite",
                            tint = CoolGray100,
                        )
                        PpsText(
                            modifier = Modifier.padding(start = 5.dp),
                            text = item.likes.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CoolGray300,
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (item.stampCount > 0) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my),
                            contentDescription = "Attraction Favorite",
                            tint = CoolGray100,
                        )
                        PpsText(
                            modifier = Modifier.padding(start = 5.dp),
                            text = item.stampCount.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = CoolGray300,
                            )
                        )
                    }

                }
                // Location
                if (grantedPermission && distance != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(com.seoulmate.ui.R.drawable.ic_distance),
                            contentDescription = "Icon Distance",
                            tint = CoolGray200,
                        )
                        PpsText(
                            modifier = Modifier,
                            text = stringResource(
                                com.seoulmate.ui.R.string.distance_from_me, distance
                            ),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CoolGray400,
                            ),
                        )
                    }
                } else {
                    PpsText(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.address ?: "",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = CoolGray400,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            // Interest Button
            ChallengeInterestButton(
                isInterest = if (UserInfo.isUserLogin()) item.isLiked else false,
                size = 32.dp
            ) {
                onItemLikeClick(item)
            }
        }
    }
}