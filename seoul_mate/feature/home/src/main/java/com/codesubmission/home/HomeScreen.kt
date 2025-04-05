package com.codesubmission.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.navigation.BottomNavItem
import com.codesubmission.home.navigation.HomeNavHost
import com.codesubmission.home.ui.map.BottomSheetContent
import com.codesubmission.home.ui.rememberHomeState
import com.seoulmate.ui.component.HomeNavigationSuiteScaffold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(

) {
    val homeState = rememberHomeState()
    val homeBottomNavItem = listOf(
        BottomNavItem.Suggest,
        BottomNavItem.Favorite,
        BottomNavItem.Map,
    )

    val currentDestination = homeState.currentDestination

    HomeNavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteItems = {
            homeBottomNavItem.forEach { navItem ->
                val selected = true
                item(
                    onClick = { homeState.navigate(navItem.route) },
                    icon = {
                        Icon(
                            modifier = Modifier.width(25.dp).height(25.dp),
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.route,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            modifier = Modifier.width(25.dp).height(25.dp),
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.route,
                        )
                    },
                    label = {
                        Text(text = stringResource(navItem.label), fontSize = 10.sp)
                    },
                    selected = selected,
                )
            }
        }
    ) {
        BottomSheetScaffold(
            containerColor = Color.Transparent,
            scaffoldState = homeState.bottomSheetScaffoldState,
            sheetContent = {
                BottomSheetContent()
            },
            sheetPeekHeight = 0.dp,
        ) {
            Box(
                modifier = Modifier.padding(it)
            ) {
                HomeNavHost(
                    appState = homeState,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }

}

//private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
//    this?.hierarchy?.any {
//        it.hasRoute(route)
//    } ?: false
