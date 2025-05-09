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
import com.seoulmate.login.ui.SignupSucceedScreen
import com.seoulmate.ui.component.Screen

@Composable
fun LoginNavHost(
    loginState: LoginState,
    modifier: Modifier,
    activityContext: Context,
    viewModel: LoginViewModel,
    onSkipClick: () -> Unit,
    onSuccessLogin: () -> Unit,
) {
    NavHost(
        navController = loginState.navController,
        startDestination = Screen.Signin.route,
        modifier = modifier
    ) {
        composable(route = Screen.Signin.route) {
            RequestLoginScreen (
                activityContext,
                viewModel,
                onGoogleLoginClick = { token ->
                    viewModel.getLoginInfo(token, "GOOGLE")
                },
                onFacebookLoginClick = { token ->
                    viewModel.getLoginInfo(token, "FACEBOOK")
                },
                onSkipClick = onSkipClick,
                succeedLogin = onSuccessLogin,
                succeedSignup = {
                    loginState.navController.navigate(Screen.Signup.route)
                }
            )
        }
        composable(route = Screen.Signup.route) {
            SignupSucceedScreen(
                succeedSignup = onSuccessLogin,
            )
        }
    }
}