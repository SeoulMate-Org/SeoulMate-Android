package com.codesubmission.home.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.R
import com.codesubmission.home.ui.mypage.item.MyPageActiveLog
import com.codesubmission.home.ui.mypage.item.MyPageLoginInfo
import com.codesubmission.home.ui.mypage.item.MyPagePermission
import com.codesubmission.home.ui.mypage.item.MyPageServiceInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBarType
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.White

@Composable
fun HomeMyPageScreen(
    viewModel: HomeViewModel,
    version: String,
    showSnackBar: (SnackBarType, String) -> Unit,
    onLoginClick: () -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
    onNickNameClick: () -> Unit = {},
) {

    LaunchedEffect(Unit) {
        viewModel.reqMyPageUserInfo()
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
                    isLogin = UserInfo.isUserLogin(),
                    nickname = UserInfo.nickName,
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
                        cntFavorite = UserInfo.myPageInfo?.likeCount ?: 0,
                        cntComment = UserInfo.myPageInfo?.commentCount ?: 0,
                        cntBadge = UserInfo.myPageInfo?.badgeCount ?: 0,
                        onBadgeClick = {
                            onChangeScreen(Screen.SettingMyBadge)
                        }
                    )
                }
            }
            // Permission
            item {
                Box(modifier = Modifier
                    .padding(top = 16.dp),
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
            // Service/Terms Info
            item {
                Box(modifier = Modifier
                    .padding(top = 16.dp),
                ) {
                    MyPageServiceInfo()
                }
            }
            // Version Info
            item {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(horizontal = 20.dp, vertical = 10.dp,),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        PpsText(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.my_page_version_info),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = CoolGray900,
                            )
                        )
                        PpsText(
                            modifier = Modifier.weight(1f),
                            text = version,
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CoolGray600,
                            ),
                        )
                    }
                }
            }
            // Logout
            item {
                if (UserInfo.isUserLogin()) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(
                                color = White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        // Logout
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp,)
                                .noRippleClickable {
                                    viewModel.reqLogout()
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            PpsText(
                                modifier = Modifier.weight(1f).height(40.dp),
                                text = stringResource(R.string.my_page_logout),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CoolGray900,
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp,),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            PpsText(
                                modifier = Modifier.weight(1f).height(40.dp),
                                text = stringResource(R.string.my_page_sing_out),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CoolGray900,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}