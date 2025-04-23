package com.codesubmission.home.ui.mypage.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray75
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.White

@Composable
fun MyPageActiveLog(
    cntBadge: Int = 0,
    cntFavorite: Int = 0,
    cntReply: Int = 0,
    onBadgeClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Badge
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .noRippleClickable {
                    onBadgeClick()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.my_page_badge),
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray400,
                ),
            )
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = if (cntBadge == 0) "-" else cntBadge.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Black,
                ),
            )
        }
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .padding(8.dp),
            color = CoolGray75,
        )
        // Favorite
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.my_page_favorite),
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray400,
                ),
            )
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = if (cntFavorite == 0) "-" else cntFavorite.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Black,
                ),
            )
        }
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .padding(8.dp),
            color = CoolGray75,
        )
        // Reply
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.my_page_reply),
                style = TextStyle(
                    fontSize = 13.sp,
                    color = CoolGray400,
                ),
            )
            PpsText(
                modifier = Modifier.wrapContentSize(),
                text = if (cntReply == 0) "-" else cntReply.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Black,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMyPageActiveLog() {
    SeoulMateTheme {
        MyPageActiveLog()
    }
}