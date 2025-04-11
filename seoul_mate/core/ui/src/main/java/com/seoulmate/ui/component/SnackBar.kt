package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

enum class SnackBarType(name: String) {
    OnlyText("OnlyText"),
    CopyText("CopyText"),
}

@Composable
fun SnackBar(
    type: SnackBarType = SnackBarType.OnlyText,
    snackText: String,
    onCopyClick: () -> Unit = {},
) {

    when (type) {
        SnackBarType.OnlyText -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardColors(
                    contentColor = Color.White,
                    containerColor = Color.Gray.copy(alpha = 0.2f),
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray,
                ),
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = snackText,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        SnackBarType.CopyText -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 25.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardColors(
                    contentColor = Color.Black,
                    containerColor = Color.White,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.LightGray,
                ),
            ) {
                Row {
                    Text(
                        modifier = Modifier.padding(10.dp).weight(1f),
                        text = snackText,
                        textAlign = TextAlign.Left,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Button(
                        onClick = onCopyClick,
                    ) {
                        Text(text = "복사", style = TextStyle(color = Color.Blue))
                    }
                }
            }
        }
    }
}

fun String.snackBarMessage(): String {
    return try {
        if (this.contains(":::")) {
            this.split(":::")[1]
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }

}

fun String.snackBarType(): SnackBarType {
    return try {
        if (this.contains(":::")) {
            when(this.split(":::")[0]) {
                SnackBarType.OnlyText.name -> {
                    SnackBarType.OnlyText
                }
                SnackBarType.CopyText.name -> {
                    SnackBarType.CopyText
                }
                else -> {
                    SnackBarType.OnlyText
                }
            }

        } else {
            SnackBarType.OnlyText
        }
    } catch (e: Exception) {
        SnackBarType.OnlyText
    }
}