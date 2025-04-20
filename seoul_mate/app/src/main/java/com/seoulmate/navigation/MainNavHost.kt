package com.seoulmate.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.HomeScreen
import com.codesubmission.settings.language.SettingLanguageScreen
import com.codesubmission.interest.navigation.interestScreen
import com.codesubmission.settings.notification.SettingNotificationScreen
import com.seoulmate.challenge.detail.ChallengeDetailScreen
import com.seoulmate.challenge.reply.ChallengeReplyListScreen
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
    activityContext: Context,
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNext = {
                    appState.navigate(Screen.Home)
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
        // TODO chan need remove
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
                context = activityContext,
                onPlaceInfoClick = {
                    appState.navigate(Screen.PlaceInfoDetail)
                },
                onChangeScreen = { screen ->
                    appState.navigate(screen)
                },
                onChallengeItemClick = { item ->
                    appState.selectedChallengeItem.value = item
                    appState.navigate(Screen.ChallengeDetail)
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
        composable(route = Screen.ChallengeReplyList.route) {
            ChallengeReplyListScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.ChallengeDetail.route) {
            ChallengeDetailScreen(
                item = appState.selectedChallengeItem.value,
                onBackClick = { appState.navController.popBackStack() }
            )
        }
        composable(route = Screen.SettingLanguage.route) {
            SettingLanguageScreen(
                onBackClick = { appState.navController.popBackStack() },
                onCompleteClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.SettingNotification.route) {
            SettingNotificationScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
    }
}


