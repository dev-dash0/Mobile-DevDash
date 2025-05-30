package com.elfeky.domain.model.backlog


data class ResponseIssueBacklog(
    val errorMessages: List<Any>,
    val isSuccess: Boolean,
    val result: List<Result>,
    val statusCode: Int
)