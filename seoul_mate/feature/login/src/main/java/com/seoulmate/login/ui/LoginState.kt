package com.seoulmate.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberLoginState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): LoginState = remember(
    navController,
    coroutineScope,
) {
    LoginState(
        navController = navController,
        coroutineScope = coroutineScope,
    )
}

@Stable
class LoginState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {

    fun authenticateNaverLogin() {

    }
}