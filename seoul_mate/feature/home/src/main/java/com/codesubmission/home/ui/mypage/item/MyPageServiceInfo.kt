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
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.White

@Composable
fun MyPageServiceInfo(

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
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.my_page_faq),
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
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.my_page_terms_of_use),
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
            // Terms of Personal Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.my_page_terms_of_personal_info),
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
            // Terms of Location
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp,),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.my_page_terms_of_location),
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
        }
    }

}