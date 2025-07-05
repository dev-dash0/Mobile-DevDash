package com.elfeky.domain.repo

import com.elfeky.domain.model.search.Search

interface SearchRepo {
    suspend fun search(token: String, query: String): Search
}