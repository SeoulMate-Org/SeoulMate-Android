package com.codesubmission.home.ui.mypage

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.R
import com.codesubmission.home.ui.HomeState
import com.codesubmission.home.ui.mypage.item.LogoutDialog
import com.codesubmission.home.ui.mypage.item.MyPageActiveLog
import com.codesubmission.home.ui.mypage.item.MyPageLoginInfo
import com.codesubmission.home.ui.mypage.item.MyPagePermission
import com.codesubmission.home.ui.mypage.item.MyPageServiceInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsAlertDialog
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
    homeState: HomeState,
    context: Context,
    version: String,
    goMainHome: MutableState<Boolean>,
    showSnackBar: (SnackBarType, String) -> Unit,
    onLoginClick: () -> Unit = {},
    finishedLogout: () -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
    onNickNameClick: () -> Unit = {},
    showWebUrl: (url: String) -> Unit = {},
    goSetting: () -> Unit = {},
) {
//    val reviewManager = ReviewManagerFactory.create(context)
    val reviewManager = FakeReviewManager(context)
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showLoginAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.reqMyPageUserInfo()
    }

    LaunchedEffect(viewModel.finishedLogout.value) {
        if (viewModel.finishedLogout.value) {
            viewModel.finishedLogout.value = false
            finishedLogout()
        }
    }

    LaunchedEffect(goMainHome.value) {
        if (goMainHome.value) {
            goMainHome.value = false
            homeState.navigate(Screen.HomeMain.route)
        }
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
                        cntFavorite = viewModel.myPageInfo.value?.likeCount ?: 0,
                        cntComment = viewModel.myPageInfo.value?.commentCount ?: 0,
                        cntBadge = viewModel.myPageInfo.value?.badgeCount ?: 0,
                        onBadgeClick = {
                            if (UserInfo.isUserLogin()) {
                                onChangeScreen(Screen.SettingMyBadge)
                            } else {
                                showLoginAlertDialog = true
                            }
                        },
                        onFavoriteClick = {
                            if (UserInfo.isUserLogin()) {
                                onChangeScreen(Screen.MyAttraction)
                            } else {
                                showLoginAlertDialog = true
                            }
                        },
                        onCommentClick = {
                            if (UserInfo.isUserLogin()) {
                                onChangeScreen(Screen.MyComment)
                            } else {
                                showLoginAlertDialog = true
                            }
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
//                            onChangeScreen(Screen.SettingNotification)
                            goSetting()
                        },
                        onLocationClick = {
                            goSetting()
                        }
                    )
                }
            }
            // Service/Terms Info
            item {
                Box(modifier = Modifier
                    .padding(top = 16.dp),
                ) {
                    MyPageServiceInfo(
                        showWebUrl = showWebUrl,
                        showOnBoarding = {
                            onChangeScreen(Screen.MyPageOnBoarding)
                        },
                    )
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
                            .height(60.dp)
                            .padding(horizontal = 20.dp, vertical = 10.dp,)
                            .noRippleClickable {
                                reviewManager.requestReviewFlow()
                            },
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
                            text = "v$version",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CoolGray600,
                            ),
                            textAlign = TextAlign.End
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
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp,)
                                .noRippleClickable {
                                    showLogoutDialog = true
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            PpsText(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.my_page_logout),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CoolGray900,
                                )
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp,)
                                .noRippleClickable {
                                    onChangeScreen(Screen.Withdraw)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            PpsText(
                                modifier = Modifier.weight(1f).height(40.dp),
                                text = stringResource(com.seoulmate.ui.R.string.withdraw_title),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = CoolGray900,
                                )
                            )
                        }
                    }
                }
            }
        }

        if(showLogoutDialog) {
            LogoutDialog(
                onClickCancel = {
                    showLogoutDialog = false
                },
                onClickLogout = {
                    viewModel.reqLogout()
                },
            )
        }

        if(showLoginAlertDialog) {
            PpsAlertDialog(
                titleRes = com.seoulmate.ui.R.string.str_need_login,
                descriptionRes = com.seoulmate.ui.R.string.str_need_login_description,
                confirmRes = com.seoulmate.ui.R.string.str_login,
                cancelRes = com.seoulmate.ui.R.string.str_cancel,
                onClickCancel = {
                    showLoginAlertDialog = false
                },
                onClickConfirm = {
                    onChangeScreen(Screen.Login)
                    showLoginAlertDialog = false
                },
            )
        }
    }
}