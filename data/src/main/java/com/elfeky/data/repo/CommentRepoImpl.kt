package com.elfeky.data.repo

import com.elfeky.data.remote.CommentApiService
import com.elfeky.data.remote.dto.CommentRequest
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.repo.CommentRepo
import javax.inject.Inject

class CommentRepoImpl @Inject constructor(
    private val apiService: CommentApiService
) : CommentRepo {
    override suspend fun getComments(
        accessToken: String,
        issueId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Comment> =
        apiService.getComments("Bearer $accessToken", issueId, pageSize, pageNumber).result

    override suspend fun sendComment(
        accessToken: String,
        issueId: Int,
        content: String
    ): Comment =
        apiService.sendComment(
            "Bearer $accessToken",
            issueId,
            CommentRequest(content)
        ).result.comment
}