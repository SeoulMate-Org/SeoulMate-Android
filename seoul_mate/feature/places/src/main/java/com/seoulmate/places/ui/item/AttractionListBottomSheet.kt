package com.seoulmate.places.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.ui.R
import com.seoulmate.ui.component.ChallengeInterestButton
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray200
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun AttractionListBottomSheet(
    itemList: List<AttractionItem>,
    selectedPlace: Int? = null,
    onItemClick: (index: Int) -> Unit = {},
    onItemLikeClick: (attractionId: Int) -> Unit = {},
    onCopyClick: (str: String) -> Unit = {},
    onDetailClick: (item: AttractionItem) -> Unit = {},
) {

    LazyColumn(
        modifier = Modifier.background(color = TrueWhite)
    ){
        if (selectedPlace == null) {
            itemsIndexed(
                items = ChallengeDetailInfo.attractions,
                key = { index, item -> item.id }
            ) { index, item ->
                AttractionItemLayout(
                    item = item,
                    onItemClick = {
                        onItemClick(index)
                    },
                    onItemLikeClick = onItemLikeClick,
                )
            }
        } else {
            item {
                SelectedAttractionLayout(
                    item = itemList[selectedPlace],
                    distance = ChallengeDetailInfo.attractionDistance[selectedPlace],
                    onCopyClick = {
                        onCopyClick(itemList[selectedPlace].address ?: "")
                    },
                    onDetailClick = {
                        onDetailClick(itemList[selectedPlace])
                    },
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(65.dp))
        }

    }
}

@Composable
fun SelectedAttractionLayout(
    item: AttractionItem,
    distance: Float?,
    onCopyClick: () -> Unit = {},
    onDetailClick: () -> Unit = {},
    onItemLikeClick: (attractionId: Int) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        // Content info
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                PpsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.name ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(5.dp))
                PpsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.address ?: "",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = CoolGray300,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (distance != null && distance > 0) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_distance_blue),
                            contentDescription = "Distance Icon",
                            contentScale = ContentScale.Fit,
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        PpsText(
                            modifier = Modifier,
                            text = String.format(
                                "%.2f",
                                (distance / 1000)
                            ) + "km",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CoolGray600
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        VerticalDivider(
                            modifier = Modifier.height(16.dp).width(1.dp),
                            thickness = 1.dp,
                            color = CoolGray300,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_bottom_nav_fill_favorite),
                        contentDescription = "Attraction Favorite",
                        tint = CoolGray200,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 5.dp),
                        text = item.likes.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CoolGray600,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
            // Interest Icon
            ChallengeInterestButton(
                isInterest = item.isLiked,
                size = 40.dp
            ) {
                onItemLikeClick(item.id)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Button
        Row {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PpsButton(
                    modifier = Modifier.weight(1f),
                    stringRes = com.seoulmate.places.R.string.str_copy_address,
                    color = CoolGray50,
                    borderColor = CoolGray50,
                    fontColor = Black,
                    onClick = onCopyClick,
                    cornerRound = 10.dp,
                )
                Spacer(modifier = Modifier.width(10.dp))
                PpsButton(
                    modifier = Modifier.weight(1f),
                    stringRes = com.seoulmate.places.R.string.str_address_detail,
                    onClick = onDetailClick,
                    cornerRound = 10.dp,
                )
            }
        }
    }
}

@Composable
fun AttractionItemLayout(
    item: AttractionItem,
    onItemClick: () -> Unit = {},
    onItemLikeClick: (attractionId: Int) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .noRippleClickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        // Image
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
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
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            // Title
            PpsText(
                modifier = Modifier,
                text = item.name ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900,
                ),
            )
            // Address
            PpsText(
                modifier = Modifier,
                text = item.address ?: "",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray300,
                ),
            )
        }

        Spacer(modifier = Modifier.width(15.dp))
        // Interest Icon
        ChallengeInterestButton(
            isInterest = item.isLiked,
            size = 40.dp
        ) {
            onItemLikeClick(item.id)
        }
    }
}