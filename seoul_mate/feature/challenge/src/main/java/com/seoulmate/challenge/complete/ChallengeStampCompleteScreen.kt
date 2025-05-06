package com.seoulmate.challenge.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seoulmate.challenge.R
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.ChallengeInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun ChallengeStampCompleteScreen(
    onBackClick: () -> Unit = {},
) {
    val iconList = listOf(
        com.seoulmate.ui.R.drawable.ic_theme_01,
        com.seoulmate.ui.R.drawable.ic_theme_02,
        com.seoulmate.ui.R.drawable.ic_theme_03,
        com.seoulmate.ui.R.drawable.ic_theme_04,
        com.seoulmate.ui.R.drawable.ic_theme_05,
        com.seoulmate.ui.R.drawable.ic_theme_06,
        com.seoulmate.ui.R.drawable.ic_theme_07,
        com.seoulmate.ui.R.drawable.ic_theme_08,
        com.seoulmate.ui.R.drawable.ic_theme_09,
    )

    val strTheme = if(UserInfo.getLanguageCode() == "KOR") {
        stringResource(
            R.string.complete_stamp_sub_title,
            ChallengeInfo.themeList[
                (ChallengeDetailInfo.completedStampThemeId ?: 1) - 1
            ].nameKor )
    } else {
        stringResource(
            R.string.complete_stamp_sub_title,
            ChallengeInfo.themeList[
                (ChallengeDetailInfo.completedStampThemeId ?: 1) - 1
            ].title )
    }

    Scaffold(
        modifier = Modifier.background(color = TrueWhite)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Badge Image
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                var themeId = ChallengeDetailInfo.completedStampThemeId ?: 1
                if (themeId < 1)
                    themeId = 1
                Image(
                    modifier = Modifier.size(305.dp),
                    painter = painterResource(
                        iconList[themeId - 1]
                    ),
                    contentDescription = "Challenge Stamp Complete",
                    contentScale = ContentScale.Fit,
                )
            }
            //
            Column {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.complete_stamp_title),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = CoolGray900,
                    )
                )
                PpsText(
                    modifier = Modifier,
                    text = stringResource(
                        R.string.complete_stamp_sub_title,
                        strTheme
                    ),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray400,
                    )
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            //
            PpsButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .padding(horizontal = 20.dp),
                stringRes = com.seoulmate.ui.R.string.str_confirm
            ) {
                onBackClick()
            }
        }
    }
}