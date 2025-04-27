package com.seoulmate.data.model.request

data class MyLocationReqData(
    val locationX: Double,
    val locationY: Double,
    val radius: Int = 150,
    val limit: Int = 10,
)
