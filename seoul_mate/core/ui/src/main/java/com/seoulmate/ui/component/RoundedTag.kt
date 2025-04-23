package com.seoulmate.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.theme.Blue50
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray200
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun RoundedTag(
    title: String = "",
    isSelected: Boolean = false,
    defaultColor: Color = CoolGray200,
    defaultFontColor: Color = CoolGray900,
    defaultBackgroundColor: Color = TrueWhite,
    selectedColor: Color = Blue500,
    selectedBackgroundColor: Color = Blue50,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if(isSelected) selectedColor else defaultColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(color = if(isSelected) selectedBackgroundColor else defaultBackgroundColor)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        PpsText(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = if(isSelected) selectedColor else defaultFontColor,
            )
        )

    }
}

@Preview
@Composable
private fun PreviewRoundedTag() {
    SeoulMateTheme {
        RoundedTag(
            title = "Tag",
            isSelected = false,
        )
    }
}