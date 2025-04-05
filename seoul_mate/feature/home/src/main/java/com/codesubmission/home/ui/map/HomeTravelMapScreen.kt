package com.codesubmission.home.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeTravelMapScreen(
    expandBottomSheet: () -> Unit,
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
        Box(Modifier.fillMaxSize()) {
            NaverMap(cameraPositionState = cameraPositionState) {
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
    }


}