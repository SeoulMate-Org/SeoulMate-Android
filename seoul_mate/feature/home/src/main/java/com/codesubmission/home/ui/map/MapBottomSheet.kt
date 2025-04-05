package com.codesubmission.home.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetContent(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("This is a custom Bottom Sheet")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Handle Action */ }) {
            Text("Click Me")
        }
        Spacer(modifier = Modifier.height(500.dp))

    }
}