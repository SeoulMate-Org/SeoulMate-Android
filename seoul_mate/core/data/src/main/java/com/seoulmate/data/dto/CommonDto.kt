package com.seoulmate.data.dto

data class CommonDto<T> (
    val code: Int,
    val message: String?,
    val response: T?,
)
