package com.codesubmission.interest.interests_theme

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserInterestsThemeScreen(
    onNext: () -> Unit,
) {
    Scaffold { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onNext,
                ) {
                    Text(text = "BACK")
                }
                Button(
                    onClick = onNext,
                ) {
                    Text(text = "TOGETHER")
                }
            }
        }
    }

}