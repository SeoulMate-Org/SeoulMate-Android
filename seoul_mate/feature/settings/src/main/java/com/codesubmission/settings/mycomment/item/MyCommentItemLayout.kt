package com.codesubmission.settings.mycomment.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.ui.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.ColorBlueComment
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray700
import com.seoulmate.ui.theme.CoolGray900
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun MyCommentItemLayout(
    modifier: Modifier = Modifier.fillMaxWidth(),
    item: ChallengeCommentItem,
    onDeleteClick: (item: ChallengeCommentItem) -> Unit = {},
) {
    var dropDownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        // User Info
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            val (body, dropdown) = createRefs()

            Row(
                modifier = Modifier.fillMaxWidth()
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
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
                if(item.isMine) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .noRippleClickable {
                                dropDownExpanded = true
                            },
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = "More Icon",
                        tint = CoolGray300,
                    )

                    DropdownMenu(
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                PpsText(
                                    modifier = Modifier,
                                    text = stringResource(R.string.str_delete),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = CoolGray900,
                                    )
                                )
                            },
                            onClick = {
                                dropDownExpanded = false
                                onDeleteClick(item)
                            }
                        )
                    }
                }
            }


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