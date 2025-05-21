package com.codesubmission.settings.badge

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.seoulmate.data.model.challenge.ChallengeMyBadgeData
import com.seoulmate.ui.component.PpsLoading
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
        com.seoulmate.ui.R.drawable.ic_theme_09,
        com.seoulmate.ui.R.drawable.ic_theme_01,
        com.seoulmate.ui.R.drawable.ic_theme_02,
        com.seoulmate.ui.R.drawable.ic_theme_03,
        com.seoulmate.ui.R.drawable.ic_theme_04,
        com.seoulmate.ui.R.drawable.ic_theme_05,
        com.seoulmate.ui.R.drawable.ic_theme_06,
        com.seoulmate.ui.R.drawable.ic_theme_07,
        com.seoulmate.ui.R.drawable.ic_theme_08,
    )

    val defaultIconList = listOf(
        com.seoulmate.ui.R.drawable.ic_theme_09_default,
        com.seoulmate.ui.R.drawable.ic_theme_01_default,
        com.seoulmate.ui.R.drawable.ic_theme_02_default,
        com.seoulmate.ui.R.drawable.ic_theme_03_default,
        com.seoulmate.ui.R.drawable.ic_theme_04_default,
        com.seoulmate.ui.R.drawable.ic_theme_05_default,
        com.seoulmate.ui.R.drawable.ic_theme_06_default,
        com.seoulmate.ui.R.drawable.ic_theme_07_default,
        com.seoulmate.ui.R.drawable.ic_theme_08_default,
    )

    val badgeItemList = mutableListOf<BadgeData>()

    selectedIconList.forEachIndexed { index, item ->
        badgeItemList.add(
            BadgeData(
                index = index,
                selectedIcon = item,
                defaultIcon = defaultIconList[index]
            )
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

        ConstraintLayout (
            modifier = Modifier
                .fillMaxSize()
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            val (loading, body) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = CoolGray25)
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {
                item {
                    Box (
                        modifier = Modifier
                            .heightIn(min = 80.dp)
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(shape = RoundedCornerShape(16.dp), color = TrueWhite)
                                .padding(15.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                PpsText(
                                    modifier = Modifier,
                                    text = stringResource(R.string.badge_info_title),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = CoolGray500,
                                        fontWeight = FontWeight.Medium,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                PpsText(
                                    modifier = Modifier,
                                    text = stringResource(R.string.badge_info_sub_title),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = CoolGray900,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }

                            Image(
                                modifier = Modifier.size(59.dp),
                                painter = painterResource(id = com.seoulmate.ui.R.drawable.img_badge_title),
                                contentDescription = "Badge Title",
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }

                if (viewModel.badgeList.value.size > 0) {
                    items(
                        items = viewModel.badgeList.value.chunked(3)
                    ) { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        ) {
                            rowItems.forEach { badgeItem ->
                                badgeItem?.let {
                                    Box(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        BadgeItem(
                                            badgeItemList[it.themeId -1],
                                            it,
                                        )
                                    }
                                }

                            }

                        }
                    }
                }

            }

            if (viewModel.isShowLoading.value) {
                PpsLoading(
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(loading) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )
            }
        }

    }
}

@Composable
private fun BadgeItem(
    item: BadgeData,
    countItem: ChallengeMyBadgeData,
) {
    Column(
        modifier = Modifier
            .background(color = TrueWhite, RoundedCornerShape(16.dp))
            .padding(horizontal = 5.dp, vertical = 6.dp)
            .widthIn(min = 105.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(74.dp),
            painter = painterResource(if (countItem.completeCount > 0) item.selectedIcon else item.defaultIcon),
            contentDescription = "Badge Icon",
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(7.dp))
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = countItem.themeName,
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
                text = countItem.completeCount.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (countItem.completeCount > 0) Blue500 else CoolGray300,
                ),
            )
            PpsText(
                modifier = Modifier,
                text = " / ${countItem.themeCount}",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray300,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

data class BadgeData(
    val index: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val defaultIcon: Int,
)