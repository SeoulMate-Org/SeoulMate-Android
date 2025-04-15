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
        com.seoulmate.ui.R.drawable.ic_bottom_nav_home,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_home,
        R.string.nav_home_label
    )
    data object Challenge : BottomNavItem(
        Screen.HomeChallenge.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_challenge,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_challenge,
        R.string.nav_challenge_label
    )
    data object MyPage : BottomNavItem(
        Screen.HomeMyPage.route,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_my,
        com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_my,
        R.string.nav_my_page_label
    )

}

