package com.seoulmate.login.ui

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.seoulmate.login.BuildConfig
import com.seoulmate.login.LoginViewModel
import com.seoulmate.login.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.IntroBlue
import com.seoulmate.ui.theme.SplashGradientEnd
import com.seoulmate.ui.theme.SplashGradientStart
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun RequestLoginScreen(
    activityContext: Context,
    viewModel: LoginViewModel,
    isFirst: Boolean = false,
    onGoogleLoginClick: (String) -> Unit,
    onFacebookLoginClick: (String) -> Unit,
    onSkipClick: () -> Unit,
    succeedLogin: () -> Unit,
) {
    val rememberCoroutineScope = rememberCoroutineScope()

    var languageCode = "KOR"

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.GOOGLE_CLOUD_CLIENT_ID)
        .setAutoSelectEnabled(true)
//        .setNonce(<nonce string to use when generating a Google ID token>)
        .build()

    val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
        .Builder(BuildConfig.GOOGLE_CLOUD_CLIENT_ID)
        .build()

    val credentialManager = CredentialManager.create(activityContext)
    val facebookLoginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        facebookLoginManager.createLogInActivityResultContract(callbackManager, null)) {
    }

    LaunchedEffect(Unit) {
        val localeLanguageIsKorean = Locale.getDefault().language == "ko"
        languageCode = if (localeLanguageIsKorean) {
            "KOR"
        } else {
            "US"
        }
    }

    LaunchedEffect(viewModel.isSuccessLogin.value) {
        if (viewModel.isSuccessLogin.value) {
            succeedLogin()
        }
    }

    DisposableEffect(Unit) {
        facebookLoginManager.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {}

            override fun onError(error: FacebookException) {
                Log.d("@@@@@@@", "FacebookException onError : ${error.message}")
            }

            override fun onSuccess(result: LoginResult) {
                val token = result.accessToken.token
                Log.d("@@@@@@@", "onSuccess token : $token")

                onFacebookLoginClick(token)
            }

        })
        onDispose {
            facebookLoginManager.unregisterCallback(callbackManager)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(listOf(SplashGradientStart, SplashGradientEnd)),
                shape = RectangleShape,
                alpha = 1f,
            ),
    ) {
        val (loginColumn, skip, back, titleImg) = createRefs()

        // Title Image
        Image(
            modifier = Modifier
                .width(710.dp)
                .height(659.dp)
                .constrainAs(titleImg) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 75.dp)
                },
            painter = painterResource(R.drawable.img_title),
            contentDescription = "Splash Image",
        )

        if (isFirst) {
            // Skip
            Button(
                modifier = Modifier
                    .constrainAs(skip) {
                        top.linkTo(parent.top, margin = 55.dp)
                        end.linkTo(parent.end, margin = 35.dp)
                    },
                onClick = onSkipClick,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = "SKIP",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TrueWhite
                    )
                )
            }
        } else {
            // Back
            IconButton (
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 55.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    },
                onClick = onSkipClick,
            ) {
                Icon(
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_left),
                    contentDescription = "Back Icon",
                    tint = TrueWhite
                )
            }
        }

        // Login Button (Google, Facebook)
        Column(
            modifier = Modifier
                .constrainAs(loginColumn) {
                    bottom.linkTo(parent.bottom, margin = 75.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Image(
                modifier = Modifier.noRippleClickable {
                    launcher.launch(listOf("email", "public_profile"))
                },
                painter = painterResource(R.drawable.img_facebook_login),
                contentDescription = "Facebook Login"
            )
            Image(
                modifier = Modifier.padding(top = 10.dp)
                    .noRippleClickable {
                    rememberCoroutineScope.launch {
                        val request: GetCredentialRequest = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        runCatching {
                            credentialManager.getCredential(
                                request = request,
                                context = activityContext,
                            )
                        }.onSuccess {
                            val credential = it.credential
                            when(credential) {
                                is CustomCredential -> {
                                    if(credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                                        onGoogleLoginClick(googleIdTokenCredential.idToken)

                                        Log.d("@@@@@@@", "onSuccess idToken : ${googleIdTokenCredential.idToken}")
                                        Log.d("@@@@@@@", "onSuccess displayName : ${googleIdTokenCredential.displayName}")
                                        Log.d("@@@@@@@", "onSuccess id : ${googleIdTokenCredential.id}")
                                    }

                                }
                            }

                        }.onFailure {
                            Log.d("@@@@@", "onFailure : ${it.message}")
                        }
                    }
                },
                painter = painterResource(R.drawable.img_google_login),
                contentDescription = "Google Login"
            )
        }

    }
}