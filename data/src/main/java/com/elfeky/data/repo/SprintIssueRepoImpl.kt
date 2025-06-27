package com.elfeky.data.repo

import com.elfeky.data.remote.SprintIssueApiService
import com.elfeky.data.remote.dto.RequestBodyBuilder
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.SprintIssueRepo
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class SprintIssueRepoImpl @Inject constructor(
    private val sprintIssueApiService: SprintIssueApiService
) : SprintIssueRepo {
    override suspend fun createIssue(
        accessToken: String,
        projectId: Int,
        formFields: IssueFormFields,
        attachmentFile: File?,
        attachmentMediaType: String?
    ) {
        val formParts = RequestBodyBuilder.createIssueFormParts(formFields)

        val attachmentMultipartPart: MultipartBody.Part? =
            if (attachmentFile != null && attachmentMediaType != null) {
                RequestBodyBuilder.prepareFilePart(
                    "Attachment",
                    attachmentFile,
                    attachmentMediaType
                )
            } else {
                null
            }
        sprintIssueApiService.createSprintIssue(
            accessToken = "Bearer $accessToken",
            sprintId = projectId,
            fields = formParts,
            attachment = attachmentMultipartPart
        )
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