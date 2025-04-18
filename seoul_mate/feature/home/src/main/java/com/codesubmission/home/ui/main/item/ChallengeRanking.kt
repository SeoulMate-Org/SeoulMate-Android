package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeRankingTileTypeLayout
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun ChallengeRanking(
    modifier: Modifier,
    onMoreClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        // Title
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_challenge_ranking_title),
            fontSize = 20.sp,
            style = TextStyle(
                color = CoolGray900
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        // SubTitle + More Button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home_challenge_ranking_sub_title),
                fontSize = 16.sp,
                style = TextStyle(
                    color = CoolGray600
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            // More Button
            Button(
                onClick = onMoreClick,
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Text(
                    text = stringResource(com.seoulmate.ui.R.string.str_more),
                    fontSize = 13.sp,
                    style = TextStyle(
                        color = CoolGray400
                    ),
                )
            }
        }
    }
}