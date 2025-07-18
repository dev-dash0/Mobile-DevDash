package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.common.component.avatar.AvatarStack
import com.elfeky.devdash.ui.common.dropdown_menu.DropMenuContainer
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.avatarModifier
import com.elfeky.devdash.ui.utils.dashBorder
import com.elfeky.domain.model.account.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPicker(
    availableUsers: List<UserProfile>,
    selectedUsers: List<UserProfile>,
    onUserSelected: (UserProfile) -> Unit,
    modifier: Modifier = Modifier,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var expanded by remember { mutableStateOf(false) }

    DropMenuContainer(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        onDismissRequest = { expanded = false },
        modifier = modifier,
        menuTextColor = menuTextColor,
        content = {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarStack(
                    selectedUsers,
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )

                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .dashBorder(2.dp, 7.dp, 4.dp)
                        .padding(4.dp)
                        .avatarModifier(MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.user_add_ic),
                        contentDescription = "Add Assignee",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    ) {
        availableUsers.forEach { user ->
            DropdownMenuItem(
                text = { Text(user.userName, color = MaterialTheme.colorScheme.onBackground) },
                onClick = { onUserSelected(user);expanded = false },
                leadingIcon = { Avatar(user) },
                trailingIcon = {
                    if (selectedUsers.contains(user)) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Selected Assignee",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}

@Preview
@Composable
private fun UserRowPreview() {
    val selectedUsers = userList.take(3).toMutableList()
    DevDashTheme {
        UserPicker(
            availableUsers = userList,
            selectedUsers = emptyList(),
            onUserSelected = {
                if (!selectedUsers.remove(it)) {
                    selectedUsers.add(it)
                }
            }
        )
    }
}