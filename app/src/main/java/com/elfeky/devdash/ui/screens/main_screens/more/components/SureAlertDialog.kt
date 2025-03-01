package com.elfeky.devdash.ui.screens.main_screens.more.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun SureAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    action: String,
    error: String
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    error,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    "Are you sure you want to $action?",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)


            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.onBackground)
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

@Preview
@Composable
private fun SureAlertDialogPreview() {
    DevDashTheme {
        SureAlertDialog(onConfirm = {}, onDismiss = {}, action = "Logout", error = "")
    }
}