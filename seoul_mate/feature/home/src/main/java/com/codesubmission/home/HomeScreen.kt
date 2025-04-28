package com.codesubmission.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.codesubmission.home.navigation.HomeBottomNav
import com.codesubmission.home.navigation.HomeNavHost
import com.codesubmission.home.ui.HomeTopBar
import com.codesubmission.home.ui.map.MapBottomSheetType
import com.codesubmission.home.ui.rememberHomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBar
import com.seoulmate.ui.component.snackBarMessage
import com.seoulmate.ui.component.snackBarType
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.coroutineScope
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.R
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.theme.SeoulMateTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    context: Context,
    onPlaceInfoClick: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    onChangeScreen: (screen: Screen) -> Unit = {_ -> },
    onChallengeItemClick: (item: ChallengeItemData) -> Unit = {},
    onThemeMorClick: () -> Unit = {},
    firstShowLogin: () -> Unit = {},
) {

    val homeState = rememberHomeState()
    val viewModel = hiltViewModel<HomeViewModel>()

    val permissionState = rememberMultiplePermissionsState(permissions = homeState.getAllPermissionList())

    val backgroundLocationPermissionState: PermissionState? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            rememberPermissionState(permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        } else {
            null
        }

    val fineLocationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    )

    // TODO chan Need Location Permission Alert
    var showRationale by remember(fineLocationPermissionState) {
        mutableStateOf(false)
    }

    var showLoginAlertDialog by remember { mutableStateOf(false) }

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

        coroutineScope {
            permissionState.launchMultiplePermissionRequest()

            if (fineLocationPermissionState.shouldShowRationale) {
                showRationale = true
            } else {
                fineLocationPermissionState.launchMultiplePermissionRequest()
            }

//            backgroundLocationPermissionState?.let {
//                if (it.status.shouldShowRationale) {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        data = "package:${context.packageName}".toUri()
//                    }
//                    context.startActivity(intent)
//                } else {
//                    it.launchPermissionRequest()
//                }
//            }

            firstShowLogin()

            val locationPermission = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (locationPermission.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }) {
                viewModel.getLastLocation()
            }

        }
    }

    LaunchedEffect(viewModel.lastLocation.value) {
        viewModel.lastLocation.value?.let {
            Log.d("@@@@@@", "lastLocation : ${it.latitude} , ${it.longitude}")
            val testLocation = Location("test").apply {
                latitude = 37.5686076
                longitude = 126.9816627
            }
            val d = viewModel.getLocationDistance(it, testLocation)
            Log.d("@@@@@@", "getLocationDistance : $d")
        }
    }

    LaunchedEffect(UserInfo.nickName) {
        Log.d("@@@@@@", "HomeScreen : ${UserInfo.nickName}")
    }

    BackOnPressed()
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
                    viewModel = viewModel,
                    onScreenChange = onChangeScreen,
                    onChallengeItemClick = onChallengeItemClick,
                    onThemeMoreClick = onThemeMorClick,
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
                        if (UserInfo.isUserLogin()) {
                            homeState.navigate(Screen.HomeChallenge.route)
                        } else {
                            showLoginAlertDialog = true
                        }
                    },
                    selectedRoute = currentDestination?.route ?: ""
                )
            }

            if(showLoginAlertDialog) {
                SeoulMateTheme {
                    PpsAlertDialog(
                        titleRes = R.string.str_need_login,
                        descriptionRes = R.string.str_need_login_description,
                        confirmRes = R.string.str_login,
                        cancelRes = R.string.str_cancel,
                        onClickCancel = {
                            showLoginAlertDialog = false
                        },
                        onClickConfirm = {
                            onChangeScreen(Screen.Login)
                            showLoginAlertDialog = false
                        },
                    )
                }
            }
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


// 뒤로 가기 두 번 눌렀을 때 앱 종료
@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if(System.currentTimeMillis() - backPressedTime <= 400L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

