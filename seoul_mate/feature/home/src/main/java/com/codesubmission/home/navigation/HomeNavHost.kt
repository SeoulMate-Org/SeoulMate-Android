package com.codesubmission.home.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.challenge.HomeChallengeScreen
import com.codesubmission.home.ui.main.HomeMainScreen
import com.codesubmission.home.ui.mypage.HomeMyPageScreen
import com.seoulmate.ui.component.Screen

@Composable
fun HomeNavHost(
    appState: HomeState,
    modifier: Modifier,
    context: Context,
    viewModel: HomeViewModel,
    onScreenChange: (screen: Screen) -> Unit = { _ -> },
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onThemeMoreClick: () -> Unit = {},
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.HomeMain.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeMain.route) {
            HomeMainScreen(
                context = context,
                viewModel = viewModel,
                onChallengeItemClick = onChallengeItemClick,
                onThemeMoreClick = onThemeMoreClick,
                onChangeScreen = { screen ->
                    onScreenChange(screen)
                },
                onChallengeLikeClick = { id ->
                    viewModel.reqChallengeLike(id)
                }
            )
//            GeofencingScreen(
//                context,
//                appState.getLocationPermissionList(),
//                appState.getBackgroundLocationPermission()
//            )
        }
        composable(route = Screen.HomeChallenge.route) {
            HomeChallengeScreen(
                homeState = appState,
                viewModel = viewModel,
                onChallengeItemClick = onChallengeItemClick
            )
        }
        composable(route = Screen.HomeMyPage.route) {
            HomeMyPageScreen(
                viewModel = viewModel,
                context = context,
                version = appState.getAppVersion() ?: "",
                onLoginClick = {
                    onScreenChange(Screen.Login)
                },
                onChangeScreen = { screen ->
                    onScreenChange(screen)
                },
                showSnackBar = { snackType, snackText ->
                    appState.showSnackBar(snackType, snackText)
                },
                showWebUrl = { url ->
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url))
                    )
                },
                goSetting = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        data = Uri.parse("package:${context.packageName}")
                    }
                    context.startActivity(intent)
                }
            )
        }
    }
}