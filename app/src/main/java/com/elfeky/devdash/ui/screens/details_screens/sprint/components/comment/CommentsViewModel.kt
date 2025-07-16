package com.elfeky.devdash.ui.screens.details_screens.sprint.components.comment

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.insertFooterItem
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.model.issue.Issue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommentsViewModel : ViewModel() {

    @Immutable
    data class CommentsUiState(
        val issue: Issue? = null,
        val comments: Flow<PagingData<Comment>> = flowOf(PagingData.empty()),
        val commentInputText: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val editingComment: Comment? = null // New state to hold the comment being edited
    )

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState: StateFlow<CommentsUiState> = _uiState.asStateFlow()

    fun setIssue(issue: Issue) {
        _uiState.update {
            it.copy(
                issue = issue,
                isLoading = true,
                comments = flowOf(PagingData.empty())
            )
        }

        viewModelScope.launch {
            delay(500) // Simulate network latency
            // Simulate fetching comments. Replace with actual repository call.
            val dummyComments = listOf(
                Comment(
                    id = 1,
                    content = "This is a sample comment.",
                    createdBy = UserProfile(
                        1,
                        null,
                        "john.doe@example.com",
                        "John",
                        "Doe",
                        "john_doe",
                        "123-456-7890",
                        "1990-01-01",
                        101
                    ),
                    createdById = 1,
                    creationDate = "2025-07-15",
                    issueId = issue.id,
                    projectId = 1,
                    sprintId = 1,
                    tenantId = 1
                ),
                Comment(
                    id = 2,
                    content = "Another comment here!",
                    createdBy = UserProfile(
                        2,
                        null,
                        "jane.smith@example.com",
                        "Jane",
                        "Smith",
                        "jane_smith",
                        "098-765-4321",
                        "1992-05-10",
                        101
                    ),
                    createdById = 2,
                    creationDate = "2025-07-15",
                    issueId = issue.id,
                    projectId = 1,
                    sprintId = 1,
                    tenantId = 1
                )
            )
            _uiState.update {
                it.copy(
                    isLoading = false,
                    comments = flowOf(PagingData.from(dummyComments))
                )
            }
        }
    }

    fun onCommentTextChanged(text: String) {
        _uiState.update { it.copy(commentInputText = text) }
    }

    fun addComment() {
        val currentState = _uiState.value
        if (currentState.commentInputText.isBlank() || currentState.issue == null) return

        if (currentState.editingComment != null) {
            updateComment(currentState.editingComment)
        } else {
            viewModelScope.launch {
                // Simulate adding a new comment
                delay(200)
                val newComment = Comment(
                    id = (0..10000).random(), // Generate a unique ID for simulation
                    content = currentState.commentInputText,
                    createdBy = UserProfile(
                        3,
                        null,
                        "current.user@example.com",
                        "Current",
                        "User",
                        "current_user",
                        "",
                        "",
                        101
                    ), // Replace with actual current user
                    createdById = 3,
                    creationDate = "2025-07-15",
                    issueId = currentState.issue.id,
                    projectId = 1,
                    sprintId = 1,
                    tenantId = 1
                )

                val currentPagingData = currentState.comments.firstOrNull() ?: PagingData.empty()
                _uiState.update {
                    it.copy(
                        commentInputText = "",
                        comments = flowOf(currentPagingData.insertFooterItem(item = newComment)),
                    )
                }
            }
        }
    }

    fun startEditingComment(comment: Comment) {
        _uiState.update { it.copy(editingComment = comment, commentInputText = comment.content) }
    }

    fun updateComment(comment: Comment) {
        viewModelScope.launch {
            delay(200)
            // Simulate updating the comment
//            val currentComments = _uiState.value.comments.collectAsLazyPagingItems().itemSnapshotList.items
//            val updatedList = currentComments.map {
//                if (it.id == comment.id) it.copy(content = newText) else it
//            }
//            _uiState.update {
//                it.copy(
//                    commentInputText = "",
//                    editingComment = null,
//                    comments = flowOf(PagingData.from(updatedList)),
//                )
//            }
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch {
            delay(200)
            // Simulate deleting the comment
//            val currentComments = _uiState.value.comments.collectAsLazyPagingItems().itemSnapshotList.items
//            val updatedList = currentComments.filter { it.id != comment.id }
//            _uiState.update {
//                it.copy(
//                    comments = flowOf(PagingData.from(updatedList)),
//                )
//            }
        }
    }
}
