package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.pin.PinnedItems
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PinApiService {
    @POST("/api/PinnedItem/pin")
    suspend fun pinItem(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String
    )

    @GET("/api/DashBoard/Pinneditems")
    suspend fun getPinnedItems(
        @Header("Authorization") accessToken: String
    ): ServiceResponse<PinnedItems>

    @DELETE("/api/PinnedItem/unpin")
    suspend fun unpinItem(
        @Header("Authorization") accessToken: String,
        @Query("itemId") id: Int,
        @Query("itemType") type: String
    )

}