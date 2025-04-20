package com.codesubmission.home.ui.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codesubmission.home.ui.mypage.item.MyPageActiveLog
import com.codesubmission.home.ui.mypage.item.MyPageLoginInfo
import com.codesubmission.home.ui.mypage.item.MyPagePermission
import com.codesubmission.home.ui.mypage.item.MyPageServiceInfo
import com.seoulmate.ui.component.SnackBarType
import com.seoulmate.ui.theme.CoolGray25

@Composable
fun HomeMyPageScreen(
    showSnackBar: (SnackBarType, String) -> Unit,
    onLoginClick: () -> Unit = {},
    onSettingLanguageClick: () -> Unit = {},
    onSettingNotificationClick: () -> Unit = {},
    onNickNameClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = CoolGray25
    ) {

        LazyColumn(
            modifier = Modifier.padding(15.dp),
        ) {
            // Login Info
            item {
                MyPageLoginInfo(
                    onLoginClick = onLoginClick
                )
            }
            // Active Log
            item {
                Box(modifier = Modifier
                    .padding(top = 40.dp),
                ) {
                    MyPageActiveLog()
                }
            }
            // Permission
            item {
                Box(modifier = Modifier
                    .padding(top = 30.dp),
                ) {
                    MyPagePermission(
                        onLanguageClick = onSettingLanguageClick,
                        onNotificationClick = onSettingNotificationClick,
                    )
                }
            }
            // Service Info
            item {
                Box(modifier = Modifier
                    .padding(top = 30.dp),
                ) {
                    MyPageServiceInfo()
                }
            }
        }
    }
}