package com.elfeky.domain.usecase.assign

import com.elfeky.domain.model.issue.AssignResponse
import com.elfeky.domain.model.issue.AssignUserIssue
import com.elfeky.domain.repo.AssignUserIssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AssignUserIssueUseCase @Inject constructor(
    private val repo: AssignUserIssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(issueId: Int, userId: Int): Flow<Resource<AssignResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response =
                repo.assignUserIssue(accessTokenUseCase.get(), AssignUserIssue(issueId, userId))
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}