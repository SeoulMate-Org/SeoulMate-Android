package com.seoulmate.challenge.attraction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.challenge.attraction.item.TopAttractionDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionDetailScreen(
    attractionId: Int,
    onBackClick: () -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
) {
    val viewModel = hiltViewModel<AttractionDetailViewModel>()
    var showLoginAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAttractionDetailInfo(attractionId)
    }

    LaunchedEffect(viewModel.needUserLogin.value) {
        showLoginAlertDialog = viewModel.needUserLogin.value
    }

    LaunchedEffect(UserInfo.accessToken) {
        if (UserInfo.isUserLogin()) {
            showLoginAlertDialog = false
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = "",
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
        ConstraintLayout(
            modifier = Modifier
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            val (body, loading) = createRefs()

            LazyColumn (
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                viewModel.attractionItem.value?.let {

                    item {
                        TopAttractionDetailInfo(
                            item = it,
                        )
                    }

                    item {
                        HorizontalDivider(
                            color = CoolGray25,
                            thickness = 1.dp,
                        )
                    }

                    item {
                        Column {
                            PpsText(
                                modifier = Modifier,
                                text = it.address
                            )
                            PpsText(
                                modifier = Modifier,
                                text = it.businessHours
                            )
                            PpsText(
                                modifier = Modifier,
                                text = it.homepageUrl
                            )
                            PpsText(
                                modifier = Modifier,
                                text = it.subway
                            )
                        }
                    }

                }

            }
            // Loading UI
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
            // Login UI
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