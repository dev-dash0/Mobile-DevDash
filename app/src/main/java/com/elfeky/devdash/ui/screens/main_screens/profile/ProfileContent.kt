package com.elfeky.devdash.ui.screens.main_screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.screens.main_screens.profile.components.ChangePasswordDialog
import com.elfeky.devdash.ui.screens.main_screens.profile.components.EditAccountDialog
import com.elfeky.devdash.ui.screens.main_screens.profile.components.ProfileHeader
import com.elfeky.devdash.ui.screens.main_screens.profile.components.ProfileInfoCard
import com.elfeky.devdash.ui.screens.main_screens.profile.components.ProfileOption
import com.elfeky.devdash.ui.screens.main_screens.profile.components.SureAlertDialog
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfile
import com.elfeky.domain.model.account.UserProfileRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    user: UserProfile,
    onImageChanged: (Any?) -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onChangePassword: (current: String, new: String) -> Unit,
    onEditAccount: (request: UserProfileRequest) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditAccountDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            ProfileHeader(
                imageUrl = user.imageUrl,
                userName = "${user.firstName} ${user.lastName}",
                userEmail = user.email,
                onImageChanged = onImageChanged
            )
        }

        item {
            ProfileInfoCard(user = user)
        }

        item {
            Column {
                ProfileOption(
                    icon = Icons.Default.Edit,
                    text = "Edit Account",
                    onClick = { showEditAccountDialog = true }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                ProfileOption(
                    icon = Icons.Default.Lock,
                    text = "Change Password",
                    onClick = { showChangePasswordDialog = true }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text("Log out")
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(.8f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Text("Delete Account")
                }
            }

        }
    }

    if (showDeleteDialog) {
        SureAlertDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onDeleteAccount
                showDeleteDialog = false
            },
            action = "Delete Account",
            error = ""
        )
    }

    if (showEditAccountDialog) {
        EditAccountDialog(
            user = user,
            onConfirm = { firstName, lastName, userName, email, phoneNumber, birthday ->
                onEditAccount(
                    UserProfileRequest(
                        firstName = firstName,
                        lastName = lastName,
                        userName = userName,
                        imageUrl = user.imageUrl,
                        phoneNumber = phoneNumber,
                        birthday = birthday,
                        email = email
                    )
                )
                showEditAccountDialog = false
            },
            onDismiss = { showEditAccountDialog = false }
        )
    }

    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showChangePasswordDialog = false },
            onConfirm = { currentPassword, newPassword ->
                onChangePassword(currentPassword, newPassword)
                showChangePasswordDialog = false
            },
            error = ""
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    DevDashTheme {
        ProfileContent(
            user = userList[0],
            onImageChanged = {},
            onLogout = { },
            onDeleteAccount = { },
            onChangePassword = { _, _ -> },
            onEditAccount = { }
        )
    }
}