package com.elfeky.data.repo

import com.elfeky.data.remote.BacklogApiService
import com.elfeky.data.remote.dto.RequestBodyBuilder
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.BacklogRepo
import okhttp3.MultipartBody
import javax.inject.Inject

class BacklogRepoImpl @Inject constructor(
    private val backlogApiService: BacklogApiService
) : BacklogRepo {
    override suspend fun createIssue(
        accessToken: String,
        projectId: Int,
        formFields: IssueFormFields,
        attachmentFile: java.io.File?,
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
        backlogApiService.createBacklogIssue(
            accessToken = "Bearer $accessToken",
            projectId = projectId,
            fields = formParts,
            attachment = attachmentMultipartPart
        )
    }

    override suspend fun getIssues(
        accessToken: String,
        projectId: Int,
        pageSize: Int,
        pageNumber: Int
    ): List<Issue> = backlogApiService.getBacklogIssues(
        accessToken = "Bearer $accessToken",
        projectId = projectId,
        pageSize = pageSize,
        pageNumber = pageNumber
    ).result
}
