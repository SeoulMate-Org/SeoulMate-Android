package com.seoulmate.data.model.request

import com.google.gson.Gson

data class MyLocationReqData(
    val locationX: Double,
    val locationY: Double,
    val radius: Int = 150,
    val limit: Int = 10,
)

fun MyLocationReqData.toJson(): String = Gson().toJson(this)
