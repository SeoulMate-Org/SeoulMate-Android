package com.codesubmission.settings.mycomment.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.settings.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun EmptyMyCommentLayout(
    onChallengeMoreClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 230.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PpsText(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.my_comment_empty_title),
            style = MaterialTheme.typography.titleSmall.copy(
                color = CoolGray900,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PpsText(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.my_comment_empty_sub_title),
            style = MaterialTheme.typography.bodySmall.copy(
                color = CoolGray300,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(35.dp))
        PpsButton(
            modifier = Modifier.height(38.dp).wrapContentWidth(),
            stringRes = R.string.my_comment_find_challenge,
            onClick = onChallengeMoreClick,
            cornerRound = 10.dp
        )
    }
}