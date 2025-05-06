package com.codesubmission.home.ui.mypage.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.codesubmission.home.ui.HomeState
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.White

@Composable
fun MyPageServiceInfo(
    showWebUrl: (url: String) -> Unit = {},
) {
    Column {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            // Service info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.my_page_service_info),
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
            // FAQ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f)
                        .noRippleClickable {
                            val url = if (UserInfo.getLanguageCode() == "KOR") {
                                "https://early-palladium-c40.notion.site/1e3bde6bd69580c89725e7b2399baadb?pvs=4"
                            } else {
                                "https://early-palladium-c40.notion.site/Seoul-Tourism-FAQ-1e3bde6bd6958018b538c35bf630ca0b?pvs=4"
                            }
                            showWebUrl(url)
                        },
                    text = stringResource(R.string.my_page_faq),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900
                    )
                )
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val url = if (UserInfo.getLanguageCode() == "KOR") {
                            "https://early-palladium-c40.notion.site/1e3bde6bd69580c89725e7b2399baadb?pvs=4"
                        } else {
                            "https://early-palladium-c40.notion.site/Seoul-Tourism-FAQ-1e3bde6bd6958018b538c35bf630ca0b?pvs=4"
                        }
                        showWebUrl(url)
                    }
                ) {
                    Icon(
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                        contentDescription = "Setting Notification",
                    )
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .background(
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            // Terms of User
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f)
                        .noRippleClickable {
                            val url = if (UserInfo.getLanguageCode() == "KOR") {
                                "https://early-palladium-c40.notion.site/1e3bde6bd6958065a15fc07db4fb67d1"
                            } else {
                                "https://early-palladium-c40.notion.site/Terms-and-Conditions-of-Service-1e3bde6bd695809c8437f8d794829836?pvs=4"
                            }
                            showWebUrl(url)
                        },
                    text = stringResource(R.string.my_page_terms_of_use),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900
                    )
                )
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val url = if (UserInfo.getLanguageCode() == "KOR") {
                            "https://early-palladium-c40.notion.site/1e3bde6bd6958065a15fc07db4fb67d1"
                        } else {
                            "https://early-palladium-c40.notion.site/Terms-and-Conditions-of-Service-1e3bde6bd695809c8437f8d794829836?pvs=4"
                        }
                        showWebUrl(url)
                    }
                ) {
                    Icon(
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                        contentDescription = "Setting Notification",
                    )
                }
            }
            // Terms of Personal Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f)
                        .noRippleClickable {
                            val url = if (UserInfo.getLanguageCode() == "KOR") {
                                "https://early-palladium-c40.notion.site/1e3bde6bd69580acbecdd6e595e1f7b8?pvs=4"
                            } else {
                                "https://early-palladium-c40.notion.site/Privacy-Policy-1e3bde6bd69580bcb074c603910d8c55?pvs=4"
                            }
                            showWebUrl(url)
                        },
                    text = stringResource(R.string.my_page_terms_of_personal_info),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900
                    )
                )
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val url = if (UserInfo.getLanguageCode() == "KOR") {
                            "https://early-palladium-c40.notion.site/1e3bde6bd69580acbecdd6e595e1f7b8?pvs=4"
                        } else {
                            "https://early-palladium-c40.notion.site/Privacy-Policy-1e3bde6bd69580bcb074c603910d8c55?pvs=4"
                        }
                        showWebUrl(url)
                    }
                ) {
                    Icon(
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                        contentDescription = "Setting Notification",
                    )
                }
            }
            // Terms of Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f)
                        .noRippleClickable {
                            val url = if (UserInfo.getLanguageCode() == "KOR") {
                                "https://early-palladium-c40.notion.site/1e3bde6bd6958076ac0ac01e78dda837?pvs=4"
                            } else {
                                "https://early-palladium-c40.notion.site/Location-Information-Processing-Policy-1e3bde6bd69580609698fd3b570eb124?pvs=4"
                            }
                            showWebUrl(url)
                        },
                    text = stringResource(R.string.my_page_terms_of_location),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = CoolGray900
                    )
                )
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val url = if (UserInfo.getLanguageCode() == "KOR") {
                            "https://early-palladium-c40.notion.site/1e3bde6bd6958076ac0ac01e78dda837?pvs=4"
                        } else {
                            "https://early-palladium-c40.notion.site/Location-Information-Processing-Policy-1e3bde6bd69580609698fd3b570eb124?pvs=4"
                        }
                        showWebUrl(url)
                    }
                ) {
                    Icon(
                        painter = painterResource(com.seoulmate.ui.R.drawable.ic_arrow_right),
                        contentDescription = "Setting Notification",
                    )
                }
            }
        }
    }

}