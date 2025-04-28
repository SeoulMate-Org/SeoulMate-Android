package com.seoulmate.data.utils

object NetworkConfig {

    object NaverMapService {
        // Naver Geocoding
        const val GEOCODE = "map-geocode/v2/geocode"
    }

    object Service {
        // Auth
        const val LOGIN = "auth/login"
        const val REFRESH = "auth/refresh"
        // Challenge
        const val CHALLENGE = "challenge"
        const val CHALLENGE_MY = "challenge/my"
        const val CHALLENGE_THEME = "challenge/theme"
        const val CHALLENGE_STATUS = "challenge/status"
        const val CHALLENGE_LIKE = "challenge/like"
        const val CHALLENGE_LIST_LOCATION = "challenge/list/location"
        const val CHALLENGE_LIST_STAMP = "challenge/list/stamp"
        const val CHALLENGE_LIST_THEME = "challenge/list/theme"
        // Attraction
        const val ATTRACTION_STAMP = "attraction/stamp"
        // Comment
        const val COMMENT = "comment"
        const val COMMENT_MY = "comment/my"
    }
}