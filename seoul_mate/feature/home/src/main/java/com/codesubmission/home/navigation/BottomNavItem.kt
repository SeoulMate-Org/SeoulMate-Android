package com.codesubmission.home.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.codesubmission.home.R
import com.seoulmate.ui.component.Screen


sealed class BottomNavItem(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    data object Suggest : BottomNavItem(
        Screen.HomeSuggestTheme.route,
        R.drawable.ic_home_default,
        R.string.nav_suggest_lable
    )
    data object Favorite : BottomNavItem(
        Screen.HomeFavorite.route,
        R.drawable.ic_home_default,
        R.string.nav_favorite_lable
    )
    data object Map : BottomNavItem(
        Screen.HomeTravelMap.route,
        R.drawable.ic_home_default,
        R.string.nav_map_lable
    )
}

