package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.theme.CoolGray50

@Composable
fun MapListCardItem(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    item: MapListCardItemData,
    onItemClick: (MapListCardItemData) -> Unit,
    onFavoriteClick: (MapListCardItemData) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
        ),
        onClick = {
            onItemClick(item)
        }
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp),
        ) {
            // 이미지
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Top)
                    .weight(0.3f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = CoolGray50)
                    .aspectRatio(1.0f),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(item.imgUrl)
                    .placeholder(com.seoulmate.ui.R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = "Map List Card Image",
                contentScale = ContentScale.Crop,
            )
            // Title + Address
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .weight(1.0f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(item.title)
                Text(item.address)
            }
            // 좋아요
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.3f)
                    .clip(RoundedCornerShape(45.dp))
                    .aspectRatio(1.0f)
                    .background(color = Color.LightGray),
                onClick = {
                    onFavoriteClick(item)
                }
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun PreviewMapListCardItem() {
    MapListCardItem(
        item = MapListCardItemData(
            title = "title",
            imgUrl = "imgUrl",
            address = "address",
        ),
        onItemClick = {},
        onFavoriteClick = {},
    )
}