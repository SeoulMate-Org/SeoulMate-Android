package com.codesubmission.settings.mycomment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.codesubmission.settings.mycomment.item.EmptyMyCommentLayout
import com.codesubmission.settings.mycomment.item.MyCommentItemLayout
import com.seoulmate.ui.component.ChallengeCommentItemLayout
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCommentScreen(
    onChallengeMoreClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<MyCommentViewModel>()

    LaunchedEffect(Unit) {
        viewModel.reqMyCommentList()
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if (viewModel.needRefreshToken.value == false) {
            viewModel.reqMyCommentList()
        }
    }

    LaunchedEffect(viewModel.completedDelete.value) {
        if (viewModel.completedDelete.value) {
            viewModel.completedDelete.value = false
            viewModel.reqMyCommentList()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.my_comment_title),
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
            val (loading, list) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalArrangement = Arrangement.Top,
            ) {
                if (viewModel.myCommentList.value.isNotEmpty()) {
                    // My Comment List

                    items(
                        items = viewModel.myCommentList.value
                    ) { item ->
                        MyCommentItemLayout(
                            item = item,
                            onDeleteClick = { item ->
                                viewModel.deleteComment(item.id)
                            }
                        )
                    }

                } else {
                    // My Comment list Empty
                    item {
                        EmptyMyCommentLayout(
                            onChallengeMoreClick = onChallengeMoreClick
                        )
                    }
                }
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
    }
}