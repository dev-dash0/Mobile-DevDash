package com.elfeky.domain.repo

import com.elfeky.domain.model.comment.Comment

interface CommentRepo {
    suspend fun getComments(
        accessToken: String,
        issueId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Comment>

    suspend fun sendComment(
        accessToken: String,
        issueId: Int,
        content: String
    ): Comment
}