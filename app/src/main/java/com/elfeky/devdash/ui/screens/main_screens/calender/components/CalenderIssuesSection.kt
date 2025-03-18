package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfeky.domain.model.dashboard.CalenderIssue

@Composable
fun CalenderIssuesSection(
    modifier: Modifier = Modifier,
    issues: List<CalenderIssue>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(issues.size) { index ->
            CalenderIssueChip(calenderIssue = issues[index])
        }
    }
}