package com.seoulmate.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seoulmate.ui.component.Loading

@Composable
fun SplashScreen(
    onNext: () -> Unit,
    onClickLogin: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column {
            Button(
                onClick = onNext
            ) {
                Text(text = "Splash Screen")
            }

            Button(
                onClick = onClickLogin
            ) {
                Text(text = "Login Screen")
            }
        }
    }
}