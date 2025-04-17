package com.codesubmission.home.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.theme.TrueWhite

@Composable
fun HomeMainScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TrueWhite
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Challenge Carousel Section
            item {
                HorizontalCarousel(
                    itemList = listOf(
                        ChallengeItemData(
                            id = 0,
                            title = "First Challenge Title",
                            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                            isInterest = true,
                        ),
                        ChallengeItemData(
                            id = 1,
                            title = "Second Challenge Title",
                            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 2,
                            title = "Third Challenge Title",
                            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 3,
                            title = "First Challenge Title",
                            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                            isInterest = true,
                        ),
                        ChallengeItemData(
                            id = 4,
                            title = "Second Challenge Title",
                            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                            isInterest = true,
                        ),
                        ChallengeItemData(
                            id = 5,
                            title = "Third Challenge Title",
                            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                            isInterest = true,
                        ),
                        ChallengeItemData(
                            id = 6,
                            title = "First Challenge Title",
                            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 7,
                            title = "Second Challenge Title",
                            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 8,
                            title = "Third Challenge Title",
                            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 9,
                            title = "First Challenge Title",
                            imgUrl = "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),
                        ChallengeItemData(
                            id = 10,
                            title = "Second Challenge Title",
                            imgUrl = "https://cdn.britannica.com/39/226539-050-D21D7721/Portrait-of-a-cat-with-whiskers-visible.jpg",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
                            isInterest = true,
                        ),
                        ChallengeItemData(
                            id = 11,
                            title = "Third Challenge Title",
                            imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPzlSPtQD3H6OK36fZXlVpI-PiRR8elwtGyw&s",
                            themeList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9")
                        ),

                        )
                )
            }
        }
    }
}