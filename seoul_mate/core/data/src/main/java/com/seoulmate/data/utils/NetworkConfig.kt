package com.seoulmate.data.utils

object NetworkConfig {

    object NaverMapService {
        // Naver Geocoding
        const val GEOCODE = "map-geocode/v2/geocode"
        const val REVERSE_GEOCODE = "map-reversegeocode/v2/gc"
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
        const val CHALLENGE_LIST_RANK = "challenge/list/rank"
        const val CHALLENGE_LIST_CULTURAL_EVENT = "challenge/list/cultural-event"
        // Attraction
        const val ATTRACTION = "attraction"
        const val ATTRACTION_STAMP = "attraction/stamp"
        const val ATTRACTION_LIKE = "attraction/like"
        // Comment
        const val COMMENT = "comment"
        const val COMMENT_MY = "comment/my"
        // User
        const val USER_NICKNAME = "user/nickname"
    }
}