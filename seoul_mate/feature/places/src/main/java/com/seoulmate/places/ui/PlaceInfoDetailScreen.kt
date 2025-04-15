package com.seoulmate.places.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.seoulmate.data.model.PlaceInfoData
import com.seoulmate.ui.component.SeoulMateTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceInfoDetailScreen(
    placeData: PlaceInfoData,
    onBackClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SeoulMateTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(Color.Transparent)
                    .statusBarsPadding(),
                strTitle = placeData.title,
                navigationIconRes = com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_home,
                navigationIconContentDescription = "topBar back",
                onNavigationClick = onBackClick,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                    subtitleContentColor = Color.Black,
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(
                bottom = padding.calculateBottomPadding()
            ),
        ) {
            item {
                Surface(
                    color = Color.Cyan
                ) {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                    )
                }
            }
            item {
                Surface(
                    color = Color.Green
                ) {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                    )
                }
            }
            item {
                Surface(
                    color = Color.Yellow
                ) {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                    )
                }
            }
            item {
                Surface(
                    color = Color.LightGray
                ) {
                    Box(
                        modifier = Modifier
                            .height(550.dp)
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}