package com.seoulmate.places.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.component.SnackBar
import com.seoulmate.ui.component.snackBarMessage
import com.seoulmate.ui.component.snackBarType
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.TrueWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun PlaceInfoDetailScreen(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onBackClick: () -> Unit,
    expandBottomSheet: () -> Unit = {},
) {
    val viewModel = hiltViewModel<PlaceInfoDetailViewModel>()
    var markerList = listOf<LatLng>()
    val seoul = LatLng(37.532600, 127.024612)

    LaunchedEffect(Unit) {
        markerList = ChallengeDetailInfo.attractions.map {
            LatLng((it.locationY ?: "0.0").toDouble(), (it.locationX ?: "0.0").toDouble())
        }

        if (markerList.isNotEmpty()) {
            expandBottomSheet()
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
        containerColor = Color.Transparent,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            AttractionListBottomSheet()
        },
        sheetPeekHeight = 60.dp,
        snackbarHost = { }
    ) { padding ->

        val cameraPositionState: CameraPositionState = rememberCameraPositionState {
            position = CameraPosition(
                if(markerList.isNotEmpty()) markerList[0] else seoul,
                12.0
            )
        }

        ConstraintLayout(
            modifier = Modifier.fillMaxSize().padding(padding)
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
                            captionText = ChallengeDetailInfo.attractions[index].name ?: ""
                        )
                    }

                }
            }
        }
    }

}