package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.avatar.Avatar
import com.elfeky.devdash.ui.common.component.avatar.MembersAvatar
import com.elfeky.devdash.ui.common.dropdown_menu.DropMenuContainer
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfile

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MembersMenu(
    members: List<UserProfile>,
    isOwner: Boolean,
    onRemoveMemberClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    maxShownMembers: Int = 5,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
) {
    var expanded by remember { mutableStateOf(false) }

    DropMenuContainer(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        onDismissRequest = { expanded = false },
        modifier = modifier,
        menuTextColor = menuTextColor,
        content = {
            MembersAvatar(
                users = members,
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                maxShownUsers = maxShownMembers
            )
        }
    ) {
        members.forEach { member ->
            DropdownMenuItem(
                text = {
                    Text(
                        member.userName,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                onClick = { expanded = false },
                leadingIcon = { Avatar(member) },
                trailingIcon = {
                    if (isOwner) {
                        IconButton({ onRemoveMemberClick(member.id) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "delete Member",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}

@Preview
@Composable
private fun MembersMenuPreview() {
    DevDashTheme {
        MembersMenu(userList, false, {})
    }
}