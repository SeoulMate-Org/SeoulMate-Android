package com.seoulmate.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.seoulmate.data.UserInfo
import com.seoulmate.datastore.repository.Language
import com.seoulmate.ui.main.MainActivity
import com.seoulmate.ui.theme.SeoulMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class SplashActivity : ComponentActivity()  {

    private val viewModel: SplashViewModel by viewModels()

    companion object {
        const val SCREEN_KEY = "SCREEN_KEY"
        const val LOGIN_SCREEN = "LOGIN_SCREEN"
    }

    var isFirst = mutableStateOf(true)

    private val permissionList = listOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    var permissionGranted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionList.forEach {
            val result = ContextCompat.checkSelfPermission(this, it)
            if (result != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                permissionGranted = false
            }
        }

        enableEdgeToEdge()

        Log.d("@@@@", "Splash Locale Language : ${Locale.getDefault().language}")
        val localeLanguageIsKorean = Locale.getDefault().language == Language.KOREAN.code
        var languageCode = ""
        if (localeLanguageIsKorean) {
            languageCode = "ko"
            UserInfo.localeLanguage = "ko"
        } else {
            languageCode = "en"
            UserInfo.localeLanguage = "en"
        }
        changeLanguage(languageCode)

        viewModel.grantedLocationPermission.value = permissionGranted
        viewModel.reqSplashInit()

        setContent {
            SeoulMateTheme {
                SplashScreen(
                    context = this,
                    viewModel = viewModel,
                    isFirst = isFirst.value,
                    moveLogin = { enterLogin() },
                    moveMain = { enterMain() },
                )
            }
        }

        lifecycleScope.launch {
            //
            viewModel.splashInitDataFlow.collect { splashInitData ->
                viewModel.reqInit()
                Log.e("@@@@@", "Splash splashInitDataFlow collect : $splashInitData")
                with(splashInitData) {
                    isFirst.value = isFirstEnter

                    if (language.isNotEmpty()) {
                        Log.e("@@@@@", "Splash languageFlow collect isNotEmpty : ${language}")

                        changeLanguage(language)
                    }

                    userData?.let {
                        if (it.nickName.isNotEmpty()) {
                            Log.e("@@@@@", "Splash userInfoFlow collect isNotEmpty : $userData")
                        }
                    }

                }

            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        val country = if (languageCode == "ko") "KR" else "US"
        val locale = Locale(languageCode, country)
        Locale.setDefault(locale)

        val configuration = this@SplashActivity.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        try {
            this@SplashActivity.resources.updateConfiguration(
                configuration,
                this@SplashActivity.resources.displayMetrics
            )
        } catch (e: Exception) {
            Log.e("@@@@@", "Main languageFlow Exception : ${e.message}")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
            // Call this on the main thread as it may require Activity.restart()
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    /**
     * Move MainActivity - HomeScreen
     */
    private fun enterMain() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }

    /**
     * Move MainActivity - LoginScreen
     */
    private fun enterLogin() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .apply {
                    putExtra(SCREEN_KEY, LOGIN_SCREEN)
                }
        )
        finish()
    }
}