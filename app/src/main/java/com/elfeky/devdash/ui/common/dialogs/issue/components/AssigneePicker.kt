package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.component.AssigneeAvatar
import com.elfeky.devdash.ui.common.dialogs.issue.model.UserUiModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.dashBorder

@Composable
fun AssigneePicker(
    availableAssignees: List<UserUiModel>,
    selectedAssignees: MutableList<UserUiModel>,
    onAssigneeSelected: (UserUiModel) -> Unit,
    modifier: Modifier = Modifier,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var expanded by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(48.dp) }
    val local = LocalDensity.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy((-12).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectedAssignees.forEach { assignee ->
            AssigneeAvatar(assignee)
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(R.drawable.user_add_ic),
                contentDescription = "Add Assignee",
                modifier = Modifier
                    .dashBorder(1.dp, 7.dp, 4.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(4.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = itemHeight * 5),
            offset = DpOffset(0.dp, 0.dp),
            scrollState = rememberScrollState(),
            shape = MaterialTheme.shapes.medium,
            containerColor = menuTextColor
        ) {
            availableAssignees.forEachIndexed { index, assignee ->
                var selected by remember { mutableStateOf(false) }
                DropdownMenuItem(
                    text = {
                        Text(
                            assignee.name,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        selected = !selected
                        onAssigneeSelected(assignee)
                    },
                    leadingIcon = {
                        AssigneeAvatar(assignee)
                    },
                    trailingIcon = {
                        if (selected) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Selected Assignee",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        if (index == 0) itemHeight =
                            with(local) { layoutCoordinates.size.height.toDp() }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AssigneeRowPreview() {
    val selectedAssignees = mutableListOf<UserUiModel>()
    DevDashTheme {
        AssigneePicker(
            availableAssignees = assigneeList,
            selectedAssignees = selectedAssignees,
            onAssigneeSelected = {
                if (!selectedAssignees.remove(it)) {
                    selectedAssignees.add(it)
                }
            }
        )
    }
}