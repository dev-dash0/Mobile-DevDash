package com.elfeky.devdash.ui.common.dialogs.join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentHorizontal
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun InviteDialog(
    onDismiss: () -> Unit,
    onConfirm: (code: String, role: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Admin") }
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Admin", "Developer", "Project Manager")

    DialogContainer(
        title = "Invite Member",
        onDismiss = onDismiss,
        onConfirm = { onConfirm(email, selectedRole) },
        confirmEnable = email.isNotEmpty(),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabelledContentVertical("Email") {
                InputField(
                    value = email,
                    onValueChange = { email = it },
                    placeholderText = "",
                    isEmail = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                )
            }

            LabelledContentHorizontal("Role: ", Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(.75f)) {
                    TextButton({ expanded = true }) {
                        Text(
                            text = selectedRole,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = "Dropdown arrow",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        roles.forEach { role ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        role,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                },
                                onClick = { selectedRole = role; expanded = false })
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InviteDialogPreview() {
    DevDashTheme {
        InviteDialog(
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}