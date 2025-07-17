package com.elfeky.devdash.ui.common.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.twotone.ChatBubble
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.card.component.CardTitle
import com.elfeky.devdash.ui.common.card.component.IssueCardLabels
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.common.issueList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.formatDisplayDate
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.issue.Issue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueCard(
    issue: Issue,
    user: UserProfile?,
    isPinned: Boolean,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onCommentsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    CardContainer(
        modifier = modifier,
        contentPadding = PaddingValues(),
        verticalSpaceBetweenItems = 0.dp,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.End)
        ) {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "More actions",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            if (isPinned) "Unpin" else "Pin",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        onPinClick()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = if (isPinned) R.drawable.ic_un_pin else R.drawable.ic_pin),
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Edit", color = MaterialTheme.colorScheme.onBackground) },
                    onClick = {
                        onEditClick()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete", color = MaterialTheme.colorScheme.onBackground) },
                    onClick = {
                        onDeleteClick()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_trash),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardTitle(
                    issueTitle = issue.title,
                    date = "${issue.startDate?.let { formatDisplayDate(it) } ?: "_"} | ${
                        issue.deadline?.let { formatDisplayDate(it) } ?: "_"
                    }"
                ) {
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(CircleShape),
                        thickness = 4.dp,
                        color = issue.priority.toPriority().color
                    )
                }
            }

            issue.labels?.let { IssueCardLabels(labels = it.trim().split(" ")) }

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                issue.assignedUsers.forEach { user ->
                    Avatar(user = user, size = 20.dp)
                }
            }

            if (!issue.attachmentPath.isNullOrEmpty()) {
                AsyncImage(
                    model = issue.attachment,
                    contentDescription = "Issue Attachment Preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        if (issue.assignedUsers.any { it.id == user?.id }) {
            IconButton(
                onClick = onCommentsClick,
                modifier = Modifier.align(Alignment.Start),
            ) {
                Icon(
                    Icons.TwoTone.ChatBubble,
                    contentDescription = "Comments Chat",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
private fun IssueCardPreview() {
    DevDashTheme {
        IssueCard(
            issue = issueList[0],
            user = userList[0],
            isPinned = false,
            onPinClick = {},
            onDeleteClick = {},
            onEditClick = {},
            onCommentsClick = {}
        )
    }
}
