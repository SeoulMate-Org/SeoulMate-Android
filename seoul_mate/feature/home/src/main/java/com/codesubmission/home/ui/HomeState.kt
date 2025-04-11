package com.codesubmission.home.ui

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codesubmission.home.ui.map.MapBottomSheetContent
import com.codesubmission.home.ui.map.MapBottomSheetType
import com.codesubmission.home.ui.map.MapItemListBottomSheet
import com.codesubmission.home.ui.map.MapTopTagData
import com.seoulmate.data.model.MapListCardItemData
import com.seoulmate.ui.component.SnackBarType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun rememberHomeState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    bottomSheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    snackState: SnackbarHostState = remember { SnackbarHostState() },
): HomeState = remember(
    navController,
    coroutineScope,
) {
    HomeState(
        navController = navController,
        coroutineScope = coroutineScope,
        snackBarkHostState = snackState,
        bottomSheetScaffoldState = bottomSheetState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class HomeState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val bottomSheetScaffoldState: BottomSheetScaffoldState,
    val snackBarkHostState: SnackbarHostState = SnackbarHostState(),
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

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

    val mapDetailState = mutableStateOf<MapTopTagData?>(null)

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun expandBottomSheet() {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    fun showSnackBar(
        type: SnackBarType = SnackBarType.OnlyText,
        message: String,
    ) {
        val snackBarMessage = "${type.name}:::$message"
        coroutineScope.launch {
            snackBarkHostState.showSnackbar(snackBarMessage)
        }
    }

    fun onChangeMapDetailState(tagItem: MapTopTagData?) {
        mapDetailState.value = tagItem
    }

}