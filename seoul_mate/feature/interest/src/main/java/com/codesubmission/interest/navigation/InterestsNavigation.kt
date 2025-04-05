package com.codesubmission.interest.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codesubmission.interest.interests_theme.UserInterestsThemeScreen
import com.codesubmission.interest.interests_together.UserInterestsTogetherScreen
import com.seoulmate.ui.component.Screen

fun NavGraphBuilder.interestScreen(
    startDestination: String,
    route: String,
    onThemeNextClick: () -> Unit,
    onTogetherNextClick: () -> Unit,
    onFinalNextClick: () -> Unit,
) {
    navigation(
        startDestination = startDestination,
        route = route,
    ) {
        composable(route = Screen.UserInterestsTheme.route) {
            UserInterestsThemeScreen(
                onNext = onThemeNextClick,
            )
        }
        composable(route = Screen.UserInterestsTogether.route) {
            UserInterestsTogetherScreen(
                onNext = onFinalNextClick,
            )
        }
        composable(route = Screen.SuccessUserInterests.route) {

        }
    }
}