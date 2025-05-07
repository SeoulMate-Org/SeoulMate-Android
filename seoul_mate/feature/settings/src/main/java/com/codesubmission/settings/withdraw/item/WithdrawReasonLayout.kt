package com.codesubmission.settings.withdraw.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.settings.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun WithdrawReasonLayout(

) {
    val reasonRadioOptions = listOf(
        stringResource(R.string.withdraw_reason_1),
        stringResource(R.string.withdraw_reason_2),
        stringResource(R.string.withdraw_reason_3),
        stringResource(R.string.withdraw_reason_4),
        stringResource(R.string.withdraw_reason_5),
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(reasonRadioOptions[0]) }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        PpsText(
            modifier = Modifier,
            text = stringResource(R.string.withdraw_reason_title),
            style = MaterialTheme.typography.titleSmall.copy(
                color = CoolGray900,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            reasonRadioOptions.forEach { title ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .selectable(
                            selected = (title == selectedOption),
                            onClick = { onOptionSelected(title) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id =
                        if (title == selectedOption) com.seoulmate.ui.R.drawable.ic_radio_btn
                        else com.seoulmate.ui.R.drawable.ic_radio_btn_default),
                        contentDescription = "SelectLanguage Button",
                        tint = if (title == selectedOption) Blue500 else CoolGray400,
                    )
                    PpsText(
                        modifier = Modifier.padding(start = 16.dp),
                        text = title,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CoolGray900
                        ),
                    )
                }
            }
        }
    }
}