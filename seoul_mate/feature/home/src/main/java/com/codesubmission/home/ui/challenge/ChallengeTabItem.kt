package com.codesubmission.home.ui.challenge

import androidx.annotation.StringRes
import com.codesubmission.home.R

sealed class ChallengeTabItem(
    val id: Int,
    @StringRes val titleRes: Int,
) {
    data object Interest : ChallengeTabItem(
        0,
        R.string.tab_interest_challenge,
    )
    data object InProgress : ChallengeTabItem(
        1,
        R.string.tab_in_progress_challenge
    )
    data object Complete : ChallengeTabItem(
        2,
        R.string.tab_complete_challenge
    )
}
