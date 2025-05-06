package com.seoulmate.challenge.comment

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seoulmate.data.ChallengeDetailInfo
import com.seoulmate.data.UserInfo
import com.seoulmate.data.model.challenge.ChallengeCommentItem
import com.seoulmate.domain.usecase.DeleteCommentUseCase
import com.seoulmate.domain.usecase.GetCommentListUseCase
import com.seoulmate.domain.usecase.ModifyCommentUseCase
import com.seoulmate.domain.usecase.WriteCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeCommentViewModel @Inject constructor(
    private val writeCommentUseCase: WriteCommentUseCase,
    private val modifyCommentUseCase: ModifyCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val getCommentListUseCase: GetCommentListUseCase,
): ViewModel(){
    var commentList = mutableStateOf<List<ChallengeCommentItem>>(listOf())

    var completedWrite = mutableStateOf(false)

    fun writeComment(
        id: Int,
        comment: String,
    ) {
        viewModelScope.launch {
            writeCommentUseCase(
                id, comment, UserInfo.getLanguageCode()
            ).collectLatest { suspend ->
                Log.d("@@@@@", "writeComment : $suspend")

                completedWrite.value = true
            }
        }
    }

    fun modifyComment(
        commentId: Int,
        comment: String,
    ) {
        viewModelScope.launch {
            modifyCommentUseCase(
                commentId, comment, UserInfo.getLanguageCode()
            ).collectLatest { suspend ->
                Log.d("@@@@@", "writeComment : $suspend")

                completedWrite.value = true
            }
        }
    }

    fun deleteComment(
        commentId: Int,
    ) {
        viewModelScope.launch {
            deleteCommentUseCase(
                commentId
            ).collectLatest {
                completedWrite.value = true
            }
        }
    }

    // fetch comments list
    fun getCommentList(
        id: Int,
        language: String = "KOR",
    ) {
        viewModelScope.launch {
            getCommentListUseCase(
                id = id,
                languageCode = language,
            ).collectLatest { list ->
                list?.let { listItem ->
                    ChallengeDetailInfo.commentList = listItem
                    commentList.value = ChallengeDetailInfo.commentList
                }
            }
        }
    }

}