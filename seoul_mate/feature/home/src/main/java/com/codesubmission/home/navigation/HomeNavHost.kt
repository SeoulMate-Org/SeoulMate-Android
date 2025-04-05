package com.codesubmission.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.favorite.HomeFavoriteScreen
import com.codesubmission.home.ui.map.HomeTravelMapScreen
import com.codesubmission.home.ui.suggest.HomeSuggestThemeScreen
import com.seoulmate.ui.component.Screen

@Composable
fun HomeNavHost(
    appState: HomeState,
    modifier: Modifier,
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.HomeSuggestTheme.route,
        modifier = modifier
    ) {
        composable(route = Screen.HomeSuggestTheme.route) {
            HomeSuggestThemeScreen()
        }
        composable(route = Screen.HomeFavorite.route) {
            HomeFavoriteScreen()
        }
        composable(route = Screen.HomeTravelMap.route) {
            HomeTravelMapScreen(
                expandBottomSheet = { appState.expandBottomSheet() }
            )
        }
    }
}