package com.seoulmate.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

/**
 * Common AlertDialog
 */
@Composable
fun PpsAlertDialog(
    containerWidth: Dp = 320.dp,
    @StringRes titleRes: Int,
    @StringRes descriptionRes: Int,
    @StringRes confirmRes: Int = R.string.str_confirm,
    @StringRes cancelRes: Int? = null,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
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
                .padding(10.dp)
                .width(containerWidth)
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
                verticalArrangement = Arrangement.Center,
            ) {
                // Title
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(titleRes),
                    fontSize = 20.sp,
                    style = TextStyle(
                        color = CoolGray900
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Description
                Text(
                    text = stringResource(descriptionRes),
                    fontSize = 16.sp,
                    style = TextStyle(
                        color = CoolGray600
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Button Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (cancelRes != null) {
                        PpsButton(
                            modifier = Modifier.weight(1f),
                            stringRes = cancelRes,
                            color = CoolGray50,
                            fontColor = Black,
                            onClick = onClickCancel,
                        )
                    }

                    PpsButton(
                        modifier = Modifier.weight(1f),
                        stringRes = confirmRes,
                        onClick = onClickConfirm,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun PreviewPpsAlertDialog() {
    SeoulMateTheme {
        PpsAlertDialog(
            titleRes = R.string.str_need_login,
            descriptionRes = R.string.str_need_login_description,
            confirmRes = R.string.str_login,
            cancelRes = R.string.str_cancel,
            onClickCancel = {},
            onClickConfirm = {},
        )
    }
}