package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.model.project.Project
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.model.tenant.Tenant
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PinApiService {
    @POST("/api/PinnedItem/pin")
    suspend fun pinTenant(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "tenant"
    )

    @POST("/api/PinnedItem/pin")
    suspend fun pinProject(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "project"
    )

    @POST("/api/PinnedItem/pin")
    suspend fun pinSprint(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "sprint"
    )

    @POST("/api/PinnedItem/pin")
    suspend fun pinIssue(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "issue"
    )

    @GET("/api/PinnedItem/owned-pinned-items")
    suspend fun getPinnedTenants(
        @Header("Authorization") accessToken: String,
        @Query("itemType") type: String = "tenant"
    ): ServiceResponse<List<Tenant>>

    @GET("/api/PinnedItem/owned-pinned-items")
    suspend fun getPinnedProjects(
        @Header("Authorization") accessToken: String,
        @Query("itemType") type: String = "project"
    ): ServiceResponse<List<Project>>

    @GET("/api/PinnedItem/owned-pinned-items")
    suspend fun getPinnedSprints(
        @Header("Authorization") accessToken: String,
        @Query("itemType") type: String = "sprint"
    ): ServiceResponse<List<Sprint>>

    @GET("/api/PinnedItem/owned-pinned-items")
    suspend fun getPinnedIssues(
        @Header("Authorization") accessToken: String,
        @Query("itemType") type: String = "issue"
    ): ServiceResponse<List<Issue>>

    @DELETE("/api/PinnedItem/unpin")
    suspend fun unpinTenant(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "tenant"
    )

    @DELETE("/api/PinnedItem/unpin")
    suspend fun unpinProject(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "project"
    )

    @DELETE("/api/PinnedItem/unpin")
    suspend fun unpinSprint(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "sprint"
    )

    @DELETE("/api/PinnedItem/unpin")
    suspend fun unpinIssue(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String = "issue"
    )

    @GET("/api/DashBoard/Pinneditems")
    suspend fun getAllPinnedItems(
        @Header("Authorization") accessToken: String
    ): ServiceResponse<PinnedItems>

}