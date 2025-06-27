package com.elfeky.data.repo

import com.elfeky.data.remote.IssueApiService
import com.elfeky.data.remote.dto.RequestBodyBuilder
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.model.issue.IssueFormFields
import com.elfeky.domain.repo.IssueRepo
import okhttp3.MultipartBody
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
        issueApiService.updateIssue(
            accessToken = accessToken,
            id = id,
            fields = formParts,
            attachment = attachmentMultipartPart
        )
    }

    override suspend fun deleteIssue(accessToken: String, id: Int) {
        issueApiService.deleteIssue(accessToken = accessToken, id = id)
    }
}