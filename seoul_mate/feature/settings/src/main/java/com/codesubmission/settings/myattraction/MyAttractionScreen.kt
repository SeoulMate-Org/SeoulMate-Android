package com.codesubmission.settings.myattraction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.codesubmission.settings.R
import com.codesubmission.settings.myattraction.item.EmptyAttractionLayout
import com.seoulmate.ui.component.AttractionTileTypeLayout
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray300
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAttractionScreen(
    onBackClick: () -> Unit = {},
    onAttractionItemClick: (attractionId: Int) -> Unit = {},
) {
    val viewModel = hiltViewModel<MyAttractionViewModel>()

    LaunchedEffect(Unit) {
        viewModel.reqMyAttractionList()
    }

    LaunchedEffect(viewModel.needRefreshToken.value) {
        if (viewModel.needRefreshToken.value == true) {
            viewModel.refreshToken()
        } else if (viewModel.needRefreshToken.value == false) {
            viewModel.reqMyAttractionList()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.my_attraction_title),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        ),
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

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
                .background(color = TrueWhite)
                .padding(padding)
        ) {
            val (loading, list) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalArrangement = Arrangement.Top,
            ) {
                if (viewModel.myAttractionList.value.isNotEmpty()) {
                    // My Attraction List
                    item {
                        Spacer(modifier = Modifier.height(25.dp))
                        PpsText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            text = stringResource(
                                R.string.attraction_count,
                                viewModel.myAttractionList.value.size),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = CoolGray300,
                            ),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    itemsIndexed(
                        items = viewModel.myAttractionList.value,
                    ) { index, item ->
                        if (index > 0) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                                    .padding(top = 5.dp)
                                    .background(color = CoolGray25),
                            )
                        }

                        AttractionTileTypeLayout(
                            item = item,
                            onItemClick = onAttractionItemClick,
                            onItemLikeClick = { attractionId ->

                            }
                        )
                    }

                } else {
                    // My Attraction list is Empty
                    item {
                        EmptyAttractionLayout()
                    }
                }
            }

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