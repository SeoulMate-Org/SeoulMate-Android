package com.seoulmate.ui.onboarding

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.seoulmate.R
import com.seoulmate.ui.AppState
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.ColorOnBoarding
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray75
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.Red
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    appState: AppState,
    goLogin: () ->Unit = {},
) {
    val pagerState = rememberPagerState(
        pageCount = { 4 },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    Scaffold { paddding ->
        Column(
            modifier = Modifier.padding(paddding).fillMaxWidth()
        ) {
            HorizontalPager(
                modifier = Modifier
                    .weight(1f),
                state = pagerState,
                userScrollEnabled = true,
            ) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (index == 0) White else ColorOnBoarding),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val imgRes = when(index) {
                        0 -> R.drawable.img_onboarding_top_1
                        1 -> R.drawable.img_onboarding_top_2
                        2 -> R.drawable.img_onboarding_top_3
                        3 -> R.drawable.img_onboarding_top_4
                        else -> R.drawable.img_onboarding_top_1
                    }

                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = imgRes),
                        contentDescription = null,
                    )

                }
            }
            // Bottom
            Column(
                modifier = Modifier
                    .height(390.dp)
                    .fillMaxWidth()
                    .background(color = TrueWhite),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when(pagerState.currentPage) {
                        0 -> BottomOnBoarding0()
                        1 -> BottomOnBoarding1()
                        2 -> BottomOnBoarding2()
                        3 -> BottomOnBoarding3()
                        else -> BottomOnBoarding0()
                    }
                }
                // Indicator
                PagerIndicator(
                    pageCount = pagerState.pageCount,
                    currentPageIndex = pagerState.currentPage,
                )
                // Button
                Box(modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp, top = 24.dp)
                ) {
                    PpsButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),
                        stringRes = if(pagerState.currentPage == 3) R.string.str_start else R.string.str_next,
                        cornerRound = 12.dp
                    ) {
                        if (pagerState.currentPage == 3) {
                            goLogin()
                        } else {
                            appState.coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(pageCount: Int, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPageIndex == iteration) CoolGray700 else CoolGray75

            if (currentPageIndex == iteration) {
                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color)
                        .height(8.dp)
                        .width(16.dp)
                )
            } else {
                Box(
                    modifier = modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }

        }
    }
}

@Composable
fun BottomOnBoarding0() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.img_onboarding_1),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            PpsText(
                modifier = Modifier,
                text = stringResource(R.string.str_onboarding_inext_0_1),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900,
                )
            )
        }
        Image(
            modifier = Modifier.width(28.dp).height(16.dp).padding(top = 4.dp),
            painter = painterResource(id = R.drawable.img_onboarding_),
            contentDescription = null,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.img_onboarding_2),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            PpsText(
                modifier = Modifier,
                text = stringResource(R.string.str_onboarding_inext_0_2),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900,
                )
            )
        }
        Image(
            modifier = Modifier.width(28.dp).height(16.dp).padding(top = 4.dp),
            painter = painterResource(id = R.drawable.img_onboarding_),
            contentDescription = null,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.img_onboarding_3),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            PpsText(
                modifier = Modifier,
                text = stringResource(R.string.str_onboarding_inext_0_3),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900,
                )
            )
        }
    }
}

@Composable
fun BottomOnBoarding1() {
    Column {
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_1_title),
            style = MaterialTheme.typography.titleLarge.copy(
                color = CoolGray900,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_1_sub),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = CoolGray400,
            ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BottomOnBoarding2() {
    Column {
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_2_title),
            style = MaterialTheme.typography.titleLarge.copy(
                color = CoolGray900,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_2_sub),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = CoolGray400,
            ),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BottomOnBoarding3() {
    Column {
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_3_title),
            style = MaterialTheme.typography.titleLarge.copy(
                color = CoolGray900,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PpsText(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.str_onboarding_index_3_sub),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = CoolGray400,
            ),
            textAlign = TextAlign.Center,
        )
    }
}