package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.common.component.AssigneesRow
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel

@Composable
fun IssueCardFooter(
    priorityTint: Color,
    assignees: List<UserUiModel>,
    containerColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(Icons.Default.Flag, tint = priorityTint, "High")
        AssigneesRow(assignees, containerColor)
    }
}