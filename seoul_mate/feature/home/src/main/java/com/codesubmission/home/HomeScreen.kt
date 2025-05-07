package com.codesubmission.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.codesubmission.home.navigation.HomeBottomNav
import com.codesubmission.home.navigation.HomeNavHost
import com.codesubmission.home.ui.HomeTopBar
import com.codesubmission.home.ui.rememberHomeState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBar
import com.seoulmate.ui.component.snackBarMessage
import com.seoulmate.ui.component.snackBarType
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.coroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.R
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.theme.SeoulMateTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    context: Context,
    goMainHome: MutableState<Boolean>,
    onPlaceInfoClick: () -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    onChangeScreen: (screen: Screen) -> Unit = {_ -> },
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onThemeMorClick: () -> Unit = {},
    firstShowLogin: () -> Unit = {},
    finishedLogout: () -> Unit = {},
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

    val currentDestination = homeState.currentDestination

    LaunchedEffect(Unit) {
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

    LaunchedEffect(viewModel.needUserLogin.value) {
        if(viewModel.needUserLogin.value) {
            showLoginAlertDialog = true
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
                    finishedLogout = finishedLogout,
                    goMainHome = goMainHome,
                )
                HomeBottomNav(
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
            Toast.makeText(context, context.getString(com.codesubmission.home.R.string.exit_app), Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

