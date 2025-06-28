package com.elfeky.data.remote.dto

import com.elfeky.domain.model.comment.Comment

data class PostCommentResult(
    val id: Int,
    val comment: Comment
)