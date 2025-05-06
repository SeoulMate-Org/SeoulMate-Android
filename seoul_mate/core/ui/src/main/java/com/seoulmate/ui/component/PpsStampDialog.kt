package com.seoulmate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.Color0F1721
import com.seoulmate.ui.theme.CoolGray200
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun PpsStampDialog(
    strLocation: String,
    onClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
    ) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardColors(
                containerColor = Color0F1721,
                contentColor = Color0F1721,
                disabledContainerColor = Color0F1721,
                disabledContentColor = Color0F1721,
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.str_stamp_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = TrueWhite
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                PpsText(
                    modifier = Modifier,
                    text = "{ $strLocation }",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray200
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier
                        .width(280.dp)
                        .height(200.dp),
                    painter = painterResource(R.drawable.img_stamp),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.height(16.dp))
                PpsButton(
                    modifier = Modifier
                        .height(51.dp)
                        .fillMaxWidth(),
                    stringRes = R.string.str_confirm,
                    cornerRound = 15.dp,
                    onClick = onClick,
                )
            }
        }
    }
}