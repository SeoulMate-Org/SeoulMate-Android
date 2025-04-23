package com.seoulmate.login.ui

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.IntroBlue
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun RequestLoginScreen(
    activityContext: Context,
    viewModel: LoginViewModel,
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

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = IntroBlue,
    ) {
        ConstraintLayout {
            val (loginColumn, skip, back) = createRefs()

            Button(
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 55.dp)
                        start.linkTo(parent.start, margin = 35.dp)
                    },
                onClick = onSkipClick,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = "BACK",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TrueWhite
                    )
                )
            }

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

            Column(
                modifier = Modifier
                    .constrainAs(loginColumn) {
                        bottom.linkTo(parent.bottom, margin = 55.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Button(
                    onClick =  {
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
                    }
                ) {
                    Text(text = "Google Login")
                }

                Button(
                    onClick = {
                        launcher.launch(listOf("email", "public_profile"))
                    }
                ) {
                    Text(text = "Facebook Login")
                }

            }

        }

    }
}