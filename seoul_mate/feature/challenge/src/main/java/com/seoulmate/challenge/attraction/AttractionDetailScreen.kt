package com.seoulmate.challenge.attraction

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.seoulmate.challenge.attraction.item.TopAttractionDetailInfo
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.ui.component.PpsAlertDialog
import com.seoulmate.ui.component.PpsLoading
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.noRippleClickable
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.Blue300
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.Red
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun AttractionDetailScreen(
    attractionId: Int,
    onBackClick: () -> Unit = {},
    onUrlClick: (url: String) -> Unit = {},
    onChangeScreen: (Screen) -> Unit = {},
    onCopyClick: (label: String, strCopy: String) -> Unit = { _, _ -> },
) {
    val viewModel = hiltViewModel<AttractionDetailViewModel>()
    var showLoginAlertDialog by remember { mutableStateOf(false) }
    var distance by remember { mutableStateOf<Float?>(null) }
    val seoul = LatLng(37.532600, 127.024612)

    var cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            seoul,
            14.0
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getAttractionDetailInfo(attractionId)

        run distance@ {
            ChallengeDetailInfo.attractions.forEachIndexed { index, attractionItem ->
                if (attractionItem.id == attractionId) {
                    distance = ChallengeDetailInfo.attractionDistance[index]
                    return@distance
                }
            }
        }

    }

    LaunchedEffect(viewModel.needUserLogin.value) {
        showLoginAlertDialog = viewModel.needUserLogin.value
    }

    LaunchedEffect(UserInfo.accessToken) {
        if (UserInfo.isUserLogin()) {
            showLoginAlertDialog = false
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = "",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
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
        },
        containerColor = TrueWhite
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TrueWhite)
                .padding(padding),
        ) {
            val (body, loading) = createRefs()

            LazyColumn (
                modifier = Modifier
                    .fillMaxHeight()
                    .constrainAs(body) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                viewModel.attractionItem.value?.let {

                    item {
                        TopAttractionDetailInfo(
                            item = it,
                            onItemLikeClick = { item ->
                                viewModel.reqAttractionLike(item.id)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        HorizontalDivider(
                            color = CoolGray25,
                            thickness = 1.dp,
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        // Address
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Top,
                            ) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_address),
                                    contentDescription = "Location Icon",
                                    tint = CoolGray400,
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    PpsText(
                                        modifier = Modifier
                                            .padding(vertical = 5.dp),
                                        text = it.address,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = CoolGray900,
                                        )
                                    )
                                    if (distance != null) {
                                        val km = String.format(
                                            "%.2f",
                                            (distance!! / 1000)
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Image(
                                                modifier = Modifier.size(24.dp),
                                                painter = painterResource(com.seoulmate.ui.R.drawable.ic_distance_blue),
                                                contentDescription = "Distance Icon",
                                                contentScale = ContentScale.Fit,
                                            )
                                            PpsText(
                                                modifier = Modifier,
                                                text = stringResource(
                                                    com.seoulmate.ui.R.string.distance_from_me, km
                                                ),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = CoolGray900,
                                                ),
                                            )
                                        }
                                    }
                                }
                                PpsText(
                                    modifier = Modifier.height(32.dp).wrapContentWidth()
                                        .noRippleClickable {
                                            onCopyClick(
                                                "copy address",
                                                it.address
                                            )
                                            // TODO chan show Toast
                                        },
                                    text = stringResource(com.seoulmate.ui.R.string.str_copy),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = Blue500,
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            // Homepage Url
                            if (it.homepageUrl.length > 1) {
                                Row(
                                    modifier = Modifier
                                        .noRippleClickable {
                                            onUrlClick(it.homepageUrl)
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_url),
                                        contentDescription = "Location Icon",
                                        tint = CoolGray400,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    PpsText(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 5.dp)
                                            .noRippleClickable {
                                                onUrlClick(it.homepageUrl)
                                            },
                                        text = it.homepageUrl,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Blue300,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            // Phone
                            if (it.tel.length > 1) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        modifier = Modifier.size(16.dp),
                                        painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_phone),
                                        contentDescription = "Location Icon",
                                        tint = CoolGray400,
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    PpsText(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(vertical = 5.dp),
                                        text = it.tel,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = CoolGray900,
                                        )
                                    )
                                    PpsText(
                                        modifier = Modifier.height(32.dp).wrapContentWidth()
                                            .noRippleClickable {
                                                onCopyClick(
                                                    "copy tel",
                                                    it.tel,
                                                )
                                                // TODO chan show Toast
                                            },
                                        text = stringResource(com.seoulmate.ui.R.string.str_copy),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Blue500,
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            // Subway
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_subway),
                                    contentDescription = "Location Icon",
                                    tint = CoolGray400,
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                PpsText(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 5.dp),
                                    text = it.subway,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = CoolGray900,
                                    )
                                )
                            }

                            //
                            Spacer(modifier = Modifier.height(24.dp))
                            cameraPositionState.position = CameraPosition(
                                LatLng((it.locationY).toDouble(), (it.locationX).toDouble()),
                                16.0
                            )
                            Box(
                                Modifier
                                    .fillMaxSize()
                            ) {
                                NaverMap(
                                    modifier = Modifier
                                        .width(340.dp)
                                        .height(250.dp),
                                    cameraPositionState = cameraPositionState,
                                    onMapClick = { point, LatLng ->
                                        Log.d("@@@@" , "point: $point, LatLng: $LatLng")
                                    },
                                    onLocationChange = { location ->
                                        Log.d("@@@@", "location: $location")
                                    }
                                ) {
                                    Marker(
                                        state = MarkerState(position = LatLng((it.locationY).toDouble(), (it.locationX).toDouble())),
                                        captionText = it.name,
                                        iconTintColor = Red,
                                    )
                                }
                            }
                        }
                    }

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
            // Login UI
            if(showLoginAlertDialog) {
                PpsAlertDialog(
                    titleRes = com.seoulmate.ui.R.string.str_need_login,
                    descriptionRes = com.seoulmate.ui.R.string.str_need_login_description,
                    confirmRes = com.seoulmate.ui.R.string.str_login,
                    cancelRes = com.seoulmate.ui.R.string.str_cancel,
                    onClickCancel = {
                        showLoginAlertDialog = false
                    },
                    onClickConfirm = {
                        onChangeScreen(Screen.Login)
                        showLoginAlertDialog = false
                    },
                )
            }
        }
    }

}