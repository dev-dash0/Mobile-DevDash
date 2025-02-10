package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elfeky.devdash.ui.common.dialogs.issue.components.DialogTopBar
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.White

@Composable
fun FullScreenDialog(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    title: String,
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
                    onSubmit = onSubmit
                )
            },
            containerColor = DarkBlue,
            contentColor = White,
            content = content
        )
    }
}