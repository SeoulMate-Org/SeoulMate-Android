package com.codesubmission.home.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codesubmission.home.navigation.BottomNavItem
import com.codesubmission.home.ui.map.MapTopTagData
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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

    val navigationSuiteItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Challenge,
        BottomNavItem.MyPage,
    )

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

    fun getLocationPermissionList(): List<String> {
        val locationPermissionList = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        // For Android 10 onwards, we need background permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val permissionRecognition = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                Manifest.permission.ACTIVITY_RECOGNITION
//            } else {
//                "com.google.android.gms.permission.ACTIVITY_RECOGNITION"
//            }
//            locationPermissionList.add(permissionRecognition)
//        }

        return locationPermissionList.toList()
    }

    fun getBackgroundLocationPermission(): String? = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    } else {
        null
    }

    fun getAllPermissionList(): List<String> {
        val permissionList = mutableListOf<String>()

//        getBackgroundLocationPermission()?.let {
//            permissionList.add(it)
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        permissionList.addAll(getLocationPermissionList().toMutableList())

        return permissionList.toList()
    }

    fun createNotificationChannel(context: Context) {
        val name = "NAME"
        val descriptionText = "DESCRIPTION"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}