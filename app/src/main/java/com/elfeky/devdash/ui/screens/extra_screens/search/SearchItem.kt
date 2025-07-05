package com.elfeky.devdash.ui.screens.extra_screens.search

data class SearchItem(
    val id: Int,
    val title: String,
    val type: Type = Type.NONE,
)

enum class Type {
    ISSUE, PROJECT, COMPANY, SPRINT, NONE
}
