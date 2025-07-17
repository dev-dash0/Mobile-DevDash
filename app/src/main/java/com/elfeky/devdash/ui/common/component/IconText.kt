package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun IconText(
    selectedItem: MenuOption,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color =
            if (selectedItem is Status) MaterialTheme.colorScheme.secondary else selectedItem.color
        if (selectedItem is Status) StatusIndicator(selectedItem, size = iconSize)
        else
            Icon(
                imageVector = selectedItem.icon,
                contentDescription = null,
                tint = selectedItem.color,
                modifier = Modifier.size(iconSize)
            )
        Text(
            text = selectedItem.text,
            color = color,
            style = textStyle
        )
    }
}

@Preview
@Composable
private fun IconTextPreview() {
    DevDashTheme { IconText(Status.Planning) }
}