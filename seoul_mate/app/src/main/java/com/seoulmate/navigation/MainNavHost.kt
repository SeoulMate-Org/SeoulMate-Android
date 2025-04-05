package com.seoulmate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.HomeScreen
import com.codesubmission.interest.navigation.interestScreen
import com.seoulmate.SplashScreen
import com.seoulmate.ui.AppState
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.ScreenGraph

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier,
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNext = {
                    appState.navigate(Screen.UserInterestsTheme)
                }
            )
        }
        interestScreen(
            startDestination = Screen.UserInterestsTheme.route,
            route = ScreenGraph.UserInterestsGraph.route,
            onThemeNextClick = {
                appState.navigate(Screen.UserInterestsTogether)
            },
            onTogetherNextClick = {},
            onFinalNextClick = {
                appState.navigate(Screen.Home)
            },
        )
        composable(route = Screen.Home.route) {
            HomeScreen(

            )
        }
        composable(route = Screen.PlaceInfoDetail.route) {

        }
    }
}


