package com.seoulmate.ui.splash

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.seoulmate.R
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.theme.SplashGradientEnd
import com.seoulmate.ui.theme.SplashGradientStart

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SplashScreen(
    context: Context,
    viewModel: SplashViewModel,
    isFirst: Boolean,
    moveMain: () -> Unit = {},
    moveLogin: () -> Unit = {},
) {

    LaunchedEffect(viewModel.isShowLoading.value) {
        if (viewModel.isShowLoading.value == false) {
            if (isFirst) {
                // case : Enter First APP - Setting Init [First Enter APP Flag]
                moveLogin()
            } else {
                moveMain()
            }
        }
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if(viewModel.needRefreshToken.value == false) {
            viewModel.reqInit()
        }

    }

    LaunchedEffect(viewModel.finishedFetchUserInfo.value) {
        if (viewModel.finishedFetchUserInfo.value) {
            viewModel.reqHomeChallengeItems()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(listOf(SplashGradientStart, SplashGradientEnd)),
                shape = RectangleShape,
                alpha = 1f,
            )
    ) {
        val (bottomImg, titleImg, loading) = createRefs()

        Image(
            modifier = Modifier
                .width(610.dp)
                .height(559.dp)
                .constrainAs(titleImg) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top, margin = 75.dp)
            },
            painter = painterResource(R.drawable.img_splash_pop_cut),
            contentDescription = "Splash Image",
        )

        Image(
            modifier = Modifier.constrainAs(bottomImg) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            painter = painterResource(R.drawable.img_splash_bottom),
            contentDescription = "Splash Image",
        )

        if (viewModel.isShowLoading.value == true) {
            PpsLoading(
                modifier = Modifier.wrapContentSize()
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