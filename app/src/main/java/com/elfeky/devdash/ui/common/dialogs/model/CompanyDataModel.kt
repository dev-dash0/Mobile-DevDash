package com.elfeky.devdash.ui.common.dialogs.model

import android.net.Uri

data class CompanyDataModel(
    val title: String,
    val websiteUrl: String,
    val keywords: String,
    val description: String,
    val logoUri: Uri? = null
)
