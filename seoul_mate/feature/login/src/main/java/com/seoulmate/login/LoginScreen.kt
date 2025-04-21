package com.seoulmate.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.seoulmate.login.navigation.LoginNavHost
import com.seoulmate.login.ui.rememberLoginState
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.IntroBlue
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    activityContext: Context,
) {
    
    val viewModel = hiltViewModel<LoginViewModel>()

    LaunchedEffect(Unit) {
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Blue500,
    ) {
        LoginNavHost(
            loginState = rememberLoginState(),
            modifier = Modifier.fillMaxSize(),
            activityContext = activityContext,
            viewModel = viewModel,
        )
    }

}

