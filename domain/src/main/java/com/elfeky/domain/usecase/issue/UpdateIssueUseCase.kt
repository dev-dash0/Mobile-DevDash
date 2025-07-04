package com.elfeky.domain.usecase.issue

import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.repo.IssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateIssueUseCase @Inject constructor(
    private val repo: IssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(
        issue: Issue,
        sprintId: Int? = null,
        attachmentFile: File? = null,
        attachmentMediaType: String? = null
    ): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.updateIssue(
                accessToken = accessTokenUseCase.get(),
                id = issue.id,
                priority = issue.priority,
                status = issue.status,
                title = issue.title,
                type = issue.type,
                description = issue.description,
                isBacklog = sprintId == null,
                startDate = issue.startDate,
                deadline = issue.deadline,
                deliveredDate = issue.deliveredDate,
                lastUpdate = LocalDateTime.now().toString(),
                labels = issue.labels,
                attachmentFile = attachmentFile,
                attachmentMediaType = attachmentMediaType,
                sprintId = sprintId
            )
            emit(Resource.Success())
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}