package com.elfeky.devdash.ui.common.dialogs.issue.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.Pink
import com.elfeky.devdash.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTopBar(title: String, onDismiss: () -> Unit, onSubmit: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        },
        actions = {
            IconButton(onClick = onSubmit) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Submit")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBlue,
            titleContentColor = White,
            navigationIconContentColor = LightGray,
            actionIconContentColor = Pink
        )
    )
}