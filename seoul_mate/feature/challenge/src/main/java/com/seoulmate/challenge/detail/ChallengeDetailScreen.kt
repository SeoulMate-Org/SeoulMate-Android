package com.seoulmate.challenge.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.seoulmate.challenge.R
import com.seoulmate.challenge.detail.item.BottomChallengeDetail
import com.seoulmate.challenge.detail.item.ChallengeStamp
import com.seoulmate.challenge.detail.item.TopChallengeDetailInfo
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ReplyItemData
import com.seoulmate.ui.component.ChallengeReplyItemLayout
import com.seoulmate.ui.component.ChallengeTileTypeLayout
import com.seoulmate.ui.component.PpsButton
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Black
import com.seoulmate.ui.theme.Black20
import com.seoulmate.ui.theme.Blue400
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.ColorEDF4FF
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray50
import com.seoulmate.ui.theme.CoolGray500
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    item: ChallengeItemData?,
    isLogin: Boolean = false,
    startedChallenge: Boolean = false,
    onBackClick: () -> Unit = {},
) {

    val challengeMissionLocationList = listOf(
        ChallengeItemData(
            id = 0,
            title = "First Challenge Title",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
        ),
    )

    val replyList = listOf(
        ReplyItemData(
            id = 0,
            title = "Lorem ipsum dolor sit amet consectetur. Ipsum sdv semper nisl bibendum dolor pretium commodo.",
            userNickName = "어리버리 송편",
            timeStamp = "2025.04.04 11:00",
        ),
        ReplyItemData(
            id = 1,
            title = "Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕 Hi Hello 안녕",
            userNickName = "어리버리 송편",
            timeStamp = "2025.04.04 11:01",
        ),
    )

    val isShowBottomFloating = true

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = onBackClick
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = com.seoulmate.ui.R.drawable.ic_left),
                            contentDescription = "Search Icon",
                            tint = CoolGray900,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TrueWhite,
                    titleContentColor = TrueWhite,
                    navigationIconContentColor = TrueWhite,
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(color = TrueWhite)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.Transparent)
            ) {
                val (body, floating) = createRefs()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(body) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                ) {
                    // Top Challenge Info
                    item {
                        item?.let {
                            TopChallengeDetailInfo(item = item)
                        }
                    }
                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    // Stamp
                    item {
                        ChallengeStamp()
                    }
                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    // Stamp Mission Location
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 15.dp),
                        ) {
                            PpsText(
                                modifier = Modifier.wrapContentSize(),
                                text = stringResource(id = R.string.stamp_mission_location_title),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = CoolGray900,
                                )
                            )
                            PpsText(
                                modifier = Modifier.wrapContentSize(),
                                text = stringResource(id = R.string.stamp_mission_location_info),
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    color = CoolGray400,
                                )
                            )
                        }
                    }
                    items(
                        count = challengeMissionLocationList.size
                    ) { index ->
                        ChallengeTileTypeLayout(
                            item = challengeMissionLocationList[index],
                        )
                    }
                    // Divider
                    item{
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 25.dp),
                            color = CoolGray25,
                            thickness = 2.dp,
                        )
                    }
                    // Reply
                    item {
                        Column(
                            modifier = Modifier.padding(horizontal = 15.dp),
                        ) {
                            PpsText(
                                modifier = Modifier.wrapContentSize(),
                                text = stringResource(id = R.string.title_reply),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = CoolGray900,
                                )
                            )
                            if (replyList.isEmpty()) {
                                PpsText(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = "{${replyList.size}}",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = CoolGray900,
                                    )
                                )
                            }
                        }
                    }
                    items(
                        count = replyList.size
                    ) { index ->
                        Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                            ChallengeReplyItemLayout(
                                item = replyList[index]
                            )
                        }
                    }

                    // Bottom Spacer
                    item {
                        Spacer(modifier = Modifier.height(25.dp))
                    }

                }

                // Bottom Floating UI
                if (isShowBottomFloating) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(
                                color = ColorEDF4FF,
                                shape = RoundedCornerShape(18.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Blue400,
                                shape = RoundedCornerShape(18.dp),
                            )
                            .constrainAs(floating) {
                                linkTo(top = parent.top, bottom = parent.bottom, bias = 1f)
                                linkTo(start = parent.start, end = parent.end)
                            }
                    ) {
                        PpsText(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = stringResource(R.string.stamp_floating),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Blue500,
                            )
                        )
                    }
                }
            }

            // Bottom
            BottomChallengeDetail(
                isLogin = isLogin,
                startedChallenge = startedChallenge,
            )
        }
    }

}



@Preview
@Composable
private fun PreviewLogin() {
    SeoulMateTheme {
        ChallengeDetailScreen(
            item = ChallengeItemData(
                id = 0,
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
            )
        )
    }
}

@Preview
@Composable
private fun PreviewStartChallenge() {
    SeoulMateTheme {
        ChallengeDetailScreen(
            item = ChallengeItemData(
                id = 0,
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
            ),
            isLogin = true,
            startedChallenge = true,
        )
    }
}

@Preview
@Composable
private fun PreviewChallenge() {
    SeoulMateTheme {
        ChallengeDetailScreen(
            item = ChallengeItemData(
                id = 0,
                title = "First Challenge Title",
                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
            ),
            isLogin = true,
            startedChallenge = false,
        )
    }
}