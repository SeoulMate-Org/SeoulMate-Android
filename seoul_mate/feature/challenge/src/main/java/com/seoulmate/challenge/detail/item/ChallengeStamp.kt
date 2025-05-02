package com.seoulmate.challenge.detail.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.challenge.R
import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.ui.component.LinearStampIndicator
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.StampIndicator
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.CoolGray75
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme

@Composable
fun ChallengeStamp(
    attractions: List<AttractionItem> = listOf()
) {
    val totalAttractionItemCount = attractions.size
    val stampedAttractionItemCount = attractions.count { it.isStamped }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp),
    ) {
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.stamp_title),
            style = MaterialTheme.typography.titleSmall.copy(
                color = CoolGray900,
            )
        )
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.stamp_info),
            style = MaterialTheme.typography.labelLarge.copy(
                color = CoolGray400,
            )
        )
        // Stamp Progress
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = CoolGray25)
                .padding(15.dp),
        ) {
            Column {
                // Linear Indicator
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    // Indicator
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        for (index in 0 until totalAttractionItemCount) {
                            LinearStampIndicator(
                                modifier = Modifier.weight(1f),
                                isCompleted = index < stampedAttractionItemCount,
                            )
                            if (index > 0) {
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                    // Indicator Count
                    Spacer(modifier = Modifier.width(10.dp))
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = "${stampedAttractionItemCount}/${totalAttractionItemCount}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGray500,
                        )
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(top = 15.dp),
                    color = CoolGray75,
                    thickness = 1.dp,
                )
                // Stamp Indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (index in 0 until totalAttractionItemCount) {
                        StampIndicator(
                            isCompleted = index < stampedAttractionItemCount,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChallengeStaop() {
    SeoulMateTheme {
        ChallengeStamp()
    }
}

