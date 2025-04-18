package com.seoulmate.ui.component

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun RoundedIconTag(
    modifier: Modifier = Modifier
        .height(30.dp)
        .padding(horizontal = 10.dp),
    @DrawableRes drawableIconRes: Int,
    @StringRes titleRes: Int,
    @ColorRes colorRes: Int,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(onClick = onClick, role = Role.Button,)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .fillMaxHeight()
                    .background(colorResource(colorRes).copy(alpha = 0.5f)),
            ) {
                Icon(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp)
                        .align(Alignment.Center),
                    painter = painterResource(drawableIconRes),
                    contentDescription = "Map Tag",
                )
            }
            Box (
              modifier = Modifier
                  .wrapContentWidth()
                  .fillMaxHeight()
                  .background(Color.Transparent)
                  .padding(horizontal = 5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.Center),
                    text = stringResource(titleRes),
                    fontSize = 13.sp,
                    style = TextStyle(
                        color = CoolGray900
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreViewRoundedTag() {
    RoundedIconTag(
        drawableIconRes = R.drawable.ic_bottom_nav_favorite,
        titleRes = R.string.str_test,
        colorRes = R.color.orange,
    )
}