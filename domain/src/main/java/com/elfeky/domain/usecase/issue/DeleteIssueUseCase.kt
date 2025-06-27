package com.elfeky.domain.usecase.issue

import com.elfeky.domain.repo.IssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteIssueUseCase @Inject constructor(
    private val repo: IssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(id: Int): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.deleteIssue(accessTokenUseCase.get(), id)
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}