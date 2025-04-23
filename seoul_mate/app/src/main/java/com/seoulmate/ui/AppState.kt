package com.seoulmate.ui

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import java.util.Locale

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    context: Context,
    startLogin: Boolean = false,
    viewModel: MainViewModel,
): AppState = remember(
    navController,
    coroutineScope,
) {
    AppState(
        navController = navController,
        coroutineScope = coroutineScope,
        context = context,
        viewModel = viewModel,
        startLogin = startLogin,
    )
}

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    private val context: Context,
    private val viewModel: MainViewModel,
    startLogin: Boolean = false,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val selectedChallengeItem = mutableStateOf<ChallengeItemData?>(null)
    private var isShowLoading = mutableStateOf(false)
    private var isShowServiceLoading = viewModel.isShowLoading.value
    private var userData = mutableStateOf(viewModel.userData.value)

    private var firstShowLogin = mutableStateOf(startLogin)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val getShowLoadingFlag: Boolean = isShowLoading.value || isShowServiceLoading

    val getContext: Context get() = this.context

    fun navigate(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun firstShowLogin() {
        if (firstShowLogin.value) {
            firstShowLogin.value = false
            navController.navigate(Screen.Login.route)
        }
    }

    fun selectedLanguage(languageCode: String) {
        viewModel.updateLanguage(languageCode)
    }

    fun refreshUserToken() {
        viewModel.refreshUserToken()
    }

}