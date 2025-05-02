package com.codesubmission.settings.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingUserNicknameScreen(
    onBackClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<SettingUserNicknameViewModel>()

    val focusManager = LocalFocusManager.current
    var textFieldState by remember { mutableStateOf(UserInfo.nickName) }

    LaunchedEffect(viewModel.isCompleted.value) {
        if (viewModel.isCompleted.value) {
            // Completed Change Nickname
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
                .background(color = TrueWhite)
                .noRippleClickable {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center
        ) {
            // Nickname TextField
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
                    .noRippleClickable {
                        focusManager.clearFocus()
                    },
            ) {
                OutlinedTextField(
                    modifier = Modifier,
                    value = textFieldState,
                    onValueChange = { textFieldState = it},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }),
                )
            }
            // Button
            PpsButton(
                modifier = Modifier.fillMaxWidth(),
                stringRes = com.seoulmate.ui.R.string.str_confirm,
            ) {
                viewModel.reqUserNickname(textFieldState)
            }
        }
    }
}