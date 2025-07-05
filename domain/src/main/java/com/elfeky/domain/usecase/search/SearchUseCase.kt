package com.elfeky.domain.usecase.search

import com.elfeky.domain.model.search.Search
import com.elfeky.domain.repo.SearchRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repo: SearchRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(query: String): Flow<Resource<Search?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.search(accessTokenUseCase.get(), query)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                emit(Resource.Success(null))
            } else {
                emit(Resource.Error(message = "Unexpected error occurred"))

            }
        }
    }
}