package com.seoulmate.places.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.places.ui.item.AttractionListBottomSheet
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.Red
import com.seoulmate.ui.theme.TrueWhite

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun PlaceInfoDetailScreen(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onBackClick: () -> Unit,
    expandBottomSheet: () -> Unit = {},
    onAttractionClick: (attractionId: Int) -> Unit = {},
    onCopyClick: (label: String, strCopy: String) -> Unit = { _, _ -> },
) {
    val viewModel = hiltViewModel<PlaceInfoDetailViewModel>()
    val markerList = ChallengeDetailInfo.attractions.map {
        LatLng((it.locationY ?: "0.0").toDouble(), (it.locationX ?: "0.0").toDouble())
    }
    val seoul = LatLng(37.532600, 127.024612)

    var cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            if(markerList.isNotEmpty()) markerList[0] else seoul,
            14.0
        )
    }

    var selectedPlace by remember { mutableStateOf<Int?>(null) }

    val backPressedState by remember { mutableStateOf(true) }

    BackHandler(enabled = backPressedState) {
        if(selectedPlace != null) {
            selectedPlace = null
        } else {
            onBackClick()
        }
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    PpsText(
                        modifier = Modifier.wrapContentSize(),
                        text = ChallengeDetailInfo.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = CoolGray900,
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if(selectedPlace != null) {
                                selectedPlace = null
                            } else {
                                onBackClick()
                            }
                        }
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
        containerColor = TrueWhite,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            AttractionListBottomSheet(
                itemList = ChallengeDetailInfo.attractions,
                selectedPlace = selectedPlace,
                onItemClick = { index ->
                    cameraPositionState.position = CameraPosition(
                        markerList[index],
                        16.0
                    )

                    selectedPlace = index
                },
                onDetailClick = { item ->
                    onAttractionClick(item.id)
                },
                onCopyClick = onCopyClick,
                onItemLikeClick = { attractionId ->

                }
            )
        },
        sheetContentColor = TrueWhite,
        sheetContainerColor = TrueWhite,
        sheetPeekHeight = 155.dp,
        snackbarHost = { }
    ) { padding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val (mapContent) = createRefs()
            Box(
                Modifier
                    .fillMaxSize()
                    .constrainAs(mapContent) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                NaverMap(
                    cameraPositionState = cameraPositionState,
                    onMapClick = { point, LatLng ->
                        Log.d("@@@@" , "point: $point, LatLng: $LatLng")
                    },
                    onLocationChange = { location ->
                        Log.d("@@@@", "location: $location")
                    }
                ) {
                    markerList.forEachIndexed { index, latLng ->
                        Marker(
                            state = MarkerState(position = latLng),
                            captionText = ChallengeDetailInfo.attractions[index].name ?: "",
                            iconTintColor = Red,
                        )
                    }

                }
            }
        }
    }

}
