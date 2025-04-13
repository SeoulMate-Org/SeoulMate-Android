package com.seoulmate.ui.component

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.POST_NOTIFICATIONS]
)
@Composable
fun RequestPermission(
    modifier: Modifier,
    permissionList: List<String>,
    permissionSettingConfirmText: String,
    granted: () -> Unit,
    dismiss: () -> Unit,
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = permissionList,
    )

    LaunchedEffect(Unit) {
        if (!permissionsState.allPermissionsGranted) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(permissionsState.allPermissionsGranted) {
        if(permissionsState.allPermissionsGranted) {
            granted.invoke()
        }
    }

    if(permissionsState.shouldShowRationale) { // 권한 설정 페이지 이동 팝업
//        PermissionSettingConfirmDialog(
//            text = permissionSettingConfirmText,
//            dismiss = dismiss
//        )
    }

    Surface(
        modifier = modifier,
        color = Color.Black.copy(alpha = 0.8f)
    ) {

    }
}