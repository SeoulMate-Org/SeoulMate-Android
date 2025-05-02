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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codesubmission.home.ui.mypage.item.MyPageActiveLog
import com.codesubmission.home.ui.mypage.item.MyPageLoginInfo
import com.codesubmission.home.ui.mypage.item.MyPagePermission
import com.codesubmission.home.ui.mypage.item.MyPageServiceInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBarType
import com.seoulmate.ui.theme.CoolGray25

@Composable
fun HomeMyPageScreen(
    showSnackBar: (SnackBarType, String) -> Unit,
    onLoginClick: () -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
    onNickNameClick: () -> Unit = {},
) {
    val rememberNickName = remember { mutableStateOf(UserInfo.nickName) }

    LaunchedEffect(UserInfo.nickName) {
        rememberNickName.value = UserInfo.nickName
    }

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
                    isLogin = rememberNickName.value.isNotEmpty(),
                    nickname = rememberNickName.value,
                    loginType = UserInfo.loginType,
                    onLoginClick = onLoginClick,
                    onNickNameClick = {
                        onChangeScreen(Screen.SettingUserNickname)
                    }
                )
            }
            // Active Log
            item {
                Box(modifier = Modifier
                    .padding(top = 40.dp),
                ) {
                    MyPageActiveLog(
                        cntFavorite = UserInfo.myLikeChallengeList.size,
                        cntComment = UserInfo.myCommentList.size,
                        onBadgeClick = {
                            onChangeScreen(Screen.SettingMyBadge)
                        }
                    )
                }
            }
            // Permission
            item {
                Box(modifier = Modifier
                    .padding(top = 30.dp),
                ) {
                    MyPagePermission(
                        onLanguageClick = {
                            onChangeScreen(Screen.SettingLanguage)
                        },
                        onNotificationClick = {
                            onChangeScreen(Screen.SettingNotification)
                        },
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