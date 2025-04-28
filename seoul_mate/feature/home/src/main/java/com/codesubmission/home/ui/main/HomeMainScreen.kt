package com.codesubmission.home.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.codesubmission.home.R
import com.codesubmission.home.ui.main.item.ChallengeCategory
import com.codesubmission.home.ui.main.item.ChallengeRanking
import com.codesubmission.home.ui.main.item.HorizontalCarousel
import com.codesubmission.home.ui.main.item.MissingChallenge
import com.codesubmission.home.ui.main.item.MyLocationChallenge
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.data.model.ChallengeStampItemData
import com.seoulmate.ui.component.ChallengeRankingTileTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.component.Screen
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.CoolGray25
import com.seoulmate.ui.theme.MainMissingChallengeGradientStart
import com.seoulmate.ui.theme.MainTopGradientStart
import com.seoulmate.ui.theme.TrueWhite
import com.seoulmate.ui.theme.White

@Composable
fun HomeMainScreen(
    context: Context,
    onChallengeItemClick: (item: ChallengeItemData) -> Unit = {},
    onChallengeLikeClick: (challengeId: Int) -> Unit = {},
    onThemeMoreClick: () -> Unit = {},
    onChangeScreen: (screen: Screen) -> Unit = { _ -> },
) {
    val locationPermission = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocationPermissionGranted = remember{ mutableStateOf(false) }

    val testRankingList = listOf(
        ChallengeItemData(
            id = 0,
            title = "First Challenge Title 0",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 1,
            title = "Second Challenge Title 1",
            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
        ),
        ChallengeItemData(
            id = 2,
            title = "Third Challenge Title 2",
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
        ),
        ChallengeItemData(
            id = 3,
            title = "First Challenge Title 3",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 4,
            title = "Second Challenge Title 4",
            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 5,
            title = "Third Challenge Title 5",
            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
            isInterest = true,
        ),
        ChallengeItemData(
            id = 6,
            title = "First Challenge Title 6",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
        ),
        ChallengeItemData(
            id = 7,
            title = "First Challenge Title 7",
            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
        ),
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    LaunchedEffect(Unit) {
        fineLocationPermissionGranted.value = locationPermission.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TrueWhite,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Challenge Carousel Section
            item {
                Column(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(listOf(MainTopGradientStart, TrueWhite)),
                            shape = RectangleShape,
                            alpha = 1f,
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(top = 15.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        PpsText(
                            modifier = Modifier.padding(start = 20.dp, bottom = 16.dp),
                            text = stringResource(R.string.home_top_title),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = White,
                            ),
                        )
                        Image(
                            painter = painterResource(com.seoulmate.ui.R.drawable.img_main_top),
                            contentDescription = "Main Top Title Image"
                        )
                    }
                    HorizontalCarousel(
                        itemList = listOf(
                            ChallengeItemData(
                                id = 0,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 1,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 2,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 3,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 4,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 5,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 6,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 7,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 8,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 9,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                            ChallengeItemData(
                                id = 10,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 11,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                        ),
                        onChallengeItemClick = onChallengeItemClick,
                    )
                }
            }
            // My Location Challenge
            item {
                Surface(
                    modifier = Modifier.padding(top = 48.dp),
                    color = TrueWhite
                ) {
                    MyLocationChallenge(
                        modifier = Modifier.fillMaxWidth(),
                        isLoginUser = UserInfo.isUserLogin(),
                        isLocationPermission = fineLocationPermissionGranted.value,
                        possibleStampList = listOf(
                            ChallengeItemData(
                                id = 0,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 1,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            ),
                            ChallengeItemData(
                                id = 2,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            ),
                            ChallengeItemData(
                                id = 3,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 4,
                                title = "Second Challenge Title",
                                imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 5,
                                title = "Third Challenge Title",
                                imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                                isInterest = true,
                            ),
                            ChallengeItemData(
                                id = 6,
                                title = "First Challenge Title",
                                imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            ),
                        ),
                        onSignUpClick = {
                            onChangeScreen(Screen.Login)
                        },
                        onLocationPermissionClick = {
                            launcher.launch(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = "package:${context.packageName}".toUri()
                            })
                        },
                    )
                }
            }
            // Challenge Category
            item {
                Column (
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .background(color = TrueWhite),
                ) {
                    ChallengeCategory(
                        modifier = Modifier.fillMaxWidth(),
                        onMoreClick = onThemeMoreClick,
                        onChallengeLikeClick = onChallengeLikeClick,
                    )
                }
            }
            // Missing Challenge
            item {
                MissingChallenge()
            }
            // Challenge Ranking
            item {
                ChallengeRanking(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                    onMoreClick = {

                    },
                )
            }
            items(
                count = testRankingList.size
            ) { index ->
                Box(modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 15.dp),
                ) {
                    ChallengeRankingTileTypeLayout(
                        item = testRankingList[index],
                        index = index,
                    ) { item ->

                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}