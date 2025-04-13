package com.seoulmate.login.ui

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch

@Composable
fun RequestLoginScreen(
    activityContext: Context,
    onGoogleLoginClick: (String) -> Unit,
    onFacebookLoginClick: (String) -> Unit,
) {
    val rememberCoroutineScope = rememberCoroutineScope()

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

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
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