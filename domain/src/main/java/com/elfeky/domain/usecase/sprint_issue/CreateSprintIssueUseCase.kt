package com.elfeky.domain.usecase.sprint_issue

import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.SprintIssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class CreateSprintIssueUseCase @Inject constructor(
    private val repo: SprintIssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(
        sprintId: Int,
        issue: IssueFormFields,
        attachmentFile: File?,
        attachmentMediaType: String?
    ): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.createIssue(
                accessToken = accessTokenUseCase.get(),
                sprintId = sprintId,
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