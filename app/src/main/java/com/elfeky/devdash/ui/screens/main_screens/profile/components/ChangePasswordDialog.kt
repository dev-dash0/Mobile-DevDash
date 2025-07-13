package com.elfeky.devdash.ui.screens.main_screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.InputField

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (currentPassword:String, newPassword:String) -> Unit,
    error: String
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        title = {
            Text("Update your Password", color = MaterialTheme.colorScheme.onBackground)
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    error,
                    color = MaterialTheme.colorScheme.error
                )
                InputField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    placeholderText = "Current Password",
                    modifier = Modifier.fillMaxWidth(),
                    isPassword = true
                )
                Spacer(Modifier.height(8.dp))
                InputField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholderText = "New Password",
                    modifier = Modifier.fillMaxWidth(),
                    isPassword = true
                )
            }

        },
        confirmButton = {
            Button(
                onClick = { onConfirm(currentPassword, newPassword) },
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)


            ) {
                Text("Update", color = MaterialTheme.colorScheme.onBackground)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface)
            ) {
                Text("Dismiss", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    )
}

