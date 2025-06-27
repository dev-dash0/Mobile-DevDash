package com.elfeky.domain.usecase.sprint_issue

import com.elfeky.domain.model.issue.Issue
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
        priority: String,
        status: String,
        title: String,
        type: String,
        description: String,
        isBacklog: Boolean,
        startDate: String,
        deadline: String,
        deliveredDate: String,
        lastUpdate: String,
        labels: String,
        attachmentFile: File?,
        attachmentMediaType: String?
    ): Flow<Resource<Issue>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.createIssue(
                accessToken = accessTokenUseCase.get(),
                sprintId = sprintId,
                priority = priority,
                status = status,
                title = title,
                type = type,
                description = description,
                isBacklog = isBacklog,
                startDate = startDate,
                deadline = deadline,
                deliveredDate = deliveredDate,
                lastUpdate = lastUpdate,
                labels = labels,
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