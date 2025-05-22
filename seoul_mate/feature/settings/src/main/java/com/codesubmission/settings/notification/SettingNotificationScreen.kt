package com.codesubmission.settings.notification

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.codesubmission.settings.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue100
import com.seoulmate.ui.theme.Blue300
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SettingNotificationScreen(
    context: Context,
    onBackClick: () -> Unit = {},
) {
    val notificationPermissionState: PermissionState? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            null
        }

    var isShowNotificationPermission by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        notificationPermissionState?.let {
            isShowNotificationPermission = !it.status.isGranted
        }
    }

    LaunchedEffect(notificationPermissionState?.status?.isGranted) {
        notificationPermissionState?.let {
            isShowNotificationPermission = !it.status.isGranted
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.notification_title),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Search Icon",
                            tint = CoolGray900,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrueWhite,
                    titleContentColor = TrueWhite,
                    navigationIconContentColor = TrueWhite,
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            // Notification Options
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(color = White)
                    .padding(20.dp)
            ) {
                if (isShowNotificationPermission) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .noRippleClickable {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    data = "package:${context.packageName}".toUri()
                                }
                                context.startActivity(intent)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            PpsText(
                                modifier = Modifier,
                                text = "testtesttest",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = CoolGray900,
                                )
                            )
                            PpsText(
                                modifier = Modifier,
                                text = "testtesttest123123123",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = CoolGray600,
                                )
                            )
                        }
                        PpsText(
                            modifier = Modifier,
                            text = "설정하러가기",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Blue500,
                            )
                        )
                    }
                }
            }

            // Button
            Box(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                PpsButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),
                    stringRes = com.seoulmate.ui.R.string.str_complete,
                    cornerRound = 12.dp,
                ) {

                }
            }
        }
    }
}