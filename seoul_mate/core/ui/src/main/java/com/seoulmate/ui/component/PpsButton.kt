package com.seoulmate.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

/**
 * Common Button
 */
@Composable
fun PpsButton(
    modifier: Modifier,
    color: Color = Blue500,
    borderColor: Color = Blue500,
    @StringRes stringRes: Int,
    fontColor: Color = TrueWhite,
    cornerRound: Dp = 15.dp,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRound),
        colors = ButtonColors(
            containerColor = color,
            contentColor = color.copy(alpha = 0.5f),
            disabledContainerColor = color.copy(alpha = 0.5f),
            disabledContentColor = color.copy(alpha = 0.5f),
        ),
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor,
        )
    ) {
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(stringRes),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = fontColor,
            ),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewPpsButton() {
    SeoulMateTheme {
        PpsButton(
            modifier = Modifier,
            stringRes = R.string.str_more,
        )
    }
}