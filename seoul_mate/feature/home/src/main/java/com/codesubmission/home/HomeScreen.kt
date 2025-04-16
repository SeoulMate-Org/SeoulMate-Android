package com.codesubmission.home

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codesubmission.home.navigation.HomeBottomNav
import com.codesubmission.home.navigation.HomeNavHost
import com.codesubmission.home.ui.HomeTopBar
import com.codesubmission.home.ui.map.MapBottomSheetContent
import com.codesubmission.home.ui.map.MapBottomSheetType
import com.codesubmission.home.ui.map.MapItemListBottomSheet
import com.codesubmission.home.ui.rememberHomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.HomeNavigationSuiteScaffold
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBar
import com.seoulmate.ui.component.snackBarMessage
import com.seoulmate.ui.component.snackBarType
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
    ]
)
@Composable
fun HomeScreen(
    onPlaceInfoClick: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    onChangeScreen: (screen: Screen) -> Unit = {_ -> },
) {
    val homeState = rememberHomeState()
    val context = LocalContext.current

    var rejectedPermissions: Set<String>? = null

    val permissionState = rememberMultiplePermissionsState(permissions = homeState.getAllPermissionList()) { map ->
        rejectedPermissions = map.filterValues { !it }.keys
    }

    val revokedBackgroundLocationPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionState.revokedPermissions.all { Manifest.permission.ACCESS_BACKGROUND_LOCATION in it.permission }
        } else {
            false
        }

    val bottomNavHeight = remember { mutableIntStateOf(55) }
    val bottomSheetType =
        remember { mutableStateOf<MapBottomSheetType>(MapBottomSheetType.TestType) }

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

        homeState.createNotificationChannel(context)

        permissionState.launchMultiplePermissionRequest()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q &&
            revokedBackgroundLocationPermission) {
            Toast.makeText(context, "Background location permission is revoked", Toast.LENGTH_SHORT).show()
        }
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

    Scaffold(
        topBar = {
            HomeTopBar(
                currentRoute = currentDestination?.route ?: Screen.HomeMyPage.route,
                onSearchClick = {
                    Log.d("@@@@@", "SearchClick")
                },
            )
        },
        containerColor = TrueWhite,
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
    ) { padding ->
        Surface(
            modifier = Modifier.padding(paddingValues = padding)
        ) {
            Column {
                HomeNavHost(
                    modifier = Modifier.weight(1f),
                    appState = homeState,
                    context = context,
                    onScreenChange = onChangeScreen
                )
                HomeBottomNav(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    onHomeClick = {
                        homeState.navigate(Screen.HomeMain.route)
                    },
                    onMyPageClick = {
                        homeState.navigate(Screen.HomeMyPage.route)
                    },
                    onChallengeClick = {
                        homeState.navigate(Screen.HomeChallenge.route)
                    },
                    selectedRoute = currentDestination?.route ?: ""
                )
            }

//            PermissionBox(
//                permissions = homeState.getAllPermissionList()
//            ) {
//                homeState.getBackgroundLocationPermission()?.let {
//                    PermissionBox(
//                        permissions = listOf(it)
//                    ) {
//                        Column {
//                            HomeNavHost(
//                                modifier = Modifier.weight(1f),
//                                appState = homeState,
//                                context = context,
//                                onScreenChange = onChangeScreen
//                            )
//                            HomeBottomNav(
//                                modifier = Modifier
//                                    .height(55.dp)
//                                    .fillMaxWidth(),
//                                onHomeClick = {
//                                    homeState.navigate(Screen.HomeMain.route)
//                                },
//                                onMyPageClick = {
//                                    homeState.navigate(Screen.HomeMyPage.route)
//                                },
//                                onChallengeClick = {
//                                    homeState.navigate(Screen.HomeChallenge.route)
//                                },
//                                selectedRoute = currentDestination?.route ?: ""
//                            )
//                        }
//                    }
//                }
//            }


//            ConstraintLayout(
//
//            ) {
//                val (navHost, bottomBar) = createRefs()
//
//                PermissionBox(
//                    permissions = homeState.getAllPermissionList()
//                ) {
//                    homeState.getBackgroundLocationPermission()?.let {
//                        PermissionBox(
//                            permissions = listOf(it)
//                        ) { }
//                    }
//
//                }
//                HomeNavHost(
//                    modifier = Modifier
//                        .constrainAs(navHost) {
//                            top.linkTo(parent.top)
//                            bottom.linkTo(bottomBar.top)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        },
//                    appState = homeState,
//                    context = context,
//                )
//
//                // Bottom Navigation Bar
//                if(homeState.mapDetailState.value == null) {
//                    HomeBottomNav(
//                        modifier = Modifier
//                            .height(55.dp)
//                            .fillMaxWidth()
//                            .constrainAs(bottomBar) {
//                                bottom.linkTo(parent.bottom)
//                                start.linkTo(parent.start)
//                                end.linkTo(parent.end)
//                            },
//                        onHomeClick = {
//                            homeState.navigate(Screen.HomeMain.route)
//                        },
//                        onMyPageClick = {
//                            homeState.navigate(Screen.HomeMyPage.route)
//                        },
//                        onChallengeClick = {
//                            homeState.navigate(Screen.HomeChallenge.route)
//                        },
//                        selectedRoute = currentDestination?.route ?: ""
//                    )
//                }
//            }
        }
    }

//    BottomSheetScaffold(
//        modifier = Modifier
//            .fillMaxWidth(),
//        topBar = {
//            HomeTopBar(
//                currentRoute = currentDestination?.route ?: Screen.HomeMyPage.route,
//                titleRes = if(mapDetailState.value == null) { null } else { mapDetailState.value!!.titleRes },
//                onSearchClick = {
//                    Log.d("@@@@@", "SearchClick")
//                },
//            )
//        },
//        containerColor = Color.Transparent,
//        scaffoldState = homeState.bottomSheetScaffoldState,
//        sheetContent = {
//            if ((currentDestination?.route ?: "") == Screen.HomeMyPage.route) {
//                if(mapDetailState.value == null) {
//                    MapBottomSheetContent(
//                        onDetailClick = {
//
//                        }
//                    )
//                } else {
//                    MapItemListBottomSheet(
//                        itemList = mapBottomSheetDataList
//                    )
//                }
//            }
//        },
//        sheetPeekHeight =
//            if ((currentDestination?.route ?: "") == Screen.HomeMyPage.route) {
//                (bottomNavHeight.intValue + 50).dp
//            } else {
//                0.dp
//            },
//        snackbarHost = {
//            SnackbarHost(
//                hostState = homeState.snackBarkHostState,
//            ) { snackBarData ->
//                SnackBar(
//                    type = snackBarData.visuals.message.snackBarType(),
//                    snackText = snackBarData.visuals.message.snackBarMessage(),
//                )
//            }
//        }
//    ) { padding ->
//        ConstraintLayout(
//            modifier = Modifier.fillMaxSize().padding(padding)
//        ) {
//            val (navHost, bottomBar) = createRefs()
//
//            PermissionBox(
//                permissions = homeState.getAllPermissionList()
//            ) {
//                homeState.getBackgroundLocationPermission()?.let {
//                    PermissionBox(
//                        permissions = listOf(it)
//                    ) { }
//                }
//
//            }
//            HomeNavHost(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .constrainAs(navHost) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(bottomBar.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    },
//                appState = homeState,
//                context = context,
//            )
//
//            // Bottom Navigation Bar
//            if(homeState.mapDetailState.value == null) {
//                HomeBottomNav(
//                    modifier = Modifier
//                        .height(55.dp)
//                        .fillMaxWidth()
//                        .constrainAs(bottomBar) {
//                            bottom.linkTo(parent.bottom)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        },
//                    onHomeClick = {
//                        homeState.navigate(Screen.HomeMain.route)
//                    },
//                    onMyPageClick = {
//                        homeState.navigate(Screen.HomeMyPage.route)
//                    },
//                    onChallengeClick = {
//                        homeState.navigate(Screen.HomeChallenge.route)
//                    },
//                    selectedRoute = currentDestination?.route ?: ""
//                )
//            }
//        }
//    }
}


