package com.seoulmate.navigation

import android.app.Activity
import android.content.Intent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.HomeScreen
import com.codesubmission.settings.language.SettingLanguageScreen
import com.codesubmission.interest.navigation.interestScreen
import com.codesubmission.settings.badge.SettingMyBadgeScreen
import com.codesubmission.settings.nickname.SettingUserNicknameScreen
import com.codesubmission.settings.notification.SettingNotificationScreen
import com.seoulmate.challenge.attraction.AttractionDetailScreen
import com.seoulmate.challenge.detail.ChallengeDetailScreen
import com.seoulmate.challenge.comment.ChallengeCommentListScreen
import com.seoulmate.challenge.rank.ChallengeRankListScreen
import com.seoulmate.challenge.theme.ChallengeThemeListScreen
import com.seoulmate.data.model.PlaceInfoData
import com.seoulmate.login.LoginScreen
import com.seoulmate.places.ui.PlaceInfoDetailScreen
import com.seoulmate.ui.AppState
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.ScreenGraph
import com.seoulmate.ui.splash.SplashActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    appState: AppState,
    startDestination: String = Screen.Home.route,
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                appState.getContext,
                onSkipClick = {
                    appState.navController.popBackStack()
                },
                onSuccessLogin = {
                    appState.navController.popBackStack()
                }
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
                context = appState.getContext,
                onPlaceInfoClick = {
                    appState.navigate(Screen.PlaceInfoDetail)
                },
                onChangeScreen = { screen ->
                    appState.navigate(screen)
                },
                onChallengeItemClick = { challengeId ->
                    appState.selectedChallengeItemId.intValue = challengeId
                    appState.navigate(Screen.ChallengeDetail)
                },
                firstShowLogin = {
                    appState.firstShowLogin()
                },
                onThemeMorClick = {
                    appState.navigate(Screen.ChallengeThemeList)
                }
            )
        }
        composable(route = Screen.PlaceInfoDetail.route) {
            PlaceInfoDetailScreen(
                bottomSheetScaffoldState = appState.bottomSheetScaffoldState,
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.ChallengeCommentList.route) {
            ChallengeCommentListScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.ChallengeRankList.route) {
            ChallengeRankListScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.ChallengeDetail.route) {
            ChallengeDetailScreen(
                context = appState.getContext,
                challengeId = appState.selectedChallengeItemId.intValue,
                onBackClick = { appState.navController.popBackStack() },
                onChangeScreen = { screen ->
                    appState.navigate(screen)
                },
                onAttractionClick = { attractionId ->
                    appState.selectedAttractionItemId.intValue = attractionId
                    appState.navigate(Screen.AttractionDetail)
                }
            )
        }
        composable(route = Screen.ChallengeThemeList.route) {
            ChallengeThemeListScreen(
                coroutineScope = appState.coroutineScope,
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.SettingLanguage.route) {
            SettingLanguageScreen(
                onBackClick = { appState.navController.popBackStack() },
                onCompleteClick = { languageCode ->
                    appState.selectedLanguage(languageCode)
                    appState.getContext.startActivity(
                        Intent(appState.getContext, SplashActivity::class.java)
                    )
                    (appState.getContext as Activity).finish()
                }
            )
        }
        composable(route = Screen.SettingNotification.route) {
            SettingNotificationScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.SettingMyBadge.route) {
            SettingMyBadgeScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.SettingUserNickname.route) {
            SettingUserNicknameScreen(
                onBackClick = { appState.navController.popBackStack() },
            )
        }
        composable(route = Screen.AttractionDetail.route) {
            AttractionDetailScreen(
                attractionId = appState.selectedAttractionItemId.intValue,
                onBackClick = { appState.navController.popBackStack() },
            )
        }
    }
}



