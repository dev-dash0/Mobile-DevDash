package com.elfeky.devdash.ui.common.dialogs.company.model

import android.net.Uri

data class CompanyUiModel(
    val title: String,
    val websiteUrl: String,
    val keywords: String,
    val description: String,
    val logoUri: Uri? = null
)
