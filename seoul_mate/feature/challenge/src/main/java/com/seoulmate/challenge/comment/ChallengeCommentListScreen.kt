package com.seoulmate.challenge.comment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.challenge.R
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.ui.component.ChallengeCommentItemLayout
import com.seoulmate.ui.component.PpsCommentAlertDialog
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeCommentListScreen(
    onBackClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var textFieldState by remember { mutableStateOf("") }
    var isShowDialog by remember { mutableStateOf(false) }
    var selectedCommentItem by remember { mutableStateOf<ChallengeCommentItem?>(null) }
    
    val viewModel = hiltViewModel<ChallengeCommentViewModel>()

    val backPressedState by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.commentList.value = ChallengeDetailInfo.commentList
    }

    LaunchedEffect(viewModel.completedWrite.value) {
        if(viewModel.completedWrite.value) {
            viewModel.completedWrite.value = false

            viewModel.getCommentList(
                id = ChallengeDetailInfo.id,
                language = UserInfo.getLanguageCode(),
            )
            textFieldState = ""
            focusManager.clearFocus()
            selectedCommentItem = null
        }

    }

    BackHandler(enabled = backPressedState) {
        if(selectedCommentItem == null) {
            onBackClick()
        } else {
            selectedCommentItem = null
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.noRippleClickable {
                    focusManager.clearFocus()
                },
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(
                            if (selectedCommentItem == null) R.string.comment_list_title
                            else R.string.comment_list_modify_title
                        ),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(selectedCommentItem == null) {
                                onBackClick()
                            } else {
                                selectedCommentItem = null
                            }
                        }
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
    ){ padding ->
        Surface(
            modifier = Modifier
                .background(color = TrueWhite)
                .padding(padding)
                .noRippleClickable {
                    focusManager.clearFocus()
                },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .imePadding(),
            ) {
                // Comment List
                Box(modifier = Modifier.weight(1f)) {
                    if (viewModel.commentList.value.isEmpty()) {
                        PpsText(
                            modifier = Modifier.fillMaxWidth().padding(top = 36.dp),
                            text = stringResource(R.string.comment_list_empty),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = CoolGray900,
                            ),
                            textAlign = TextAlign.Center,
                        )

                    } else {
                        LazyColumn {
                            items(viewModel.commentList.value.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 11.dp)
                                ) {
                                    ChallengeCommentItemLayout(
                                        item = viewModel.commentList.value[index],
                                        onClickMore = { item ->
                                            selectedCommentItem = item
                                            isShowDialog = true
                                        }
                                    )
                                }

                            }
                        }
                    }
                }

                // Comment Input
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = textFieldState,
                        onValueChange = { textFieldState = it},
                        trailingIcon = {
                            IconButton(
                                enabled = textFieldState.trim().isNotEmpty(),
                                onClick = {
                                    if (selectedCommentItem == null) {
                                        viewModel.writeComment(
                                            id = ChallengeDetailInfo.id,
                                            comment = textFieldState,
                                        )
                                    } else {
                                        viewModel.modifyComment(
                                            commentId = selectedCommentItem!!.id,
                                            comment = textFieldState,
                                        )
                                    }

                                }
                            ) {
                                val imgRes = if(textFieldState.trim().isNotBlank()) {
                                    com.seoulmate.ui.R.drawable.ic_input_box
                                } else {
                                    com.seoulmate.ui.R.drawable.ic_input_box_default
                                }
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(id = imgRes),
                                    contentDescription = "Send TextField Item icon",
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                        placeholder = {
                            PpsText(
                                modifier = Modifier,
                                text = stringResource(R.string.comment_hint),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = CoolGray300,
                                )
                            )
                        }
                    )

                }
            }

            if (isShowDialog) {
                selectedCommentItem?.let {
                    PpsCommentAlertDialog(
                        onClickCancel = {
                            isShowDialog = false
                            selectedCommentItem = null
                            textFieldState = ""
                        },
                        onClickDelete = {
                            viewModel.deleteComment(it.id)
                            isShowDialog = false
                        },
                        onClickModify = {
                            textFieldState = it.comment
                            isShowDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChallenReplyListScreen() {
    SeoulMateTheme {
        ChallengeCommentListScreen()
    }
}

