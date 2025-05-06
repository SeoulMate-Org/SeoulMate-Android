package com.seoulmate.login.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.seoulmate.data.UserInfo
import com.seoulmate.login.BuildConfig
import com.seoulmate.login.LoginViewModel
import com.seoulmate.login.R
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.SplashGradientEnd
import com.seoulmate.ui.theme.SplashGradientStart
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch

@Composable
fun RequestLoginScreen(
    activityContext: Context,
    viewModel: LoginViewModel,
    onGoogleLoginClick: (String) -> Unit,
    onFacebookLoginClick: (String) -> Unit,
    onSkipClick: () -> Unit,
    succeedLogin: () -> Unit,
    succeedSignup: () -> Unit,
) {
    val rememberCoroutineScope = rememberCoroutineScope()

    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.GOOGLE_CLOUD_CLIENT_ID)
        .setAutoSelectEnabled(true)
//        .setNonce(<nonce string to use when generating a Google ID token>)
        .build()

    val credentialManager = CredentialManager.create(activityContext)
    val facebookLoginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        facebookLoginManager.createLogInActivityResultContract(callbackManager, null)) {
    }

    val locationPermission = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocationPermissionGranted = remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                activityContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    LaunchedEffect(viewModel.isSuccessLogin.value) {
        if(viewModel.isSuccessLogin.value) {
            viewModel.reqHomeChallengeItems()
        }
    }

    LaunchedEffect(viewModel.finishedFetchHomeItems.value) {
        if(viewModel.finishedFetchHomeItems.value) {
            viewModel.getMyData(fineLocationPermissionGranted.value)
        }
    }

    LaunchedEffect(viewModel.finishedFetchMyData.value) {
        if (viewModel.isNewUser.value) {
            succeedSignup()
        } else if (viewModel.finishedFetchMyData.value == true) {
            succeedLogin()
        }
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if(viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.reqHomeChallengeItems()
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
        val (loginColumn, skip, back, titleImg, loading) = createRefs()

        // Title Image
        Image(
            modifier = Modifier
                .width(710.dp)
                .height(659.dp)
                .constrainAs(titleImg) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 25.dp)
                },
            painter = painterResource(R.drawable.img_title_cut),
            contentDescription = "Splash Image",
        )

        if (viewModel.isFirstEnter.value != null && viewModel.isFirstEnter.value == true) {
            // Skip
            Button(
                modifier = Modifier
                    .constrainAs(skip) {
                        top.linkTo(parent.top, margin = 35.dp)
                        end.linkTo(parent.end, margin = 15.dp)
                    },
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                ),
                onClick = onSkipClick,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.str_skip),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TrueWhite
                    )
                )
            }
        } else if(viewModel.isFirstEnter.value != null && viewModel.isFirstEnter.value == false) {
            // Back
            IconButton (
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 35.dp)
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
//            Image(
//                modifier = Modifier.noRippleClickable {
//                    launcher.launch(listOf("email", "public_profile"))
//                },
//                painter = painterResource(
//                    if (UserInfo.localeLanguage == "ko") {
//                        R.drawable.img_facebook_login
//                    } else {
//                        R.drawable.img_facebook_login_eng
//                    }
//                ),
//                contentDescription = "Facebook Login"
//            )
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
                painter = painterResource(
                    if (UserInfo.localeLanguage == "ko") {
                        R.drawable.img_google_login
                    } else {
                        R.drawable.img_google_login_eng
                    }
                ),
                contentDescription = "Google Login"
            )
        }

        if (viewModel.isShowLoading.value == true) {
            PpsLoading(
                modifier = Modifier.wrapContentSize()
                    .constrainAs(loading) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            )
        }

    }
}