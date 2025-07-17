package com.elfeky.data.remote

import com.elfeky.data.remote.dto.CommentRequest
import com.elfeky.data.remote.dto.PostCommentResult
import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.comment.Comment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentApiService {
    @GET("/api/Comment")
    suspend fun getComments(
        @Header("Authorization") accessToken: String,
        @Query("IssueId") issueId: Int,
        @Query("pageSize") pageSize: Int,
        @Query("pageNumber") pageNumber: Int,
        @Query("search") search: String = ""
    ): ServiceResponse<List<Comment>>

    @POST("/api/Comment")
    suspend fun sendComment(
        @Header("Authorization") accessToken: String,
        @Query("issueid") issueId: Int,
        @Body body: CommentRequest
    ): ServiceResponse<PostCommentResult>
}