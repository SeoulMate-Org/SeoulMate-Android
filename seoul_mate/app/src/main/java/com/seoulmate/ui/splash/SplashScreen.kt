package com.seoulmate.ui.splash

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seoulmate.datastore.repository.Language
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.main.MainActivity
import com.seoulmate.ui.theme.Blue500
import java.util.Locale

@Composable
fun SplashScreen(
    context: Context,
    viewModel: SplashViewModel,
) {
    val rememberIsFirst = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // case : Enter First APP - Setting Init [First Enter APP Flag]
        if (viewModel.isFirstEnter.value) {
            rememberIsFirst.value = true
            viewModel.updateIsFirstEnter(false)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Blue500),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PpsButton(
            modifier = Modifier.wrapContentSize(),
            stringRes = com.seoulmate.ui.R.string.str_confirm
        ) {
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as SplashActivity).finish()
        }
    }
}