package com.elfeky.devdash.ui.screens.details_screens.sprint.model

import com.elfeky.domain.model.issue.Issue

data class Board(
    val title: String,
    val issues: List<Issue>
)
