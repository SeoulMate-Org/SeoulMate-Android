package com.codesubmission.settings.withdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.codesubmission.settings.withdraw.item.WithdrawConsiderationInfoLayout
import com.codesubmission.settings.withdraw.item.WithdrawDialog
import com.codesubmission.settings.withdraw.item.WithdrawReasonLayout
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(
    onBackClick: () -> Unit = {},
    completedWithdraw: () -> Unit = {},
) {
    val viewModel = hiltViewModel<WithdrawViewModel>()

    var showWithdrawDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.succeedClearUserInfo.value) {
        if (viewModel.succeedClearUserInfo.value) {
            showWithdrawDialog = false
            completedWithdraw()
        }
    }

    LaunchedEffect(viewModel.succeedDeleteUser.value) {
        if (viewModel.succeedDeleteUser.value) {
            viewModel.removeUserInfo()
        }
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if (viewModel.needRefreshToken.value == false) {
            viewModel.deleteUser()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(com.seoulmate.ui.R.string.withdraw_title),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        ),
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

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                .background(color = TrueWhite)
                .padding(padding)
        ) {
            val (loading, body, bottomButton) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(body) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        linkTo(top = parent.top, bottom = bottomButton.top, bias = 0f)
                    }
            ) {
                // Top Info
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 36.dp)
                    ) {
                        PpsText(
                            modifier = Modifier,
                            text = stringResource(R.string.withdraw_info_title),
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = CoolGray900,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        PpsText(
                            modifier = Modifier,
                            text = stringResource(R.string.withdraw_info_sub_title),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CoolGray600,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            thickness = 1.dp,
                            color = CoolGray50,
                        )
                    }
                }
                // Consideration Info
                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    WithdrawConsiderationInfoLayout()
                }
                // Reason
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    WithdrawReasonLayout()
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
            // Bottom Withdraw Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(bottomButton) {
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                PpsButton(
                    modifier = Modifier.weight(1f).height(51.dp),
                    stringRes = com.seoulmate.ui.R.string.str_cancel,
                    color = CoolGray50,
                    borderColor = CoolGray50,
                    fontColor = Black,
                    onClick = onBackClick,
                    cornerRound = 11.dp,
                )
                Spacer(modifier = Modifier.width(10.dp))
                PpsButton(
                    modifier = Modifier.weight(1f).height(51.dp),
                    stringRes = com.seoulmate.ui.R.string.withdraw_title,
                    onClick = {
                        showWithdrawDialog = true
                    },
                    cornerRound = 11.dp,
                )

            }

            if (viewModel.isShowLoading.value) {
                PpsLoading(
                    modifier = Modifier.wrapContentSize()
                        .constrainAs(loading) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )
            }
        }

        if (showWithdrawDialog) {
            WithdrawDialog(
                onClickCancel = {
                    showWithdrawDialog = false
                },
                onClickWithdraw = {
                    viewModel.deleteUser()
                },
            )
        }
    }
}