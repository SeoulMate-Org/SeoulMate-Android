package com.codesubmission.home.ui.main.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codesubmission.home.R
import com.seoulmate.data.model.challenge.ChallengeItemData
import com.seoulmate.data.model.challenge.ChallengeLocationData
import com.seoulmate.data.model.challenge.ChallengeLocationItemData
import com.seoulmate.ui.component.ChallengeSquareImageTypeLayout
import com.seoulmate.ui.component.PpsText
import com.seoulmate.ui.theme.Blue500
import com.seoulmate.ui.theme.Color1D8EFE
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.CoolGray900
import com.seoulmate.ui.theme.SeoulMateTheme
import com.seoulmate.ui.theme.TrueWhite

/**
 * 내 근처 챌린지 / 현재 위치에서 스탬프
 */
@Composable
fun MyLocationChallenge(
    modifier: Modifier,
    isLoginUser: Boolean,
    isLocationPermission: Boolean,
    possibleStampList: List<ChallengeLocationData> = listOf(),
    onSignUpClick: () -> Unit = {},
    onLocationPermissionClick: () -> Unit = {},
    onItemCLick: (challengeId: Int) -> Unit = {},
) {

    Surface(
        modifier = modifier,
        color = TrueWhite
    ) {
        if (isLoginUser && isLocationPermission) {
            Column{
                Column(
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
                }
                PossibleStampList(
                    possibleStampList = possibleStampList,
                    onItemCLick = onItemCLick,
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

            Box(
                modifier = Modifier.padding(end = 15.dp, bottom = 10.dp)
            ) {
                Image(
                    modifier = Modifier.width(75.dp).height(92.dp),
                    painter = painterResource(com.seoulmate.ui.R.drawable.img_location_permission),
                    contentDescription = "SignUp Image",
                    contentScale = ContentScale.Fit,
                )
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
    possibleStampList: List<ChallengeLocationData>,
    onItemCLick: (challengeId: Int) -> Unit = {},
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(
            items = possibleStampList,
            key = { _, item -> item.id }
        ) { index, item ->
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 4.dp)
            ) {
                if (index == 0) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                ChallengeSquareImageTypeLayout(
                    item = item,
                    isShowNowPop = true,
                    onItemCLick = onItemCLick,
                )
                if (index == possibleStampList.lastIndex) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}
