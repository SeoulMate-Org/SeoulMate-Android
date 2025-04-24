package com.seoulmate.login.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seoulmate.login.LoginViewModel
import com.seoulmate.login.LoginScreen
import com.seoulmate.login.ui.LoginState
import com.seoulmate.login.ui.RequestLoginScreen
import com.seoulmate.ui.component.Screen

@Composable
fun LoginNavHost(
    loginState: LoginState,
    modifier: Modifier,
    isFirst: Boolean?,
    activityContext: Context,
    viewModel: LoginViewModel,
    onSkipClick: () -> Unit,
    onSuccessLogin: () -> Unit,
) {
    NavHost(
        navController = loginState.navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(route = Screen.Login.route) {
            RequestLoginScreen (
                activityContext,
                viewModel,
                isFirst = isFirst ?: false,
                onGoogleLoginClick = { token ->
                    viewModel.getLoginInfo(token, "GOOGLE")
                },
                onFacebookLoginClick = { token ->
                    viewModel.getLoginInfo(token, "FACEBOOK")
                },
                onSkipClick = onSkipClick,
                succeedLogin = onSuccessLogin,
            )
        }
    }
}