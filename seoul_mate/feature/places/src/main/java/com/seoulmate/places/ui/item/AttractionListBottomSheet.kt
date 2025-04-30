package com.seoulmate.places.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.ui.R
import com.seoulmate.ui.component.ChallengeInterestButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun AttractionListBottomSheet(
    onItemLikeClick: (attractionId: Int) -> Unit = {},
) {
    LazyColumn{
        items(
            items = ChallengeDetailInfo.attractions,
            key = { item -> item.id }
        ) { item ->

            AttractionItemLayout(
                item = item,
                onItemLikeClick = onItemLikeClick,
            )
        }

    }
}

@Composable
fun AttractionItemLayout(
    item: AttractionItem,
    onItemLikeClick: (attractionId: Int) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        // Image
        AsyncImage(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = CoolGray25),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("")
                .crossfade(true)
                .placeholder(R.drawable.ic_empty_challenge)
                .build(),
            contentDescription = "Challenge Image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        ) {
            // Title
            PpsText(
                modifier = Modifier,
                text = item.name ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = CoolGray900,
                ),
            )
            // Address
            PpsText(
                modifier = Modifier,
                text = item.address ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = CoolGray900,
                ),
            )
        }

        Spacer(modifier = Modifier.width(15.dp))
        // Interest Icon
        ChallengeInterestButton(
            isInterest = item.isLiked,
            size = 32.dp
        ) {
            onItemLikeClick(item.id)
        }
    }
}