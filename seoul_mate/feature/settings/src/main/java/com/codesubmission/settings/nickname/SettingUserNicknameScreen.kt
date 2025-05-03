package com.codesubmission.settings.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue100
import com.seoulmate.ui.theme.Blue300
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.Red
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

sealed class ChangeNicknameType(val color: Color) {
    data object CanChange : ChangeNicknameType(Blue500)
    data object Error : ChangeNicknameType(Red)
    data object Default : ChangeNicknameType(CoolGray300)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingUserNicknameScreen(
    onBackClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<SettingUserNicknameViewModel>()

    val focusManager = LocalFocusManager.current
    var textFieldState by remember { mutableStateOf(UserInfo.nickName) }

    var canChange by remember { mutableStateOf(false) }
    var changeType by remember { mutableStateOf<ChangeNicknameType>(ChangeNicknameType.Default) }

    LaunchedEffect(viewModel.isCompleted.value) {
        if (viewModel.isCompleted.value) {
            // Completed Change Nickname
            onBackClick()
        }
    }

    LaunchedEffect(viewModel.duplicationNickname.value) {
        if (viewModel.duplicationNickname.value) {
            // Nickname Duplication
            changeType = ChangeNicknameType.Error
            canChange = false
        }
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.needRefreshToken.value = null

            viewModel.reqUserNickname(textFieldState)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.nickname_title),
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
        Column(
            modifier = Modifier.padding(padding)
                .background(color = White)
                .noRippleClickable {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Top,
        ) {
            // Nickname TextField
            Column (
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 20.dp)
                    .padding(top = 48.dp)
                    .noRippleClickable {
                        focusManager.clearFocus()
                    },
            ) {
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = CoolGray25)
                        .border(
                            width = 1.dp,
                            color = changeType.color,
                            shape = RoundedCornerShape(10.dp),
                        )
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = CoolGray25),
                        colors = TextFieldDefaults.colors().copy(
                            cursorColor = Blue500,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedLeadingIconColor = Blue500,
                            focusedPrefixColor = Blue500,
                            focusedSuffixColor = Blue500,
                            focusedLabelColor = Blue500,
                            focusedPlaceholderColor = Blue500,
                            focusedContainerColor = CoolGray25,
                            disabledContainerColor = CoolGray25,
                            unfocusedContainerColor = CoolGray25,
                        ),
                        value = textFieldState,
                        onValueChange = {
                            textFieldState = it
                            viewModel.duplicationNickname.value = false
                            if (it.length in 2..15) {
                                canChange = true
                                changeType = ChangeNicknameType.CanChange
                            } else {
                                canChange = false
                                changeType = ChangeNicknameType.Default
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        modifier = Modifier.background(color = CoolGray25),
                        onClick = {
                            viewModel.duplicationNickname.value = false
                            textFieldState = ""
                            changeType = ChangeNicknameType.Default
                            canChange = false
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_close),
                            contentDescription = "Clear Icon",
                            tint = CoolGray600,
                        )
                    }
                }
                //
                PpsText(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 10.dp),
                    text = stringResource(
                        if (changeType == ChangeNicknameType.Error) {
                            R.string.nickname_duplication
                        } else {
                            R.string.nickname_sub
                        }
                    ),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGray400,
                    )
                )
            }

            // Button
            Box(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                PpsButton(
                    modifier = Modifier.fillMaxWidth().height(51.dp),
                    stringRes = com.seoulmate.ui.R.string.str_complete,
                    cornerRound = 12.dp,
                    fontColor = if(canChange) TrueWhite else Blue300,
                    color = if(canChange) Blue500 else Blue100,
                    borderColor = if(canChange) Blue500 else Blue100,
                ) {
                    if (canChange) {
                        viewModel.reqUserNickname(textFieldState)
                    }
                }
            }

        }
    }
}