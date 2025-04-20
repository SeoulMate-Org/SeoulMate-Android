package com.seoulmate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.theme.Blue400
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray100
import com.seoulmate.ui.theme.CoolGray300

@Composable
fun LinearStampIndicator(
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    height: Dp = 8.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(4.dp))
            .padding(horizontal = 5.dp)
            .background(
                color = if (isCompleted) Blue400 else CoolGray100,
                shape = RoundedCornerShape(4.dp),
            ),
    )
}

@Composable
fun StampIndicator(
    isCompleted: Boolean = false,
) {
    Image(
        painter = painterResource(
            if(isCompleted) com.seoulmate.ui.R.drawable.ic_stamp
            else com.seoulmate.ui.R.drawable.ic_stamp_default),
        contentDescription = "Stamp Image",
    )
}