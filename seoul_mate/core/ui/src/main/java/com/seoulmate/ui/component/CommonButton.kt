package com.seoulmate.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun CommonButton(
    modifier: Modifier,
    color: Color = Blue500,
    @StringRes stringRes: Int,
    fontSize: TextUnit = 16.sp,
    fontColor: Color = TrueWhite,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(15.dp),
                color = color,
            ),
        colors = ButtonColors(
            containerColor = color,
            contentColor = color.copy(alpha = 0.5f),
            disabledContainerColor = color.copy(alpha = 0.5f),
            disabledContentColor = color.copy(alpha = 0.5f),
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(stringRes),
            fontSize = fontSize,
            style = TextStyle(
                color = fontColor,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}