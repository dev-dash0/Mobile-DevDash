package com.elfeky.domain.usecase.backlog

import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.BacklogRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class CreateBacklogIssueUseCase @Inject constructor(
    private val repo: BacklogRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(
        projectId: Int,
        issue: IssueFormFields,
        attachmentFile: File?,
        attachmentMediaType: String?
    ): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.createIssue(
                accessToken = accessTokenUseCase.get(),
                projectId = projectId,
                formFields = issue,
                attachmentFile = attachmentFile,
                attachmentMediaType = attachmentMediaType
            )
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}