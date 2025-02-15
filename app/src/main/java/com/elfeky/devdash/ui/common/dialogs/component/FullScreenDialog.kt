package com.elfeky.devdash.ui.common.dialogs.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun FullScreenDialog(
    title: String,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    submitEnable: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true
        )
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                DialogTopBar(
                    title = title,
                    onDismiss = onDismiss,
                    onSubmit = onSubmit,
                    submitEnable = submitEnable
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            content = content
        )
    }
}

@Preview
@Composable
private fun FullScreenDialogPreview() {
    DevDashTheme { FullScreenDialog("Title", {}, {}, true) { } }
}