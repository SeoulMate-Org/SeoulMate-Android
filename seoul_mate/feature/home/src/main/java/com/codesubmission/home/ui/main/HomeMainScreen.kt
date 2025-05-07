package com.codesubmission.home.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.DisplayMetrics
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.codesubmission.home.HomeAfterRefreshTokenType
import com.codesubmission.home.HomeViewModel
import com.codesubmission.home.R
import com.codesubmission.home.ui.main.item.ChallengeCategory
import com.codesubmission.home.ui.main.item.ChallengeRanking
import com.codesubmission.home.ui.main.item.HorizontalCarousel
import com.codesubmission.home.ui.main.item.MissingChallenge
import com.codesubmission.home.ui.main.item.MyLocationChallenge
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.ui.component.ChallengeRankingTileTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.MainTopGradientStart
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainScreen(
    context: Context,
    viewModel: HomeViewModel,
    onChallengeItemClick: (challengeId: Int) -> Unit = {},
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
    onThemeMoreClick: () -> Unit = {},
    onChangeScreen: (screen: Screen) -> Unit = { _ -> },
) {
    val locationPermission = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocationPermissionGranted = remember{ mutableStateOf(false) }
    val isShowSeoulMasterList = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    LaunchedEffect(Unit) {
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

        viewModel.startTimer()
    }

    LaunchedEffect(viewModel.isTimerOver.value) {
        if (viewModel.isTimerOver.value) {
            isShowSeoulMasterList.value = !isShowSeoulMasterList.value
            viewModel.startTimer()
        }
    }

    LaunchedEffect(viewModel.succeedChallengeProgress.value) {
        if(viewModel.succeedChallengeProgress.value) {
            viewModel.succeedChallengeProgress.value = false
            viewModel.reqHomeChallengeItems()

            onChallengeItemClick(ChallengeDetailInfo.id)
        }
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.needRefreshToken.value = null

            when(viewModel.afterRefreshToken.value) {
                HomeAfterRefreshTokenType.LikeChallenge -> {
                    viewModel.afterRefreshToken.value = null

                    viewModel.afterRefreshTokenChallengeId.value?.let {
                        viewModel.reqChallengeLike(it)
                        viewModel.afterRefreshTokenChallengeId.value = null
                    }
                }
                HomeAfterRefreshTokenType.PullToRefresh -> {
                    viewModel.afterRefreshToken.value = null

                    viewModel.reqHomeChallengeItems()
                }
                else -> {}
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TrueWhite,
    ) {
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = viewModel.isRefreshing.value,
            onRefresh = {
                viewModel.reqHomeChallengeItems()
            },
//            indicator = {
//                Indicator(
//                    modifier = Modifier.align(Alignment.TopCenter),
//                    isRefreshing = viewModel.isRefreshing.value,
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    state = state
//                )
//            },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                // Challenge Carousel Section
                item {
                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(listOf(MainTopGradientStart, TrueWhite)),
                                shape = RectangleShape,
                                alpha = 1f,
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            PpsText(
                                modifier = Modifier.padding(start = 20.dp, bottom = 16.dp),
                                text = stringResource(R.string.home_top_title),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = White,
                                ),
                            )
                            Image(
                                painter = painterResource(com.seoulmate.ui.R.drawable.img_main_top),
                                contentDescription = "Main Top Title Image"
                            )
                        }
                        HorizontalCarousel(
                            itemList = if (isShowSeoulMasterList.value) {
                                ChallengeInfo.challengeSeoulMasterList
                            } else {
                                ChallengeInfo.challengeCulturalList
                            },
                            screenWidth =
                            (context.resources.displayMetrics.widthPixels / ((context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT)).dp,
                            onChallengeItemClick = onChallengeItemClick,
                            onChallengeLikeClick = { challengeId ->
                                viewModel.reqChallengeLike(challengeId)
                            }
                        )
                    }
                }
                if(ChallengeInfo.getChallengeLocationList().isNotEmpty()) {
                    // My Location Challenge
                    item {
                        Surface(
                            modifier = Modifier.padding(top = 48.dp),
                            color = TrueWhite
                        ) {
                            MyLocationChallenge(
                                modifier = Modifier.fillMaxWidth(),
                                isLoginUser = UserInfo.isUserLogin(),
                                isLocationPermission = fineLocationPermissionGranted.value,
                                possibleStampList = ChallengeInfo.getChallengeLocationList(),
                                onSignUpClick = {
                                    onChangeScreen(Screen.Login)
                                },
                                onLocationPermissionClick = {
                                    launcher.launch(
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data = "package:${context.packageName}".toUri()
                                        })
                                },
                                onItemCLick = { challengeId ->
                                    onChallengeItemClick(challengeId)
                                }
                            )
                        }
                    }
                }

                // Challenge Category
                item {
                    Column (
                        modifier = Modifier
                            .padding(top = 48.dp)
                            .background(color = TrueWhite),
                    ) {
                        ChallengeCategory(
                            modifier = Modifier.fillMaxWidth(),
                            themeItemList = viewModel.challengeThemeList.value,
                            onMoreClick = onThemeMoreClick,
                            onChallengeLikeClick = onChallengeLikeClick,
                            onChallengeItemClick = onChallengeItemClick,
                        )
                    }
                }
                // Missing Challenge
                if (ChallengeInfo.challengeStampData != null) {
                    item {
                        MissingChallenge(
                            dataCode = ChallengeInfo.challengeStampData!!.dataCode,
                            onItemClick = { challengeId ->
                                onChallengeItemClick(challengeId)
                            },
                            startChallengeClick = { challengeId ->
                                viewModel.reqProgressChallengeStatus(challengeId)
                            }
                        )
                    }
                }
                // Challenge Ranking
                item {
                    Column(
                        modifier = Modifier
                            .background(color = CoolGray25)
                    ) {
                        Spacer(modifier = Modifier.height(30.dp))

                        ChallengeRanking(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
//                        onMoreClick = {
//                            onChangeScreen(Screen.ChallengeRankList)
//                        },
                        )

                        for(index in 0..4) {
                            Box(modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 20.dp),
                            ) {
                                ChallengeRankingTileTypeLayout(
                                    item = viewModel.challengeRankList.value[index],
                                    index = index,
                                    onItemClick = { item ->
                                        onChallengeItemClick(item.id)
                                    },
                                    onItemLikeClick = { item ->
                                        onChallengeLikeClick(item.id)
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(90.dp))

                    }
                }
            }
        }

    }
}