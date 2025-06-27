package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.dropdown_menu.model.toProjectStatus
import com.elfeky.devdash.ui.screens.details_screens.project.ProjectDetailsUiState
import com.elfeky.devdash.ui.utils.formatDisplayDate

@Composable
fun InfoPage(
    state: ProjectDetailsUiState,
    modifier: Modifier = Modifier,
    onRemoveMemberClick: (Int) -> Unit
) {
    val formattedStartDate =
        remember(state) { formatDisplayDate(state.project?.startDate.toString()) }
    val formattedEndDate = remember(state) { formatDisplayDate(state.project?.endDate.toString()) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        LabelledContentHorizontal("Priority") {
            IconText(
                state.project?.priority.toPriority(),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(state.project?.priority.toPriority().color.copy(alpha = .2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        LabelledContentHorizontal("Status") {
            IconText(
                state.project?.status.toProjectStatus(),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(state.project?.status.toPriority().color.copy(alpha = .2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        LabelledContentHorizontal("Start Date") {
            Text(
                text = formattedStartDate,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f)
            )
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
            )
        }

        LabelledContentHorizontal("End Date") {
            Text(
                text = formattedEndDate,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f)
            )

            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
            )
        }
//        LabelledContentHorizontal("Members:") {
//            Row(modifier = Modifier.weight(.75f), horizontalArrangement = Arrangement.End) {
//                MembersMenu(
//                    state.project?.joinedUsers ?: emptyList(),
//                    state.project?.role == "Admin",
//                    onRemoveMemberClick
//                )
//            }
//        }

        LabelledContentVertical("Description:") {
            state.project?.let {
                Text(
                    it.description,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .8f),
                    fontSize = 14.sp,
                )
            }
        }
    }
}