package com.seoulmate.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.HomeScreen
import com.codesubmission.interest.navigation.interestScreen
import com.seoulmate.data.model.PlaceInfoData
import com.seoulmate.login.LoginScreen
import com.seoulmate.places.ui.PlaceInfoDetailScreen
import com.seoulmate.ui.AppState
import com.seoulmate.ui.SplashScreen
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.ScreenGraph

@Composable
fun MainNavHost(
    appState: AppState,
    modifier: Modifier,
    activityContext: Context,
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
                },
                onClickLogin = {
                    appState.navigate(Screen.Login)
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                activityContext
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
                onPlaceInfoClick = {
                    appState.navigate(Screen.PlaceInfoDetail)
                }
            )
        }
        composable(route = Screen.PlaceInfoDetail.route) {
            PlaceInfoDetailScreen(
                placeData = PlaceInfoData(
                    title = "테스트 장소",
                    lat = 0,
                    lng = 0,
                    placeName = "장소 이름",
                    placeInfo = "장소 정보",
                    placeImg = "장소 이미지",
                    placeTel = "010-0000-0000",
                    placeAddress = "장소 주소"
                ),
                onBackClick = { appState.navController.popBackStack() },
            )
        }
    }
}


