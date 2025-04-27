package com.seoulmate.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seoulmate.data.UserInfo
import com.seoulmate.login.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun SignupSucceedScreen(
    succeedSignup: () -> Unit,
) {

    Scaffold(
        containerColor = TrueWhite,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = TrueWhite),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Welcome!!
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(com.seoulmate.ui.R.drawable.img_location_point),
                    contentDescription = "Succeed Image"
                )
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.str_welcome),
                )
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.str_welcome_sub, UserInfo.nickName),
                )
            }
            // Confirm Button
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                PpsButton(
                    modifier = Modifier.fillMaxWidth(),
                    stringRes = com.seoulmate.ui.R.string.str_confirm,
                ) {
                    succeedSignup()
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewSignupSucceedScreen() {
    SeoulMateTheme {
        SignupSucceedScreen(
            succeedSignup = {},
        )
    }
}