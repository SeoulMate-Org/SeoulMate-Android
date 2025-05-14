package com.seoulmate.challenge.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.challenge.R
import com.seoulmate.challenge.theme.item.ChallengeThemeTabRow
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.ChallengeHomeTileTypeLayout
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeThemeListScreen(
    coroutineScope: CoroutineScope,
    onChangeScreen: (screen: Screen) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { ChallengeInfo.themeList.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    var showLoginAlertDialog by remember { mutableStateOf(false) }

    val viewModel = hiltViewModel<ChallengeThemeListViewModel>()

    LaunchedEffect(viewModel.needUserLogin.value) {
        if (viewModel.needUserLogin.value) {
            showLoginAlertDialog = true
            viewModel.needUserLogin.value = false
        }
    }

    LaunchedEffect(viewModel.needUserLogin.value) {
        if (viewModel.needUserLogin.value) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.needRefreshToken.value = null
            viewModel.selectedChallengeId.value?.let {
                viewModel.reqChallengeLike(it)
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(R.string.title_theme),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Back Icon",
                            tint = Black,
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = TrueWhite,
                    scrolledContainerColor = TrueWhite,
                    navigationIconContentColor = CoolGray900,
                    titleContentColor = CoolGray900,
                    actionIconContentColor = CoolGray900,
                    subtitleContentColor = CoolGray900,
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            ChallengeThemeTabRow(
                coroutineScope = coroutineScope,
                pageList = ChallengeInfo.themeList,
                pagerState = pagerState,
            )
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                // Challenge Tab Contents
                HorizontalPager(
                    modifier = Modifier.weight(1f),
                    state = pagerState,
                    userScrollEnabled = false,
                ) { pageIndex ->
                    LazyColumn {
                        items(
                            count = ChallengeInfo.challengeThemeList[pageIndex].size,
                            key = { index -> ChallengeInfo.challengeThemeList[pageIndex][index].id }
                        ) { index ->
                            if (index == 0) {
                                if (!UserInfo.isUserLogin()) {
                                    Spacer(modifier = Modifier.height(24.dp))
                                    ThemeLoginTile {
                                        onChangeScreen(Screen.Login)
                                    }
                                }
                                Spacer(modifier = Modifier.height(18.dp))
                            }
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                ChallengeHomeTileTypeLayout(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = ChallengeInfo.challengeThemeList[pageIndex][index],
                                    onChallengeLikeClick = { challengeId ->
                                        viewModel.reqChallengeLike(challengeId)
                                    }
                                )
                            }
                            if (index == ChallengeInfo.challengeThemeList[pageIndex].size - 1) {
                                // More Title
                                Box(
                                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                        .padding(vertical = 16.dp),
                                ) {
                                    PpsText(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = stringResource(R.string.theme_last_item),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = CoolGray600
                                        ),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                        }
                    }
                }

            }

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

@Composable
fun ThemeLoginTile(
    goLogin: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color1D8EFE)
            .clickable {
                goLogin()
            },
    ) {
        Row(
            modifier = Modifier.padding(vertical = 13.dp).wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp).weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.theme_login_title),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TrueWhite,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.theme_login_sub_title),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = White,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Box(
                modifier = Modifier
            ) {
                Image(
                    modifier = Modifier.size(88.dp),
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_luggage),
                    contentDescription = "SignUp Image",
                    contentScale = ContentScale.Fit,
                )
            }

        }
    }
}