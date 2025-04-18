package com.codesubmission.home.ui.challenge.tabpage

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

// Empty Challenge
@Composable
fun EmptyChallenge(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(color = TrueWhite),
    @StringRes titleRes: Int = R.string.empty_interest_challenge,
    @StringRes infoRes: Int = R.string.empty_challenge_info,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_empty_challenge),
            contentDescription = "Empty Challenge Icon",
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(titleRes),
            fontSize = 18.sp,
            style = TextStyle(
                color = CoolGray900
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(infoRes),
            fontSize = 14.sp,
            style = TextStyle(
                color = CoolGray400
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(35.dp))
        PpsButton(
            modifier = Modifier.height(38.dp).wrapContentWidth(),
            stringRes = R.string.find_challenge,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun EmptyChallengePreview() {
    SeoulMateTheme {
        EmptyChallenge()
    }
}