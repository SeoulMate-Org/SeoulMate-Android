package com.codesubmission.home.ui.mypage.item

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
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
import com.seoulmate.ui.theme.TrueWhite
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
                style = MaterialTheme.typography.titleLarge.copy(
                    color = CoolGray900
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {
                    if (isLogin) {
                        onNickNameClick()
                    } else {
                        onLoginClick()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                    contentDescription = "Login"
                )
            }
        }
        // User Email
        if (isLogin) {
            when (loginType) {
                "GOOGLE" -> {
                    Image(
                        painterResource(com.seoulmate.ui.R.drawable.ic_label_google),
                        contentDescription = "Google"
                    )
                }
                "FACKBOOK" -> {
                    Image(
                        painterResource(com.seoulmate.ui.R.drawable.ic_label_facebook),
                        contentDescription = "Google"
                    )
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
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TrueWhite,
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