package com.codesubmission.settings.withdraw.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seoulmate.ui.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun WithdrawDialog(
    onClickCancel: () -> Unit,
    onClickWithdraw: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    Dialog(
        onDismissRequest = onClickCancel,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
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
                // Title
                PpsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(com.codesubmission.settings.R.string.withdraw_dialog_title),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = CoolGray900,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Description
                PpsText(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(com.codesubmission.settings.R.string.withdraw_dialog_sub_title),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = CoolGray600,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Button Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PpsButton(
                        modifier = Modifier.weight(1f),
                        stringRes = R.string.str_cancel,
                        color = CoolGray50,
                        borderColor = CoolGray50,
                        fontColor = Black,
                        onClick = onClickCancel,
                        cornerRound = 15.dp,
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    PpsButton(
                        modifier = Modifier.weight(1f),
                        stringRes = com.codesubmission.settings.R.string.str_withdraw,
                        onClick = onClickWithdraw,
                        cornerRound = 15.dp,
                    )
                }

            }
        }
    }
}