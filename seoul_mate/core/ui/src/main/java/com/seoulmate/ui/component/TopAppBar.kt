package com.seoulmate.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = topAppBarColors(),
    strTitle: String,
    @DrawableRes navigationIconRes: Int,
    navigationIconContentDescription: String,
    onNavigationClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = strTitle,
                fontSize = 18.sp,
                style = TextStyle(
                    color = CoolGray900,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        },
        navigationIcon = {
            Button(
                modifier = Modifier.size(25.dp),
                onClick = onNavigationClick
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = navigationIconRes),
                    contentDescription = navigationIconContentDescription,
                    tint = Black,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = topAppBarColors(),
    @StringRes titleRes: Int,
    @DrawableRes navigationIconRes: Int,
    navigationIconContentDescription: String,
    onNavigationClick: () -> Unit = {},
    @DrawableRes actionIconRes: Int,
    actionIconContentDescription: String,
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                style = TextStyle(
                    color = CoolGray900,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        },
        navigationIcon = {},
        actions = {},
        scrollBehavior = exitUntilCollapsedScrollBehavior(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBarOnlyActionIcon(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = topAppBarColors(),
    @StringRes titleRes: Int,
    @DrawableRes actionIconRes: Int,
    actionIconContentDescription: String,
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                style = TextStyle(
                    color = CoolGray900,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        },
        actions = {
            IconButton(
                onClick = onActionClick
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = actionIconRes),
                    contentDescription = actionIconContentDescription,
                    tint = Black,
                )
            }
        },
        scrollBehavior = exitUntilCollapsedScrollBehavior(),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBarOnlyNavigationIcon(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = topAppBarColors(),
    @StringRes titleRes: Int,
    @DrawableRes navigationIconRes: Int,
    navigationIconContentDescription: String,
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                style = TextStyle(
                    color = CoolGray900,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationClick
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(id = navigationIconRes),
                    contentDescription = navigationIconContentDescription,
                    tint = Black,
                )
            }
        },
        scrollBehavior = exitUntilCollapsedScrollBehavior(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBar(
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = topAppBarColors(),
    @StringRes titleRes: Int,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = colors,
        title = {
            Text(
                text = stringResource(titleRes),
                fontSize = 18.sp,
                style = TextStyle(
                    color = CoolGray900,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        },
        scrollBehavior = exitUntilCollapsedScrollBehavior(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun SeoulMateTopAppBarPreview() {
    SeoulMateTheme {
        SeoulMateTopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            strTitle = "Title",
            colors = TopAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.LightGray,
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black,
                subtitleContentColor = Color.LightGray,
            ),
            navigationIconRes = R.drawable.ic_bottom_nav_fill_home,
            navigationIconContentDescription = "Navigation Icon",
            scrollBehavior = exitUntilCollapsedScrollBehavior(),
        )
    }
}