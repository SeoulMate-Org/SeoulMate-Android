package com.codesubmission.home.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.location.geofencing.GeofencingScreen
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.challenge.HomeChallengeScreen
import com.codesubmission.home.ui.main.HomeMainScreen
import com.codesubmission.home.ui.mypage.HomeMyPageScreen
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.Screen

@Composable
fun HomeNavHost(
    appState: HomeState,
    modifier: Modifier,
    context: Context,
    onScreenChange: (screen: Screen) -> Unit = {_ -> },
    onChallengeItemClick: (item: ChallengeItemData) -> Unit = {},
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.HomeMain.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeMain.route) {
            HomeMainScreen(
                onChallengeItemClick = onChallengeItemClick
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
                onReplyClick = {
                    onScreenChange(Screen.ChallengeReplyList)
                },
                onChallengeItemClick = onChallengeItemClick
            )
        }
        composable(route = Screen.HomeMyPage.route) {
            HomeMyPageScreen(
                onLoginClick = {
                    onScreenChange(Screen.Login)
                },
                onChangeScreen = { screen ->
                    onScreenChange(screen)
                },
                showSnackBar = { snackType, snackText ->
                    appState.showSnackBar(snackType, snackText)
                }
            )
        }
    }
}