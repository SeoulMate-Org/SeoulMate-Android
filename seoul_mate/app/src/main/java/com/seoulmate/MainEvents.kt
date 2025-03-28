package com.seoulmate

sealed class MainEvents {
    data class GetAddresses(
        val query: String,
    ): MainEvents()
}