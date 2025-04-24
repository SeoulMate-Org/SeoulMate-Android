package com.codesubmission.home.ui.mypage.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Blue400
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.White

@Composable
fun MyPageLoginInfo(
    isLogin: Boolean = false,
    nickname: String = "",
    loginType: String = "",
    onLoginClick: () -> Unit = {},
    onNickNameClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        // User ID
        Row(
            modifier = Modifier
                .noRippleClickable {
                    if (isLogin) {
                        onNickNameClick()
                    } else {
                        onLoginClick()
                    }
                }
        ) {
            PpsText(
                modifier = Modifier.weight(1f),
                text = if (isLogin) nickname
                else stringResource(R.string.my_page_need_login),
                style = TextStyle(
                    fontSize = 22.sp,
                    color = CoolGray900,
                ),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                    contentDescription = "Login"
                )
            }
        }
        // User Email
        if (isLogin) {
            // TODO chan
            when (loginType) {
                "GOOGLE" -> {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Blue400,
                                shape = RoundedCornerShape(10.dp),
                            )
                    ) {
                        PpsText(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 10.dp, vertical = 5.dp,),
                            text = loginType,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = White ,
                            ),
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .background(
                        color = Blue400,
                        shape = RoundedCornerShape(10.dp),
                    )
            ) {
                PpsText(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 10.dp, vertical = 5.dp,),
                    text = stringResource(R.string.my_page_need_login_info),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = White ,
                    ),
                )
            }
        }

    }
}

@Preview
@Composable
private fun PreviewMyPageLoginInfo() {
    SeoulMateTheme {
        MyPageLoginInfo()
    }
}