package com.codesubmission.home.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.MapListCardItem

sealed class MapBottomSheetType() {
    data object ItemListType: MapBottomSheetType()
    data object TestType: MapBottomSheetType()
}

@Composable
fun MapItemListBottomSheet(
    itemList: List<MapListCardItemData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.8).dp)
            .padding(16.dp)
    ) {
        // Tab
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(color = Color.LightGray)
        ) {

        }
        // List Item
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = itemList,
                key = { index, item ->
                    index
                }
            ) { index, item ->
                MapListCardItem(
                    item = item,
                    onItemClick = { },
                    onFavoriteClick = { }
                )
            }
        }

//        Spacer(modifier = Modifier.height(55.dp))
    }
}

@Composable
fun MapBottomSheetContent(
    onDetailClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.8).dp)
            .padding(16.dp)
    ) {
        Text("This is a custom Bottom Sheet")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDetailClick) {
            Text("Click Detail")
        }
        Spacer(modifier = Modifier.height(55.dp))
    }
}