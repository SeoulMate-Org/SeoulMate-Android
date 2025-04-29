package com.seoulmate.challenge.attraction.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.seoulmate.data.model.attraction.AttractionDetailData
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray500

@Composable
fun TopAttractionDetailInfo(
    item: AttractionDetailData,
) {
    Column {
        // Image
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3.7f / 2.3f)
                .background(color = CoolGray25),
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(item.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Challenge Detail Image",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(15.dp))
        // Info
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
        ) {
            // Attraction name
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = item.name,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray50,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(35.dp))
            // Description
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = "관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 설명관광지 ",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray300,
                ),
            )
        }
    }
}