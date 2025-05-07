package com.codesubmission.settings.withdraw.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.settings.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun WithdrawConsiderationInfoLayout(

) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        PpsText(
            modifier = Modifier,
            text = stringResource(R.string.withdraw_consideration_title),
            style = MaterialTheme.typography.titleMedium.copy(
                color = CoolGray900,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = CoolGray50
                )
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                PpsText(
                    modifier = Modifier,
                    text = ". ",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                )
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = "가나다라마바사아자차카타파하 기니디리미비시이지치키티피히 하하호호 하하하하하 호호호 gkgkgkgkgkgkgkgkg ghghghghgk",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                PpsText(
                    modifier = Modifier,
                    text = ". ",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                )
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = "가나다라마바사아자차카타파하 기니디리미비시이지치키티피히 하하호호 하하하하하 호호호 gkgkgkgkgkgkgkgkg ghghghghgk",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                PpsText(
                    modifier = Modifier,
                    text = ". ",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                )
                PpsText(
                    modifier = Modifier.weight(1f),
                    text = "가나다라마바사아자차카타파하 기니디리미비시이지치키티피히 하하호호 하하하하하 호호호 gkgkgkgkgkgkgkgkg ghghghghgk",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = CoolGray900,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}