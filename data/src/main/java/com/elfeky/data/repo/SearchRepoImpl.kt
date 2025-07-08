package com.elfeky.data.repo

import com.elfeky.data.remote.SearchApiService
import com.elfeky.domain.model.search.Search
import com.elfeky.domain.repo.SearchRepo
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRepo {
    override suspend fun search(accessToken: String, query: String): Search {
        return searchApiService.search("Bearer $accessToken", query).result
    }
}