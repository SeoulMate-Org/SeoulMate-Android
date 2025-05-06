package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seoulmate.ui.R
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun PpsCommentAlertDialog(
    onClickDelete: () -> Unit,
    onClickModify: () -> Unit,
    onClickCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardColors(
                containerColor = TrueWhite,
                contentColor = TrueWhite,
                disabledContainerColor = CoolGray25,
                disabledContentColor = CoolGray25,
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 15.dp,
                    ),
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp)
                        .noRippleClickable {
                            onClickModify()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.str_modify),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CoolGray900
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    thickness = 1.dp,
                    color = CoolGray25,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp)
                        .noRippleClickable {
                            onClickDelete()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.str_delete),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = CoolGray900
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }

            }
        }
    }
}