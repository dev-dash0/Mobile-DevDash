package com.elfeky.data.repo

import com.elfeky.data.remote.IssueApiService
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.repo.IssueRepo
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class IssueRepoImpl @Inject constructor(
    private val issueApiService: IssueApiService
) : IssueRepo {
    override suspend fun getIssue(
        accessToken: String,
        id: Int
    ): Issue {
        return issueApiService.getIssue(
            accessToken = "Bearer $accessToken",
            id = id
        ).result
    }

    override suspend fun updateIssue(
        accessToken: String,
        id: Int,
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
        return issueApiService.updateIssue(
            accessToken = "Bearer $accessToken",
            id = id,
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

    override suspend fun deleteIssue(accessToken: String, id: Int) {
        issueApiService.deleteIssue(accessToken = "Bearer $accessToken", id = id)
    }
}