package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.ui.R
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme

@Composable
fun ChallengeCommentItemLayout(
    modifier: Modifier = Modifier.fillMaxWidth(),
    item: ChallengeCommentItem,
) {
    Column(
        modifier = modifier,
    ) {
        // User Info
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_bottom_nav_fill_favorite),
                contentDescription = "User Icon",
            )
            PpsText(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = item.nickname,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = CoolGray700
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        // Comment Content
        PpsText(
            modifier = Modifier.wrapContentSize()
                .padding(top = 6.dp),
            text = item.comment,
            style = MaterialTheme.typography.bodySmall.copy(
                color = CoolGray900,
            )
        )
        // TimeStamp
        PpsText(
            modifier = Modifier.wrapContentSize()
                .padding(top = 3.dp),
            text = item.createdAt,
            style = MaterialTheme.typography.labelLarge.copy(
                color = CoolGray300,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewChallengeReplyItemLayout() {
    SeoulMateTheme {
        ChallengeCommentItemLayout(
            item = ChallengeCommentItem(
                id = 0,
                comment = "Lorem ipsum dolor sit amet consectetur. Ipsum sdv semper nisl bibendum dolor pretium commodo.",
                createdAt = "2025.04.04 11:00",
                modifiedAt = "2025.04.04 11:00",
            ),
        )
    }
}