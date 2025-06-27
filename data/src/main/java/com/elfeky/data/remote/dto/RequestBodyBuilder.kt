package com.elfeky.data.remote.dto

import com.elfeky.domain.model.issue.IssueFormFields
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object RequestBodyBuilder {
    fun createPartFromString(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }

    fun prepareFilePart(partName: String, file: File, mediaType: String): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse(mediaType), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun formatDateToIso8601(date: Date?): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
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
            "LastUpdate" to createPartFromString(fields.lastUpdate.toString()),
            "SprintId" to createPartFromString(fields.sprintId ?: "")
        )
    }
}