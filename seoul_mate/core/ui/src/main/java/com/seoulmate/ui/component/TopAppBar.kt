package com.seoulmate.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.SeoulMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeoulMateTopAppBar(
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
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {}
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
        title = { Text(text = stringResource(id = titleRes)) },
        navigationIcon = {},
        actions = {},
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
            titleRes = R.string.top_app_bar_untitled,
            colors = TopAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.LightGray,
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black,
                subtitleContentColor = Color.LightGray,
            ),
            navigationIconRes = R.drawable.ic_home_default,
            navigationIconContentDescription = "Navigation Icon",
            actionIconRes = R.drawable.ic_home_selected,
            actionIconContentDescription = "Action Icon",
            )
    }
}