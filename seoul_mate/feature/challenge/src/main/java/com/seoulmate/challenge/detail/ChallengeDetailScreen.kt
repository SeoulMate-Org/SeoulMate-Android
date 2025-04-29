package com.seoulmate.challenge.detail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.challenge.R
import com.seoulmate.challenge.detail.item.AttractionItemTile
import com.seoulmate.challenge.detail.item.BottomChallengeDetail
import com.seoulmate.challenge.detail.item.ChallengeStamp
import com.seoulmate.challenge.detail.item.TopChallengeDetailInfo
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.ChallengeCommentItemLayout
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.Blue400
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.ColorEDF4FF
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    context: Context,
    challengeId: Int,
    onBackClick: () -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
    onAttractionClick: (attractionId: Int) -> Unit = {},
) {
    val viewModel = hiltViewModel<ChallengeDetailViewModel>()
    val locationPermission = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocationPermissionGranted = remember{ mutableStateOf(false) }
    var showLoginAlertDialog by remember { mutableStateOf(false) }

    val isShowBottomFloating = true

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun checkLocationPermission(grantedAction: () -> Unit = {}) {
        // TODO chan show alert
        if (!fineLocationPermissionGranted.value) {
            launcher.launch(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${context.packageName}".toUri()
                })
        } else {
            grantedAction()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getChallengeItem(
            id = challengeId,
            language = if(UserInfo.localeLanguage == "ko") "KOR" else "ENG"
        )

        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    LaunchedEffect(viewModel.needUserLogin.value) {
        showLoginAlertDialog = viewModel.needUserLogin.value
    }

    LaunchedEffect(UserInfo.accessToken) {
        if (UserInfo.isUserLogin()) {
            showLoginAlertDialog = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
                ),
                scrollBehavior = exitUntilCollapsedScrollBehavior(),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = TrueWhite)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Transparent)
            ) {
                val (body, floating) = createRefs()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(body) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    // Top Challenge Info
                    item {
                        TopChallengeDetailInfo(item = viewModel.challengeItem.value)
                    }
                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    // Stamp
                    item {
                        ChallengeStamp(
                            attractions = viewModel.challengeItem.value.attractions
                        )
                    }
                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    if (viewModel.challengeItem.value.attractions.isNotEmpty()) {
                        // Stamp Mission Location
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 28.dp),
                            ) {
                                PpsText(
                                    modifier = Modifier.wrapContentSize(),
                                    text = stringResource(id = R.string.stamp_mission_location_title),
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = CoolGray900,
                                    )
                                )
                                PpsText(
                                    modifier = Modifier.wrapContentSize(),
                                    text = stringResource(id = R.string.stamp_mission_location_info),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = CoolGray400,
                                    )
                                )
                            }
                        }
                        items(
                            count = viewModel.challengeItem.value.attractions.size
                        ) { index ->
                            Column (modifier = Modifier.padding(horizontal = 20.dp)) {
                                if(index > 0) {
                                    HorizontalDivider(
                                        color = CoolGray25,
                                        thickness = 1.dp,
                                    )
                                }

                                AttractionItemTile(
                                    item = viewModel.challengeItem.value.attractions[index],
                                    onItemClick = { item ->
                                        onAttractionClick(item.id)
                                    },
                                    onItemLikeClick = { item ->
                                        viewModel.reqAttractionLike(item.id)
                                    }
                                )
                            }
                        }
                    }

                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    // Comment
                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .padding(top = 16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                PpsText(
                                    modifier = Modifier.wrapContentSize(),
                                    text = stringResource(
                                        id = R.string.title_comment,
                                        viewModel.challengeItem.value.comments.size
                                    ),
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = CoolGray900,
                                    )
                                )
                                if (viewModel.isStamped.value) {
                                    IconButton(
                                        onClick = {
                                            onChangeScreen(Screen.ChallengeCommentList)
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp),
                                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_write),
                                            contentDescription = "Write Icon",
                                            tint = CoolGray300,
                                        )
                                    }
                                }
                            }

                            if (!viewModel.isStamped.value) {
                                Column(
                                    modifier = Modifier.height(53.dp).fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    PpsText(
                                        modifier = Modifier.padding(start = 10.dp),
                                        text = stringResource(R.string.comment_empty),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = CoolGray900,
                                        )
                                    )
                                }
                            }
                        }
                    }
                    items(
                        count = ChallengeDetailInfo.commentList.size
                    ) { index ->
                        Column (modifier = Modifier.padding(horizontal = 20.dp)) {
                            if (index > 0) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 16.dp),
                                    color = CoolGray25,
                                    thickness = 1.dp,
                                )
                            }

                            ChallengeCommentItemLayout(
                                item = ChallengeDetailInfo.commentList[index]
                            )
                        }
                    }

                    // Bottom Spacer
                    item {
                        Spacer(modifier = Modifier.height(55.dp))
                    }

                }

                // Bottom Floating UI
                if (isShowBottomFloating) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .wrapContentWidth()
                            .background(
                                color = ColorEDF4FF,
                                shape = RoundedCornerShape(18.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Blue400,
                                shape = RoundedCornerShape(18.dp),
                            )
                            .constrainAs(floating) {
                                linkTo(top = parent.top, bottom = parent.bottom, bias = 1f)
                                linkTo(start = parent.start, end = parent.end)
                            }
                    ) {
                        PpsText(
                            modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 11.dp),
                            text = stringResource(R.string.stamp_floating),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = Blue500,
                            )
                        )
                    }
                }
            }

            // Bottom
            BottomChallengeDetail(
                isLogin = UserInfo.isUserLogin(),
                isFavorite = viewModel.challengeItem.value.isInterest,
                startedChallenge = viewModel.startedChallenge.value,
                onChangeScreen = onChangeScreen,
                onMapClick = {
                    checkLocationPermission{
                        onChangeScreen(Screen.PlaceInfoDetail)
                    }
                },
                onChallengeStartClick = {
                    checkLocationPermission{
                        viewModel.reqProgressChallengeStatus()
                    }
                },
                onStampClick = {
                    checkLocationPermission{
                        viewModel.reqAttractionStamp(36)
                    }
                },
                onFavoriteClick = { favorite ->
                    viewModel.reqChallengeLike()
                }
            )

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

}