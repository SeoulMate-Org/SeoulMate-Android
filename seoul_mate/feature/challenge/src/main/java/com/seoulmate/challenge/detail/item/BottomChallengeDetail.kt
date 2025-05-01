package com.seoulmate.challenge.detail.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seoulmate.challenge.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.Red
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@Composable
fun BottomChallengeDetail(
    isLogin: Boolean = false,
    isFavorite: Boolean = false,
    startedChallenge: Boolean = false,
    onChangeScreen: (Screen) -> Unit = {},
    onStampClick: () -> Unit = {},
    onChallengeStartClick: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onFavoriteClick: (Boolean) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RectangleShape,
        colors = CardColors(
            contentColor = TrueWhite,
            containerColor = TrueWhite,
            disabledContentColor = TrueWhite,
            disabledContainerColor = TrueWhite,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 55.dp
        )
    ) {
        BottomRow(
            isLogin = isLogin,
            isFavorite = isFavorite,
            startedChallenge = startedChallenge,
            onChangeScreen = onChangeScreen,
            onStampClick = onStampClick,
            onChallengeStartClick = onChallengeStartClick,
            onMapClick = onMapClick,
            onFavoriteClick = onFavoriteClick,
        )
    }
}

@Composable
private fun BottomRow(
    isLogin: Boolean = false,
    isFavorite: Boolean = false,
    startedChallenge: Boolean = false,
    onChangeScreen: (Screen) -> Unit = {},
    onStampClick: () -> Unit = {},
    onChallengeStartClick: () -> Unit = {},
    onMapClick: () -> Unit = {},
    onFavoriteClick: (Boolean) -> Unit = {},
) {
    var rememberFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        rememberFavorite.value = isFavorite
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(vertical = 10.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Like
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = {
                rememberFavorite.value = !rememberFavorite.value
                onFavoriteClick(rememberFavorite.value)
            },
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        if (isFavorite) {
                            com.seoulmate.ui.R.drawable.ic_bottom_nav_fill_favorite
                        } else {
                            com.seoulmate.ui.R.drawable.ic_bottom_nav_favorite
                        }
                    ),
                    contentDescription = "Challenge Bottom Favorite",
                    tint = if (isFavorite) Red else CoolGray500
                )
                PpsText(
                    modifier = Modifier,
                    text = stringResource(com.seoulmate.ui.R.string.str_favorite),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGray700,
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(15.dp))
        // Button
        Row(
            modifier = Modifier.weight(1f),
        ) {
            if (isLogin) {
                PpsButton(
                    modifier = Modifier.weight(1f),
                    stringRes = R.string.show_in_map,
                    color = TrueWhite,
                    fontColor = Blue500,
                    cornerRound = 10.dp,
                ){
                    onMapClick()
                }
                Spacer(modifier = Modifier.width(10.dp))
                if (startedChallenge) {
                    PpsButton(
                        modifier = Modifier.weight(1f),
                        stringRes = R.string.tap_stamp,
                        cornerRound = 10.dp,
                    ){
                        onStampClick()
                    }
                } else {
                    PpsButton(
                        modifier = Modifier.weight(1f),
                        stringRes = R.string.start_challenge,
                        cornerRound = 10.dp,
                    ){
                        onChallengeStartClick()
                    }
                }
            } else {
                PpsButton(
                    modifier = Modifier.weight(1f),
                    stringRes = R.string.tap_stamp_after_login,
                    cornerRound = 10.dp,
                ) {
                    onChangeScreen(Screen.Login)
                }
            }
        }

    }
}

@Preview
@Composable
private fun NeedLogin() {
    SeoulMateTheme {
        BottomChallengeDetail(
            isLogin = false,
        )
    }
}

@Preview
@Composable
private fun Login() {
    SeoulMateTheme {
        BottomChallengeDetail(
            isLogin = true,
            startedChallenge = false
        )
    }
}

@Preview
@Composable
private fun StartChallenge() {
    SeoulMateTheme {
        BottomChallengeDetail(
            isLogin = true,
            startedChallenge = true,
        )
    }
}