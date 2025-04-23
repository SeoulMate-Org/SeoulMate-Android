package com.seoulmate.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.seoulmate.data.UserInfo
import com.seoulmate.navigation.MainNavHost
import com.seoulmate.ui.AppState
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.rememberAppState
import com.seoulmate.ui.theme.SeoulMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.reqMainInit()
        }

        setContent {
            SeoulMateTheme {
                // A surface container using the 'background' color from the theme
                MainNavHost(
                    appState = rememberAppState(
                        context = this,
                        viewModel = viewModel,
                        startLogin = !intent.getStringExtra("SCREEN_KEY").isNullOrEmpty()
                    ),
                )
            }
        }

        // Update User Language
        lifecycleScope.launch {
            viewModel.languageFlow.collect { language ->
                Log.e("@@@@@", "Main languageFlow collect : $language")

                val country = if (language == "ko") "KR" else "US"
                val locale = Locale(language, country)
                Locale.setDefault(locale)

                val configuration = this@MainActivity.resources.configuration
                configuration.setLocale(locale)
                configuration.setLayoutDirection(locale)

                try {
                    this@MainActivity.resources.updateConfiguration(
                        configuration,
                        this@MainActivity.resources.displayMetrics
                    )
                } catch (e: Exception) {
                    Log.e("@@@@@", "Main languageFlow Exception : ${e.message}")
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language)
                    // Call this on the main thread as it may require Activity.restart()
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }

            }
        }
    }

}


