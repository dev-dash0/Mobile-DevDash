package com.elfeky.devdash.ui.common.dropdown_menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.IconText
import com.elfeky.devdash.ui.common.dialogs.typeList
import com.elfeky.devdash.ui.common.dropdown_menu.model.MenuUiModel
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun MenuSelector(
    items: List<MenuUiModel>,
    selectedItem: MenuUiModel,
    onItemSelected: (MenuUiModel) -> Unit,
    modifier: Modifier = Modifier,
    menuTextColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    var expanded by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(48.dp) }
    val local = LocalDensity.current

    Box(modifier = modifier) {
        TextButton(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            IconText(selectedItem.icon, selectedItem.color, selectedItem.text)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(max = itemHeight * 5),
            offset = DpOffset(0.dp, 0.dp),
            shape = MaterialTheme.shapes.medium,
            containerColor = menuTextColor
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    leadingIcon = {
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
                    onClick = { onItemSelected(item); expanded = false },
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        if (index == 0) itemHeight =
                            with(local) { layoutCoordinates.size.height.toDp() }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
private fun MenuSelectorPreview() {
    var selectedItem by remember { mutableStateOf(typeList[1]) }

    DevDashTheme {
        MenuSelector(
            items = typeList,
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
        )
    }
}
