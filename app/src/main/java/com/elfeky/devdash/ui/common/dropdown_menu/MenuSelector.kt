package com.elfeky.devdash.ui.common.dropdown_menu

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.component.StatusIndicator
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuOption
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status.Companion.projectStatusList
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuSelector(
    items: List<MenuOption>,
    selectedItem: MenuOption,
    onItemSelected: (MenuOption) -> Unit,
    modifier: Modifier = Modifier,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        TextButton(
            onClick = { },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            IconText(selectedItem)
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(IntrinsicSize.Min),
            shape = MaterialTheme.shapes.medium,
            containerColor = menuTextColor
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    leadingIcon = {
                        if (item is Status) StatusIndicator(item)
                        else
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text,
                                tint = item.color
                            )
                    },
                    text = {
                        Text(
                            text = item.text,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MenuSelectorPreview() {
    var selectedItem by remember { mutableStateOf(projectStatusList[0]) }

    DevDashTheme {
        MenuSelector(
            items = projectStatusList,
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it as Status },
        )
    }
}
