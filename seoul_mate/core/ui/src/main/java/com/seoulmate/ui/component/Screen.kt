package com.seoulmate.ui.component

sealed class ScreenGraph(val route: String) {
    data object HomeGraph: ScreenGraph("homeGraph")
    data object MapGraph: ScreenGraph("mapGraph")
    data object UserInterestsGraph: ScreenGraph("userInterestsGraph")
}

sealed class Screen(val route: String) {
    data object Splash: Screen("splash")

    data object UserInterestsTheme: Screen("userInterestsTheme")
    data object UserInterestsTogether: Screen("userInterestsTogether")
    data object SuccessUserInterests: Screen("successUserInterests")

    data object Home: Screen("home")
    data object HomeMain: Screen("homeMain")
    data object HomeChallenge: Screen("homeChallenge")
    data object HomeMyPage: Screen("homeMyPage")


    data object PlaceInfoDetail: Screen("placeInfoDetail")

    data object MapDefault: Screen("mapDefault")

    data object Login: Screen("login")

    data object ChallengeReplyList: Screen("challengeReplyList")
    data object ChallengeDetail: Screen("challengeDetail")

    data object SettingLanguage: Screen("settingLanguage")
    data object SettingNotification: Screen("settingNotification")
}
