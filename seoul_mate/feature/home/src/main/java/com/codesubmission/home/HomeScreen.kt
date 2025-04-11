package com.codesubmission.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codesubmission.home.navigation.HomeBottomNav
import com.codesubmission.home.navigation.HomeNavHost
import com.codesubmission.home.ui.HomeTopBar
import com.codesubmission.home.ui.map.MapBottomSheetContent
import com.codesubmission.home.ui.map.MapBottomSheetType
import com.codesubmission.home.ui.map.MapItemListBottomSheet
import com.codesubmission.home.ui.rememberHomeState
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.RequestPermission
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBar
import com.seoulmate.ui.component.snackBarMessage
import com.seoulmate.ui.component.snackBarType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    permissionList: List<String>,
    onPlaceInfoClick: () -> Unit,
) {
    val homeState = rememberHomeState()

    val permissionVisibleState = remember { mutableStateOf(true) }
    val bottomNavHeight = remember { mutableIntStateOf(55) }
    val bottomSheetType = remember { mutableStateOf<MapBottomSheetType>(MapBottomSheetType.TestType) }

    val currentDestination = homeState.currentDestination
    val mapDetailState = homeState.mapDetailState
    val mapBottomSheetDataList = listOf(
        MapListCardItemData(
            title = "First MapListCardItemData Title",
            address = "First MapListCardItemData Address",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg"
        ),
        MapListCardItemData(
            title = "Second MapListCardItemData Title",
            address = "Second MapListCardItemData Address",
            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg"
        ),
        MapListCardItemData(
            title = "Third MapListCardItemData Title",
            address = "Third MapListCardItemData Address",
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s"
        ),
        MapListCardItemData(
            title = "First MapListCardItemData Title",
            address = "First MapListCardItemData Address",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg"
        )
    )

    LaunchedEffect(Unit) {
        bottomSheetType.value = MapBottomSheetType.TestType
    }

    LaunchedEffect(homeState.mapDetailState.value) {
        if (homeState.mapDetailState.value != null) {
            bottomSheetType.value = MapBottomSheetType.ItemListType
            bottomNavHeight.intValue = 0
        } else {
            bottomNavHeight.intValue = 55
            bottomSheetType.value = MapBottomSheetType.TestType
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (content, bottomBar) = createRefs()

            BottomSheetScaffold(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                topBar = {
                    HomeTopBar(
                        currentRoute = currentDestination?.route ?: Screen.HomeTravelMap.route,
                        titleRes = if(mapDetailState.value == null) { null } else { mapDetailState.value!!.titleRes },
                        isMapDetail = mapDetailState.value != null,
                        onMapBackClick = {
                            homeState.onChangeMapDetailState(null)
                        }
                    )
                },
                containerColor = Color.Transparent,
                scaffoldState = homeState.bottomSheetScaffoldState,
                sheetContent = {
                    if ((currentDestination?.route ?: "") == Screen.HomeTravelMap.route) {
                        if(mapDetailState.value == null) {
                            MapBottomSheetContent(
                                onDetailClick = {

                                }
                            )
                        } else {
                            MapItemListBottomSheet(
                                itemList = mapBottomSheetDataList
                            )
                        }
                    }
                },
                sheetPeekHeight =
                if ((currentDestination?.route ?: "") == Screen.HomeTravelMap.route) {
                    (bottomNavHeight.intValue + 50).dp
                } else {
                    0.dp
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = homeState.snackBarkHostState,
                    ) { snackBarData ->
                        SnackBar(
                            type = snackBarData.visuals.message.snackBarType(),
                            snackText = snackBarData.visuals.message.snackBarMessage(),
                        )
                    }
                }
            ) {
                ConstraintLayout {
                    val (permission, content) = createRefs()

                    HomeNavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(content) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        appState = homeState,
                    )

                    if (permissionVisibleState.value) {
                        RequestPermission(
                            modifier = Modifier
                                .fillMaxSize()
                                .constrainAs(permission) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            permissionList = permissionList,
                            permissionSettingConfirmText = "권한 설정",
                            granted = {
                                permissionVisibleState.value = false
                            },
                            dismiss = {
                                permissionVisibleState.value = false
                            }
                        )
                    }

                }
            }
            // Bottom Navigation Bar
            if(homeState.mapDetailState.value == null) {
                HomeBottomNav(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth()
                        .constrainAs(bottomBar) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    onFavoriteClick = {
                        homeState.navigate(Screen.HomeFavorite.route)
                    },
                    onMapClick = {
                        homeState.navigate(Screen.HomeTravelMap.route)
                    },
                    onSuggestClick = {
                        homeState.navigate(Screen.HomeSuggestTheme.route)
                    },
                    selectedRoute = currentDestination?.route ?: ""
                )
            }
        }
    }

}


