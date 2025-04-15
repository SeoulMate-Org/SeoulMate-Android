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
    data object Home : BottomNavItem(
        Screen.HomeMain.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_favorite,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite,
        R.string.nav_home_label
    )
    data object Challenge : BottomNavItem(
        Screen.HomeChallenge.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_suggest_theme,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_suggest_theme,
        R.string.nav_challenge_label
    )
    data object MyPage : BottomNavItem(
        Screen.HomeMyPage.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_map,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_map,
        R.string.nav_my_page_label
    )

}

