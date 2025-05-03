package com.codesubmission.home.ui.mypage.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.White

@Composable
fun MyPagePermission(
    onLanguageClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        // Language
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 10.dp)
                .noRippleClickable {
                    onLanguageClick()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PpsText(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.my_page_language),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900
                )
            )
            PpsText(
                modifier = Modifier.wrapContentWidth(),
                text = if(UserInfo.getLanguageCode() == "KOR") "한국어" else "English",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Blue500
                ),
            )
        }
        // Notification
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp,)
                .noRippleClickable {
                    onNotificationClick()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PpsText(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.my_page_notification),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CoolGray900
                )
            )
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                    contentDescription = "Setting Notification",
                )
            }
        }
        // Location
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(modifier = Modifier.weight(1f)) {
                PpsText(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(R.string.my_page_location),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900
                    )
                )
                PpsText(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = stringResource(R.string.my_page_select),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray400,
                    )
                )
            }
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                    contentDescription = "Setting Notification",
                )
            }
        }
    }
}