package com.elfeky.data.repo

import com.elfeky.data.remote.SprintIssueApiService
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.repo.SprintIssueRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class SprintIssueRepoImpl @Inject constructor(
    private val sprintIssueApiService: SprintIssueApiService
) : SprintIssueRepo {
    override suspend fun createIssue(
        accessToken: String,
        projectId: Int,
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
    ): Issue {
        val priorityBody = priority.toRequestBody("text/plain".toMediaTypeOrNull())
        val statusBody = status.toRequestBody("text/plain".toMediaTypeOrNull())
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeBody = type.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val isBacklogBody = isBacklog.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val startDateBody = startDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val deadlineBody = deadline.toRequestBody("text/plain".toMediaTypeOrNull())
        val deliveredDateBody = deliveredDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastUpdateBody = lastUpdate.toRequestBody("text/plain".toMediaTypeOrNull())
        val labelsBody = labels.toRequestBody("text/plain".toMediaTypeOrNull())

        val attachmentPart = attachmentFile?.let { file ->
            val requestFile =
                file.asRequestBody(attachmentMediaType?.toMediaTypeOrNull())
            MultipartBody.Part.createFormData("attachment", file.name, requestFile)
        }

        return sprintIssueApiService.createSprintIssue(
            accessToken = "Bearer $accessToken",
            sprintId = projectId,
            priority = priorityBody,
            status = statusBody,
            title = titleBody,
            type = typeBody,
            description = descriptionBody,
            isBacklog = isBacklogBody,
            startDate = startDateBody,
            deadline = deadlineBody,
            deliveredDate = deliveredDateBody,
            attachment = attachmentPart,
            lastUpdate = lastUpdateBody,
            labels = labelsBody
        ).result.issue
    }

    override suspend fun getIssues(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue> = sprintIssueApiService.getSprintIssues(
        accessToken = "Bearer $accessToken",
        sprintId = projectId,
        pageSize = pageSize,
        pageNumber = pageNumber
    ).result
}