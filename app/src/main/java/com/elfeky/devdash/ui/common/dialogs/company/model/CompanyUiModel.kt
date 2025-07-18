package com.elfeky.devdash.ui.common.dialogs.company.model

data class CompanyUiModel(
    val title: String,
    val websiteUrl: String,
    val keywords: String,
    val description: String,
    val logoUri: String? = null
)
