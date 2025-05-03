package com.seoulmate.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.ColorBlueComment
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

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
                painter = painterResource(R.drawable.ic_comment),
                contentDescription = "User Icon",
                tint = ColorBlueComment,
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
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .noRippleClickable {  },
                painter = painterResource(R.drawable.ic_more),
                contentDescription = "More Icon",
                tint = CoolGray300,
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
            text = dateFormatString(item.createdAt),
            style = MaterialTheme.typography.labelLarge.copy(
                color = CoolGray300,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

fun convertToCustomFormat(input: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm")

    val dateTime = LocalDateTime.parse(input, inputFormatter)
    return dateTime.format(outputFormatter)
}

/** 날짜 형식변경 - ["yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"] -> ["yyyy-MM-dd HH:mm"] */
fun dateFormatString(date: String): String{
    try {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")   // 받은 데이터 형식
        //oldFormat.timeZone = TimeZone.getTimeZone("UTC")
        oldFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val oldDate = oldFormat.parse(date)

        val newFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN) // 바꿀 데이터 형식
        newFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")                 //ex) "2016-11-01T15:25"

        return newFormat.format(oldDate)
    } catch (e: Exception) {

    }

    return ""
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