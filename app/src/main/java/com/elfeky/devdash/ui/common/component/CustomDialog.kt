package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elfeky.devdash.ui.theme.BlueGray
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.IceBlue
import com.elfeky.devdash.ui.utils.cancelButtonColor
import com.elfeky.devdash.ui.utils.secondaryButtonColor

@Composable
fun CustomAlertDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    title: String? = null,
    modifier: Modifier = Modifier,
    confirmEnable: Boolean = true,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = modifier
                .imePadding()
                .fillMaxWidth()
                .background(color = BlueGray, shape = MaterialTheme.shapes.medium)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = IceBlue
                )
            }

            content()

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton(
                    text = "Cancel",
                    onClick = onCancel,
                    buttonColor = cancelButtonColor,
                    modifier = Modifier.weight(1f)
                )
                CustomButton(
                    text = "Confirm",
                    onClick = onConfirm,
                    buttonColor = secondaryButtonColor,
                    enabled = confirmEnable,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview
@Composable
private fun DialogPreview() {
    DevDashTheme {
        CustomAlertDialog(
            title = "Title",
            onCancel = {},
            onConfirm = {},
            content = {}
        )
    }
}