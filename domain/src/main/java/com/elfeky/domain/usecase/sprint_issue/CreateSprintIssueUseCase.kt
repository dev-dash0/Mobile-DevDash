package com.elfeky.domain.usecase.sprint_issue

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.SprintIssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class CreateSprintIssueUseCase @Inject constructor(
    private val repo: SprintIssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(
        sprintId: Int,
        issue: IssueFormFields,
        attachmentFile: File? = null,
        attachmentMediaType: String? = null
    ): Flow<Resource<Issue>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.createIssue(
                accessToken = accessTokenUseCase.get(),
                sprintId = sprintId,
                priority = issue.priority,
                status = issue.status,
                title = issue.title,
                type = issue.type,
                description = issue.description ?: "",
                isBacklog = issue.isBacklog,
                startDate = issue.startDate ?: "",
                deadline = issue.deadline ?: "",
                deliveredDate = issue.deliveredDate ?: "",
                lastUpdate = LocalDateTime.now().toString(),
                labels = issue.labels ?: "",
                attachmentFile = attachmentFile,
                attachmentMediaType = attachmentMediaType
            )
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}