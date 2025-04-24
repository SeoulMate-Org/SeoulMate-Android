package com.seoulmate.ui.splash

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seoulmate.R
import com.seoulmate.datastore.repository.Language
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.main.MainActivity
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.SplashGradientEnd
import com.seoulmate.ui.theme.SplashGradientStart
import com.seoulmate.ui.theme.TrueWhite
import java.util.Locale

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
            painter = painterResource(R.drawable.img_splash_pop),
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
            LoadingIndicator(
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