package com.seoulmate.ui.component

sealed class ScreenGraph(val route: String) {
    data object HomeGraph: ScreenGraph("homeGraph")
    data object MapGraph: ScreenGraph("mapGraph")
    data object UserInterestsGraph: ScreenGraph("userInterestsGraph")
    data object LoginGraph: ScreenGraph("loginGraph")
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

    data object OnBoarding: Screen("onBoarding")
    data object MyPageOnBoarding: Screen("myPageOnBoarding")
    data object Login: Screen("login")
    data object Signin: Screen("signin")
    data object Signup: Screen("signup")
    data object Withdraw: Screen("withdraw")

    data object ChallengeCommentList: Screen("challengeReplyList")
    data object ChallengeDetail: Screen("challengeDetail")
    data object ChallengeThemeList: Screen("challengeThemeList")
    data object ChallengeRankList: Screen("challengeRankList")
    data object ChallengeStampComplete: Screen("challengeStampComplete")

    data object AttractionDetail: Screen("attractionDetail")
    data object MyAttraction: Screen("myAttraction")
    data object MyComment: Screen("myComment")

    data object SettingLanguage: Screen("settingLanguage")
    data object SettingNotification: Screen("settingNotification")
    data object SettingMyBadge: Screen("settingMyBadge")
    data object SettingUserNickname: Screen("settingUserNickname")
}
