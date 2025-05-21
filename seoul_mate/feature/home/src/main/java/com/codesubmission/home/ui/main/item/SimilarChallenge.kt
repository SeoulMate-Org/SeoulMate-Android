package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.home.R
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.MainMissingChallengeGradientStart
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@Composable
fun SimilarChallenge(
    onItemClick: (challengeId: Int) -> Unit = {},
) {
    Column(
        modifier = Modifier,
    ) {
        // Title
        Row(
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                PpsText(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(R.string.possible_challenge_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = White,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                PpsText(
                    modifier = Modifier.padding(start = 20.dp, bottom = 16.dp, top = 4.dp),
                    text = stringResource(R.string.possible_challenge_sub_title),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray25,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Image(
                modifier = Modifier.size(138.dp),
                painter = painterResource(com.seoulmate.ui.R.drawable.ic_home_missing),
                contentDescription = "Main Top Title Image"
            )
        }
        // Item Row
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                items = ChallengeInfo.getChallengeSimilarList(),
                key = { index, item -> item.id },
            ) { index, item ->
                Row {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                        MissionChallengeItem(
                            item = item,
                            onItemClick = onItemClick,
                        )
                    }
                    if (index == ChallengeInfo.getChallengeSimilarList().lastIndex) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}