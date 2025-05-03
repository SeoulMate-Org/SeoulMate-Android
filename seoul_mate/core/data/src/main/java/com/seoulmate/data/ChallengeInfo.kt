package com.seoulmate.data

import com.seoulmate.data.model.challenge.AttractionItem
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.data.model.challenge.ChallengeCulturalEventData
import com.seoulmate.data.model.challenge.ChallengeLocationData
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.data.model.challenge.ChallengeRankItemData
import com.seoulmate.data.model.challenge.ChallengeStampItemData
import com.seoulmate.data.model.challenge.ChallengeStampResponseData
import com.seoulmate.data.model.challenge.ChallengeThemeData
import com.seoulmate.data.model.challenge.ChallengeThemeItemData

object ChallengeInfo {
    var themeList = listOf(
        ChallengeThemeData(
            id = 1,
            nameKor = "지역 탐방",
            title = "Local Tour",
            descriptionKor = "특정 지역(자치구/동네)을 중심으로 구성된 투어",
            descriptionEng = "Tour centered around a specific area (district/neighborhood)"
        ),
        ChallengeThemeData(
            id = 2,
            nameKor = "문화 행사",
            title = "Cultural Events",
            descriptionKor = "특정 전시/축제/공연 참여 기반",
            descriptionEng = "aaabbbcccddd"
        ),
        ChallengeThemeData(
            id = 3,
            nameKor = "핵심 관광지 정복",
            title = "Must-See Spots",
            descriptionKor = "관광 필수 코스 조합",
            descriptionEng = "muikukhjghwdfsdfsfsdf"
        ),
        ChallengeThemeData(
            id = 4,
            nameKor = "포토 스팟",
            title = "Photo Spot",
            descriptionKor = "사진 중심의 SNS 인증형 코스",
            descriptionEng = "hhjrrghrghfghfhfghe"
        ),
        ChallengeThemeData(
            id = 5,
            nameKor = "도보 여행",
            title = "Walking Tour",
            descriptionKor = "이동 동선이 짧고 걷기 좋은 코스",
            descriptionEng = "ssggegsgsggddg"
        ),
        ChallengeThemeData(
            id = 6,
            nameKor = "야경 & 감성",
            title = "Night Views & Mood",
            descriptionKor = "야경 스폿, 산책길, 감성 여행",
            descriptionEng = "bbbbbffffbb"
        ),
        ChallengeThemeData(
            id = 7,
            nameKor = "미식 & 오래가게",
            title = "Foodie & Hidden Gems",
            descriptionKor = "전통시장/떡집/로컬 음식점 중심 체험",
            descriptionEng = "mnmnwwdfqojwdf"
        ),
        ChallengeThemeData(
            id = 8,
            nameKor = "전시 & 예술",
            title = "Exhibitions & Art",
            descriptionKor = "미술관, 전시, 예술 공간 순회",
            descriptionEng = "asdfipowpofpwdofsdfsd"
        ),
        ChallengeThemeData(
            id = 9,
            nameKor = "역사 & 문화",
            title = "History & Culture",
            descriptionKor = "고궁, 유적지, 박물관 중심의 역사 경험",
            descriptionEng = "pooiopqsdfbsd"
        ),
    )
    var challengeThemeList: List<List<ChallengeThemeItemData>> = listOf()
    var rankList: List<ChallengeRankItemData> = listOf()
    var challengeCulturalList: List<ChallengeCulturalEventData> = listOf()
    var challengeSeoulMasterList: List<ChallengeCulturalEventData> = listOf()
    var challengeStampData: ChallengeStampResponseData? = null
    var challengeLocationData: ChallengeLocationItemData? = null


    fun getChallengeStampList(): List<ChallengeStampItemData> {
        if (challengeStampData == null) {
            return listOf()
        } else {
            return challengeStampData!!.itemList
        }
    }

    fun setChallengeStampList(list: List<ChallengeStampItemData>) {
        challengeStampData?.let {
            challengeStampData = it.copy(
                itemList = list
            )
        }
    }

    fun getChallengeLocationList() : List<ChallengeLocationData> {
        if (challengeLocationData == null) {
            return listOf()
        } else {
            return challengeLocationData!!.challenges
        }
    }

    fun setChallengeLocationList(list: List<ChallengeLocationData>) {
        challengeLocationData?.let {
            challengeLocationData = it.copy(
                challenges = list
            )
        }
    }
}

object ChallengeDetailInfo {
    var id = 0
    var title =""
    var commentList = listOf<ChallengeCommentItem>()
    var attractions: List<AttractionItem> = listOf()
    var attractionDistance: List<Float?> = listOf()
}