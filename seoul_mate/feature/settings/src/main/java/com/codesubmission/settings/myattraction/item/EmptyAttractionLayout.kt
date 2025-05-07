package com.codesubmission.settings.myattraction.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codesubmission.settings.R
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900

@Composable
fun EmptyAttractionLayout(

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 230.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_empty_challenge),
            contentDescription = "Empty Challenge Icon",
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(30.dp))
        PpsText(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.my_attraction_empty_title),
            style = MaterialTheme.typography.titleSmall.copy(
                color = CoolGray900,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(5.dp))
        PpsText(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.my_attraction_empty_sub_title),
            style = MaterialTheme.typography.bodySmall.copy(
                color = CoolGray400,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}