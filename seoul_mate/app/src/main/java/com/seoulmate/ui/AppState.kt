package com.seoulmate.ui

import android.content.Context
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
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
        bottomSheetScaffoldState = bottomSheetState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class AppState (
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    private val context: Context,
    private val viewModel: MainViewModel,
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    startLogin: Boolean = false,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val selectedChallengeItemId = mutableIntStateOf(0)
    val selectedAttractionItemId = mutableIntStateOf(0)
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

    fun expandBottomSheet() {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

}