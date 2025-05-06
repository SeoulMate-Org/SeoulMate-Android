package com.codesubmission.settings.badge

import android.widget.ScrollView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.seoulmate.data.model.challenge.ChallengeMyBadgeData
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingMyBadgeScreen(
    onBackClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<SettingMyBadgeViewModel>()

    val selectedIconList = listOf(
        com.seoulmate.ui.R.drawable.ic_theme_01,
        com.seoulmate.ui.R.drawable.ic_theme_02,
        com.seoulmate.ui.R.drawable.ic_theme_03,
        com.seoulmate.ui.R.drawable.ic_theme_04,
        com.seoulmate.ui.R.drawable.ic_theme_05,
        com.seoulmate.ui.R.drawable.ic_theme_06,
        com.seoulmate.ui.R.drawable.ic_theme_07,
        com.seoulmate.ui.R.drawable.ic_theme_08,
        com.seoulmate.ui.R.drawable.ic_theme_09,
    )

    val defaultIconList = listOf(
        com.seoulmate.ui.R.drawable.ic_theme_01_default,
        com.seoulmate.ui.R.drawable.ic_theme_02_default,
        com.seoulmate.ui.R.drawable.ic_theme_03_default,
        com.seoulmate.ui.R.drawable.ic_theme_04_default,
        com.seoulmate.ui.R.drawable.ic_theme_05_default,
        com.seoulmate.ui.R.drawable.ic_theme_06_default,
        com.seoulmate.ui.R.drawable.ic_theme_07_default,
        com.seoulmate.ui.R.drawable.ic_theme_08_default,
        com.seoulmate.ui.R.drawable.ic_theme_09_default,
    )

    val titleList = listOf(
        R.string.badge_item_01,
        R.string.badge_item_02,
        R.string.badge_item_03,
        R.string.badge_item_04,
        R.string.badge_item_05,
        R.string.badge_item_06,
        R.string.badge_item_07,
        R.string.badge_item_08,
        R.string.badge_item_09,
    )

    val badgeItemList = mutableListOf<BadgeData>()

    selectedIconList.forEachIndexed { index, item ->
        badgeItemList.add(
            BadgeData(
                selectedIcon = item,
                defaultIcon = defaultIconList[index],
                title = titleList[index],)
        )
    }

    LaunchedEffect(Unit) {
        viewModel.reqMyBadge()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.badge_title),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Search Icon",
                            tint = CoolGray900,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrueWhite,
                    titleContentColor = TrueWhite,
                    navigationIconContentColor = TrueWhite,
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = CoolGray25),
        ) {
            //
//            Box (
//                modifier = Modifier
//                    .height(80.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(15.dp)
//                        .background(shape = RoundedCornerShape(16.dp), color = TrueWhite)
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f),
//                        verticalArrangement = Arrangement.Center,
//                    ) {
//                        PpsText(
//                            modifier = Modifier,
//                            text = stringResource(R.string.badge_info_title),
//                            style = MaterialTheme.typography.labelLarge.copy(
//                                color = CoolGray500,
//                            ),
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                        )
//                        PpsText(
//                            modifier = Modifier,
//                            text = stringResource(R.string.badge_info_sub_title),
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                color = CoolGray900,
//                            ),
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                        )
//                    }
//
//                    Image(
//                        modifier = Modifier.size(59.dp),
//                        painter = painterResource(id = com.seoulmate.ui.R.drawable.img_badge_title),
//                        contentDescription = "Badge Title",
//                        contentScale = ContentScale.Fit,
//                    )
//                }
//            }

            //
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .weight(1f),
                columns = GridCells.Adaptive(minSize = 105.dp),
            ) {
                items(
                    count = badgeItemList.size
                ) { index ->
                    BadgeItem(
                        badgeItemList[index],
                        viewModel.badgeList.value[index],
                    )
                }
            }
        }
    }
}

@Composable
private fun BadgeItem(
    item: BadgeData,
    countItem: ChallengeMyBadgeData?,
) {
    val totalCnt = if (countItem == null) 99 else countItem.themeCount
    val completeCnt = if (countItem == null) 0 else countItem.completeCount
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 6.dp)
            .width(105.dp).wrapContentHeight()
            .background(color = TrueWhite, RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(74.dp),
            painter = painterResource(if (totalCnt == completeCnt) item.selectedIcon else item.defaultIcon),
            contentDescription = "Badge Icon",
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(7.dp))
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(item.title),
            style = MaterialTheme.typography.labelLarge.copy(
                color = CoolGray900,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
        Row {
            PpsText(
                modifier = Modifier,
                text = completeCnt.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (totalCnt == completeCnt) Blue500 else CoolGray300,
                ),
            )
            PpsText(
                modifier = Modifier,
                text = " / $totalCnt",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray300,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

data class BadgeData(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val defaultIcon: Int,
    @StringRes val title: Int,
)