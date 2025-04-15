package com.codesubmission.home.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SeoulMateTopAppBar
import com.seoulmate.ui.component.SeoulMateTopAppBarOnlyActionIcon
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    currentRoute: String,
    onSearchClick: () -> Unit = {},
) {
    when(currentRoute) {
        Screen.HomeMyPage.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(com.seoulmate.ui.R.string.bottom_nav_travel_map_title),
                        fontSize = 18.sp,
                        style = TextStyle(
                            color = CoolGray900,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
        Screen.HomeMain.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(com.seoulmate.ui.R.string.bottom_nav_favorite_title),
                        fontSize = 18.sp,
                        style = TextStyle(
                            color = CoolGray900,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        textAlign = TextAlign.Center,
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_bottom_nav_map),
                            contentDescription = "Search Icon",
                            tint = Black,
                        )
                    }
                }
            )
        }
        Screen.HomeChallenge.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(com.seoulmate.ui.R.string.bottom_nav_suggest_title),
                        fontSize = 18.sp,
                        style = TextStyle(
                            color = CoolGray900,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    }
}