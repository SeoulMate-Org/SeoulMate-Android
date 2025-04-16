package com.codesubmission.home.navigation

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.White

@Composable
fun HomeBottomNav(
    modifier: Modifier,
    onHomeClick: () -> Unit = {},
    onMyPageClick: () -> Unit = {},
    onChallengeClick: () -> Unit = {},
    selectedRoute: String,
    @ColorRes defaultColor: Color = CoolGray300,
    @ColorRes selectedColor: Color = Blue500,
) {
    val bottomNavItem = listOf(
        BottomNavItem.Home,
        BottomNavItem.Challenge,
        BottomNavItem.MyPage,
    )
    Surface (
        modifier = modifier,
        shape = RectangleShape,
        color = White,
        shadowElevation = 20.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            bottomNavItem.forEach { item ->
                IconButton(
                    modifier = Modifier
                        .width(77.dp)
                        .height(42.dp),
                    onClick = when(item.route) {
                        BottomNavItem.Home.route -> onHomeClick
                        BottomNavItem.MyPage.route -> onMyPageClick
                        BottomNavItem.Challenge.route -> onChallengeClick
                        else -> { {} }
                    }
                ) {
                    val selected = selectedRoute == item.route
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = if(selected) painterResource(item.selectedIcon) else painterResource(item.defaultIcon),
                            tint = if(selected) selectedColor else defaultColor,
                            contentDescription = "Bottom Navigation SuggestTheme"
                        )
                        Text(
                            text = stringResource(item.label),
                            fontSize = 12.sp,
                            style = TextStyle(
                                color = if(selected) selectedColor else defaultColor
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeBottomNav() {
    HomeBottomNav(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth(),
        selectedRoute = BottomNavItem.Home.route,
    )
}