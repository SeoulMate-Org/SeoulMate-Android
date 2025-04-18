package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesubmission.home.R
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeSquareImageTypeLayout
import com.seoulmate.ui.component.RoundedTag
import com.seoulmate.ui.theme.CoolGray400
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme

@Composable
fun ChallengeCategory(
    modifier: Modifier,
    categoryList: List<String> = listOf(),
    challengeList: List<ChallengeItemData> = listOf(),
    onMoreClick: () -> Unit = {},
    onItemCLick: (ChallengeItemData) -> Unit = {},
) {
    var categoryIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        // Title + More Button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Title
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home_challenge_category_title),
                fontSize = 20.sp,
                style = TextStyle(
                    color = CoolGray900
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            // More Button
            Button(
                onClick = onMoreClick,
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                )
            ) {
                Text(
                    text = stringResource(com.seoulmate.ui.R.string.str_more),
                    fontSize = 13.sp,
                    style = TextStyle(
                        color = CoolGray400
                    ),
                )
            }
        }
        // Category
        LazyRow{
            itemsIndexed(
                items = categoryList,
                key = { _, item -> item }
            ) { index, item ->
                Box(modifier = Modifier.padding(horizontal = 5.dp),) {
                    RoundedTag(
                        title = item,
                        isSelected = index == categoryIndex
                    ) {
                        categoryIndex = index
                    }
                }
            }
        }
        // Challenge List
        LazyRow {
            itemsIndexed(
                items = challengeList,
                key = { _, item -> item.id }
            ) { index, item ->
                Box(modifier = Modifier.padding(horizontal = 10.dp),) {
                    ChallengeSquareImageTypeLayout(
                        item = item,
                        isShowNowPop = true,
                    ) { clickItem ->
                        onItemCLick(clickItem)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChallengeCategory() {
    SeoulMateTheme {
        ChallengeCategory(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
            categoryList = listOf("Tag1", "Tag2", "Tag3", "Tag4", "Tag5", "Tag6", "Tag7", "Tag8", "Tag9"),
            challengeList = listOf(
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
            )
        )
    }
}