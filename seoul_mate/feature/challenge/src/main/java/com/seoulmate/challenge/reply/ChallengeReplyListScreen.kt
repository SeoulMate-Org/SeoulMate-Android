package com.seoulmate.challenge.reply

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import com.seoulmate.challenge.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeReplyListScreen(

    onBackClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var textFieldState by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.noRippleClickable {
                    focusManager.clearFocus()
                },
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(R.string.title_reply),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = CoolGray900,
                        ),
                        fontWeight = FontWeight.SemiBold,
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
                    .background(CoolGray25),
            ) {
                // Reply List
                Box(modifier = Modifier.weight(1f))
                // Reply Input
                Row {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = textFieldState,
                        onValueChange = { textFieldState = it},
                        trailingIcon = {
                            IconButton(
                                enabled = textFieldState.trim().isNotEmpty(),
                                onClick = {

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
        ChallengeReplyListScreen()
    }
}

