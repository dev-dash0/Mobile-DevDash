package com.elfeky.devdash.ui.common.dialogs.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DialogContainer(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmEnable: Boolean,
    modifier: Modifier = Modifier,
    title: String? = null,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    content: @Composable (LazyItemScope.() -> Unit),
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(.9f)
                .imePadding()
                .imeNestedScroll()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.onSecondary)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillParentMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onDismiss,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.outlineVariant),
                    ) {
                        Icon(Icons.Default.Close, "close")
                    }

                    title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    IconButton(
                        onConfirm,
                        enabled = confirmEnable,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiary,
                            disabledContentColor = MaterialTheme.colorScheme.outline
                        ),
                    ) {
                        Icon(Icons.Default.Done, "done")
                    }

                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(16.dp)
                ) {
                    content()
                }
            }
        }
    }
}


@Preview
@Composable
private fun DialogPreview() {
    DevDashTheme {
        DialogContainer(
            title = "Title",
            onDismiss = {},
            onConfirm = {},
            confirmEnable = true,
            content = {}
        )
    }
}