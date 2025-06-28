package com.elfeky.data.remote.dto

import com.elfeky.domain.model.issue.IssueFormFields
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object RequestBodyBuilder {
    fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun prepareFilePart(partName: String, file: File, mediaType: String): MultipartBody.Part {
        val requestFile = file.asRequestBody(mediaType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createIssueFormParts(fields: IssueFormFields): Map<String, RequestBody> {
        return mapOf(
            "Priority" to createPartFromString(fields.priority),
            "Status" to createPartFromString(fields.status),
            "Title" to createPartFromString(fields.title),
            "Type" to createPartFromString(fields.type),
            "Labels" to createPartFromString(fields.labels.toString()),
            "Description" to createPartFromString(fields.description.toString()),
            "IsBacklog" to createPartFromString(fields.isBacklog.toString()),
            "StartDate" to createPartFromString(fields.startDate.toString()),
            "Deadline" to createPartFromString(fields.deadline.toString()),
            "DeliveredDate" to createPartFromString(fields.deliveredDate.toString()),
            "LastUpdate" to createPartFromString(fields.lastUpdate.toString())
        )
    }
}