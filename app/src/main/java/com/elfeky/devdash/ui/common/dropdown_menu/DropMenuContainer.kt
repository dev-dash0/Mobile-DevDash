package com.elfeky.devdash.ui.common.dropdown_menu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropMenuContainer(
    modifier: Modifier = Modifier,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    content: @Composable (ExposedDropdownMenuBoxScope.() -> Unit),
    dropBoxContent: @Composable (ColumnScope.() -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        content()

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 250.dp)
                .width(IntrinsicSize.Min),
            shape = MaterialTheme.shapes.medium,
            containerColor = menuTextColor
        ) {
            dropBoxContent()
        }
    }
}