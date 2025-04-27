package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seoulmate.data.model.ChallengeCommentItem
import com.seoulmate.ui.R
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
            modifier = Modifier.fillMaxWidth(),
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
                text = "NickName",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray700,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        // Reply Content
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = item.comment,
            style = TextStyle(
                fontSize = 14.sp,
                color = CoolGray900,
                lineHeight = 21.sp,
            )
        )
        // TimeStamp
        PpsText(
            modifier = Modifier.wrapContentSize(),
            text = item.createdAt,
            style = TextStyle(
                fontSize = 12.sp,
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