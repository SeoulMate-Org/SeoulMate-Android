package com.codesubmission.home.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.codesubmission.home.R
import com.seoulmate.ui.component.Screen


sealed class BottomNavItem(
    val route: String,
    @DrawableRes val defaultIcon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val label: Int,
) {
    data object Suggest : BottomNavItem(
        Screen.HomeSuggestTheme.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_suggest_theme,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_suggest_theme,
        R.string.nav_suggest_lable
    )
    data object Favorite : BottomNavItem(
        Screen.HomeFavorite.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_favorite,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite,
        R.string.nav_favorite_lable
    )
    data object Map : BottomNavItem(
        Screen.HomeTravelMap.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_map,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_map,
        R.string.nav_map_lable
    )
}

