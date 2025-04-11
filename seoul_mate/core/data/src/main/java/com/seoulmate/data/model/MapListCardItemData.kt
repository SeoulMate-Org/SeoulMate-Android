package com.seoulmate.data.model

data class MapListCardItemData(
    val title: String,
    val imgUrl: String,
    val address: String,
    val isFavorite: Boolean = false,
    val isOpened: Boolean? = null,
)