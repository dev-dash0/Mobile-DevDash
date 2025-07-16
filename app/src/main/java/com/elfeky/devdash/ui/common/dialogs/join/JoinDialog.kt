package com.elfeky.devdash.ui.common.dialogs.join

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.InputField
import com.elfeky.devdash.ui.common.dialogs.component.DialogContainer
import com.elfeky.devdash.ui.common.dialogs.component.LabelledContentVertical
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun JoinDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (code: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var code by remember { mutableStateOf("") }

    DialogContainer(
        title = title,
        onDismiss = onDismiss,
        onConfirm = { onConfirm(code) },
        confirmEnable = code.isNotEmpty(),
        modifier = modifier
    ) {
        LabelledContentVertical("Code") {
            InputField(
                value = code,
                onValueChange = { code = it },
                placeholderText = "",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun JoinDialogPreview() {
    DevDashTheme {
        JoinDialog(
            title = "Join Company",
            onDismiss = {},
            onConfirm = {}
        )
    }
}