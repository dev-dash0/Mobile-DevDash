package com.elfeky.devdash.ui.screens.main_screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.domain.model.account.UserProfile

@Composable
fun EditAccountDialog(
    user: UserProfile,
    onConfirm: (firstName: String, lastName: String, userName: String, email: String, phoneNumber: String, birthday: String) -> Unit,
    onDismiss: () -> Unit
) {
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var userName by remember { mutableStateOf(user.userName) }
    var email by remember { mutableStateOf(user.email) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var birthday by remember { mutableStateOf(user.birthday) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(.9f),
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Edit Account", fontWeight = FontWeight.Bold) },
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                LabelledContentVertical("First Name") {
                    InputField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                LabelledContentVertical("Last Name") {
                    InputField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                LabelledContentVertical("User Name") {
                    InputField(
                        value = userName,
                        onValueChange = { userName = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                LabelledContentVertical("Email") {
                    InputField(
                        value = email,
                        onValueChange = { email = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                LabelledContentVertical("Phone Number") {
                    InputField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                LabelledContentVertical("Birthday") {
                    InputField(
                        value = birthday,
                        onValueChange = { birthday = it },
                        placeholderText = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        firstName,
                        lastName,
                        userName,
                        email,
                        phoneNumber,
                        birthday
                    )
                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Cancel")
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}