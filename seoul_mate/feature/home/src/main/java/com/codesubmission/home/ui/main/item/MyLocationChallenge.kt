package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codesubmission.home.R
import com.seoulmate.data.model.ChallengeItemData
import com.seoulmate.ui.component.ChallengeSquareImageTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite
import kotlin.math.max

/**
 * 내 근처 챌린지 / 현재 위치에서 스탬프
 */
@Composable
fun MyLocationChallenge(
    modifier: Modifier,
    isLoginUser: Boolean,
    isLocationPermission: Boolean = false,
    possibleStampList: List<ChallengeItemData> = listOf(),
    onSignUpClick: () -> Unit = {},
    onLocationPermissionClick: () -> Unit = {},
) {

    Surface(
        modifier = modifier,
    ) {
        if (isLoginUser && isLocationPermission) {
            Column (
                modifier = Modifier.padding(horizontal = 20.dp)
            ){
                PpsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.home_my_location_possible_stamp_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = CoolGray900,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                PpsText(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    text = stringResource(R.string.home_my_location_possible_stamp_sub),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = CoolGray600,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                PossibleStampList(
                    possibleStampList = possibleStampList,
                )
            }
        } else if (!isLoginUser)  {
            Surface(modifier = Modifier.padding(horizontal = 20.dp)) {
                SignUpTile(
                    onSignUpClick = onSignUpClick
                )
            }
        } else if (!isLocationPermission) {
            Surface(modifier = Modifier.padding(horizontal = 20.dp)) {
                LocationPermissionTile(
                    onLocationPermissionClick = onLocationPermissionClick
                )
            }
        }
    }

}

@Composable
fun LocationPermissionTile(
    onLocationPermissionClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color1D8EFE)
            .clickable {
                onLocationPermissionClick()
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp).weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.home_my_location_permission_content_title),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TrueWhite,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            ConstraintLayout {
                val (img, button) = createRefs()

                Image(
                    modifier = Modifier.size(66.dp),
                    painter = painterResource(com.seoulmate.ui.R.drawable.img_location_point),
                    contentDescription = "SignUp Image",
                )

                Box(
                    modifier = Modifier
                ) {
                    PpsText(
                        modifier = Modifier,
                        text = stringResource(R.string.home_my_location_permission_content_sub_title),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Blue500,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpTile(
    onSignUpClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color1D8EFE)
            .clickable {
                onSignUpClick()
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp).weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                PpsText(
                    modifier = Modifier,
                    text = stringResource(R.string.home_my_location_sign_up_content_title),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TrueWhite,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                PpsText(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(R.string.home_my_location_sign_up_content_sub_title),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TrueWhite,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Image(
                painter = painterResource(com.seoulmate.ui.R.drawable.img_location_point),
                contentDescription = "SignUp Image",
            )
        }
    }
}

@Composable
fun PossibleStampList(
    possibleStampList: List<ChallengeItemData>,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(
            items = possibleStampList,
            key = { _, item -> item.id }
        ) { index, item ->
            Box(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                ChallengeSquareImageTypeLayout(
                    item = item,
                    isShowNowPop = true,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMyLocationChallenge() {
    SeoulMateTheme {
        MyLocationChallenge(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
            isLoginUser = true,
            possibleStampList = listOf(
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