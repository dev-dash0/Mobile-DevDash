package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status

@Composable
fun IconText(
    selectedItem: MenuOption,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedItem is Status) StatusIndicator(selectedItem)
        else
            Icon(
                imageVector = selectedItem.icon,
                contentDescription = null,
                tint = selectedItem.color
            )
        Text(text = selectedItem.text)
    }
}

@Preview
@Composable
private fun IconTextPreview() {
    IconText(Status.Planning)
}