package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.component.avatar.AvatarStack
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.domain.model.account.UserProfile

@Composable
fun IssueCardFooter(
    priority: Priority,
    assignees: List<UserProfile>,
    containerColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(priority)
        AvatarStack(assignees, containerColor = containerColor)
    }
}