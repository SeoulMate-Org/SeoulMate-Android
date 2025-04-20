package com.codesubmission.settings.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.settings.R
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingLanguageScreen(
    onBackClick: () -> Unit = {},
    onCompleteClick: () -> Unit = {},
) {
    val languageList = listOf(
        R.string.language_ko,
        R.string.language_en,
    )
    val (selectedOption, onOptionSelected)
    = remember { mutableStateOf(languageList[0]) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.language_title),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = CoolGray900,
                        ),
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Search Icon",
                            tint = CoolGray900,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrueWhite,
                    titleContentColor = TrueWhite,
                    navigationIconContentColor = TrueWhite,
                )
            )
        }
    ) { padding ->
        // Select Language
        Column(
            modifier = Modifier
                .padding(padding)
                .background(color = TrueWhite),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .weight(1f),
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.setting_select_language),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = CoolGray900,
                    ),
                )
                Column {
                    languageList.forEach {
                        val selected = it == selectedOption
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = { onOptionSelected(it) },
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id =
                                        if (selected) com.seoulmate.ui.R.drawable.ic_radio_btn
                                    else com.seoulmate.ui.R.drawable.ic_radio_btn_default),
                                    contentDescription = "SelectLanguage Button ",
                                    tint = if (selected) Blue500 else CoolGray400,
                                )
                            }

                            PpsText(
                                modifier = Modifier
                                    .padding(horizontal = 15.dp),
                                text = stringResource(it),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = CoolGray900,
                                ),
                            )
                        }
                    }
                }
            }
            // Complete Button
            PpsButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                stringRes = com.seoulmate.ui.R.string.str_complete,
            ) {
                onCompleteClick()
            }
        }
    }
}