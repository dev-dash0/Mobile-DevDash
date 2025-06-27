package com.elfeky.data.remote

import com.elfeky.data.remote.dto.ServiceResponse
import com.elfeky.domain.model.search.Search
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApiService {
    @GET("/global")
    suspend fun search(
        @Header("Authorization") accessToken: String,
        @Query("query") query: String
    ): ServiceResponse<Search>
}