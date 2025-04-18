package com.codesubmission.home.ui.map

import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codesubmission.home.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.seoulmate.ui.component.RoundedIconTag

// 맵 상단 태그 아이템 정의
sealed class MapTopTagData(
    @StringRes val titleRes: Int,
    @DrawableRes val drawableIconRes: Int,
    @ColorRes val colorRes: Int,
) {
    data object TrashcanTagData: MapTopTagData(
        titleRes = com.seoulmate.ui.R.string.top_map_tag_trashcan,
        drawableIconRes = R.drawable.ic_trashcan,
        colorRes = com.seoulmate.ui.R.color.orange,
    )
    data object WifiTagData: MapTopTagData(
        titleRes = com.seoulmate.ui.R.string.top_map_tag_wifi,
        drawableIconRes = R.drawable.ic_wifi,
        colorRes = com.seoulmate.ui.R.color.orange,
    )
    data object ToiletTagData: MapTopTagData(
        titleRes = com.seoulmate.ui.R.string.top_map_tag_toilet,
        drawableIconRes = R.drawable.ic_toilet,
        colorRes = com.seoulmate.ui.R.color.orange,
    )
}
// 맵 상단 태그 아이템 리스트 (쓰레기통, 와이파이, 화장실)
val mapTagList = listOf(
    MapTopTagData.TrashcanTagData,
    MapTopTagData.WifiTagData,
    MapTopTagData.ToiletTagData,
)

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeTravelMapScreen(
    expandBottomSheet: () -> Unit,
    onTagClick: (tagItem: MapTopTagData) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val seoul = LatLng(37.532600, 127.024612)
        val cameraPositionState: CameraPositionState = rememberCameraPositionState {
            // 카메라 초기 위치를 설정합니다.
            position = CameraPosition(seoul, 11.0)
        }
        ConstraintLayout {
            val (mapContent, topTagList) = createRefs()
            // 네이버 지도
            Box(
                Modifier
                    .fillMaxSize()
                    .constrainAs(mapContent) {}
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
                    Marker(
                        state = MarkerState(position = LatLng(37.566661, 126.978388)),
                        captionText = "서울 시청 테스트 \uD83D\uDCA9"
                    )
                }
                Button(onClick = {
                    // 카메라를 새로운 줌 레벨로 이동합니다.
//                cameraPositionState.move(CameraUpdate.zoomIn())
                    expandBottomSheet()
                }) {
                    Text(text = "Zoom In")
                }
            }
            // 상단 Tag List
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .constrainAs(topTagList) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                LazyRow (
                    Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(
                        items = mapTagList,
                        key = { index, item ->
                            item.titleRes
                        }
                    ) { index, item ->
                        RoundedIconTag(
                            titleRes = item.titleRes,
                            drawableIconRes = item.drawableIconRes,
                            colorRes = item.colorRes,
                        ) {
                            onTagClick(item)
                        }
                    }
                }
            }
        }

    }


}