package com.seoulmate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.seoulmate.ui.R

@Composable
fun ChallengeInterestButton(
    isInterest: Boolean = false,
    size: Dp = 32.dp,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = Modifier
            .size(size),
        onClick = onClick,
    ) {
        val res = if (isInterest) {
            R.drawable.ic_interest_challenge
        } else {
            R.drawable.ic_interest_challenge_default
        }
        Image(
            painter = painterResource(id = res),
            contentDescription = "Interest Button"
        )
    }
}