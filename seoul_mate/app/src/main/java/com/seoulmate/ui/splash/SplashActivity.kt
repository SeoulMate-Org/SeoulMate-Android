package com.seoulmate.ui.splash

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.seoulmate.datastore.repository.Language
import com.seoulmate.datastore.repository.dataStore
import com.seoulmate.ui.theme.SeoulMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class SplashActivity : ComponentActivity()  {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.reqSplashInit()

        Log.d("@@@@", "Splash Locale Language : ${Locale.getDefault().language}")
        val localeLanguageIsKorean = Locale.getDefault().language == Language.KOREAN.code
        val languageCode = if (localeLanguageIsKorean) {
            "ko"
        } else {
            "en"
        }
        changeLanguage(languageCode)

        setContent {
            SeoulMateTheme {
                SplashScreen(
                    context = this,
                    viewModel = viewModel,
                )
            }
        }

        lifecycleScope.launch {
            viewModel.languageFlow.collect { language ->
                Log.e("@@@@@", "Splash languageFlow collect : $language")
                if (language.isNotEmpty()) {
                    Log.e("@@@@@", "Splash languageFlow collect isNotEmpty : $language")

                    changeLanguage(language)
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
}