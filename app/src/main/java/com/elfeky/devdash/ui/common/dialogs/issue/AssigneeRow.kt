package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.AssigneeImage
import com.elfeky.devdash.ui.common.dialogs.model.assigneeList
import com.elfeky.devdash.ui.theme.BlueGray
import com.elfeky.devdash.ui.theme.LightBlueGray
import com.elfeky.devdash.ui.theme.White
import com.elfeky.devdash.ui.utils.dashBorder

@Composable
fun AssigneeRow(
    availableAssignees: MutableList<Pair<String, String>>,
    selectedAssignees: MutableList<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth(.9f)
            .clickable { expanded = !expanded }
            .padding(8.dp)
    ) {
        val overlap = (-12).dp
        Row {
            selectedAssignees.forEachIndexed { index, assignee ->
                AssigneeImage(assignee, index, overlap)
            }
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Add Assignee",
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (overlap * selectedAssignees.size).roundToPx(),
                            y = 0
                        )
                    }
                    .dashBorder(2.dp, 7.dp, 4.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(BlueGray)
                    .size(32.dp),
                tint = White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = LightBlueGray
        ) {
            availableAssignees.forEach { assignee ->
                DropdownMenuItem(
                    text = { Text(assignee.first, color = White) },
                    onClick = {
                        if (!selectedAssignees.remove(assignee)) {
                            selectedAssignees.add(assignee)
                        }
                    },
                    leadingIcon = {
                        AssigneeImage(assignee)
                    },
                    trailingIcon = {
                        if (selectedAssignees.contains(assignee)) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Selected Assignee",
                                tint = White
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AssigneeRowPreview() {
    AssigneeRow(
        availableAssignees = assigneeList.toMutableStateList(),
        selectedAssignees = mutableListOf()
    )
}