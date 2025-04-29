package com.seoulmate.challenge.attraction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.seoulmate.challenge.attraction.item.TopAttractionDetailInfo
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionDetailScreen(
    attractionId: Int,
    onBackClick: () -> Unit = {},
) {
    val viewModel = hiltViewModel<AttractionDetailViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getAttractionDetailInfo(attractionId)
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = "",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = CoolGray900,
                        ),
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Back Icon",
                            tint = Black,
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = TrueWhite,
                    scrolledContainerColor = TrueWhite,
                    navigationIconContentColor = CoolGray900,
                    titleContentColor = CoolGray900,
                    actionIconContentColor = CoolGray900,
                    subtitleContentColor = CoolGray900,
                )
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            val (body, loading) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                viewModel.attractionItem.value?.let {
                    TopAttractionDetailInfo(
                        item = it,
                    )
                }
            }
            // Loading UI
            if (viewModel.isShowLoading.value) {
                PpsLoading(
                    modifier = Modifier.wrapContentSize()
                        .constrainAs(loading) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                )
            }
        }
    }

}