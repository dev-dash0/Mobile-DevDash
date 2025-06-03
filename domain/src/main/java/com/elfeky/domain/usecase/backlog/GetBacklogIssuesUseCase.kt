package com.elfeky.domain.usecase.backlog

import com.elfeky.domain.model.backlog.ResponseIssueBacklog
import com.elfeky.domain.repo.BacklogRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBacklogIssuesUseCase @Inject constructor(
    private val repo: BacklogRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(projectId: Int, pageNumber: Int, pageSize: Int): Flow<Resource<ResponseIssueBacklog>> = flow {
        try {
            emit(Resource.Loading())
            val response =repo.getIssues(accessToken = accessTokenUseCase.get(), projectId = projectId,pageNumber = pageNumber, pageSize = pageSize  )
            emit(Resource.Success(data = response))
        }catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}