package com.codesubmission.home.ui.mypage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seoulmate.ui.component.SnackBarType

@Composable
fun HomeMyPageScreen(
    showSnackBar: (SnackBarType, String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.scrim
    ) {
        Button(
            onClick = {
                showSnackBar(
                    SnackBarType.CopyText,
                    "HomeSuggestThemeScreen!!\nSnackBar 테스트 SnackBar 테스트",
                )
            }
        ) {
            Text(text = "HomeSuggestThemeScreen")
        }
    }
}