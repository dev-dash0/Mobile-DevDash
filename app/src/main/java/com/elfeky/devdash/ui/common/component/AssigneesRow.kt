package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.component.AssigneeAvatar
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel

@Composable
fun AssigneesRow(assignees: List<UserUiModel>, containerColor: Color) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-12).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        assignees.forEach { assignee ->
            AssigneeAvatar(assignee, containerColor)
        }
    }
}