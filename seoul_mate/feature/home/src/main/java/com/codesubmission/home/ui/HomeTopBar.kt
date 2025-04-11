package com.codesubmission.home.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SeoulMateTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    currentRoute: String,
    @StringRes titleRes: Int? = null,
    isMapDetail: Boolean = false,
    onMapBackClick: () -> Unit = {},
) {
    when(currentRoute) {
        Screen.HomeTravelMap.route -> {
            if(isMapDetail) {
                SeoulMateTopAppBar(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    titleRes = titleRes ?: com.seoulmate.ui.R.string.bottom_nav_travel_map_title,
                    navigationIconRes = com.seoulmate.ui.R.drawable.ic_left,
                    navigationIconContentDescription = "Map Back Button",
                    onNavigationClick = onMapBackClick
                )
            } else {
                SeoulMateTopAppBar(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    titleRes = titleRes ?: com.seoulmate.ui.R.string.bottom_nav_travel_map_title,
                )
            }
        }
        Screen.HomeFavorite.route -> {
            SeoulMateTopAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                titleRes = titleRes ?: com.seoulmate.ui.R.string.bottom_nav_favorite_title,
            )
        }
        Screen.HomeSuggestTheme.route -> {
            SeoulMateTopAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                titleRes = titleRes ?: com.seoulmate.ui.R.string.bottom_nav_suggest_title,
            )
        }
    }
}