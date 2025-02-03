package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.dialogs.model.DropDownMenuDataModel
import com.elfeky.devdash.ui.common.dialogs.model.statusList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.LightBlueGray

@Composable
fun CustomDropdownMenu(
    items: List<DropDownMenuDataModel>,
    selectedItem: DropDownMenuDataModel,
    onItemSelected: (DropDownMenuDataModel) -> Unit,
    modifier: Modifier = Modifier,
    menuTextColor: Color = LightBlueGray
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier,
        ) {
            Icon(
                painter = painterResource(selectedItem.icon),
                contentDescription = selectedItem.text,
                tint = selectedItem.tint
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = menuTextColor
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    leadingIcon = {
                        if (item.icon != 0) Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.text,
                            tint = item.tint
                        )
                    },
                    text = { Text(text = item.text, color = item.tint) },
                    onClick = { onItemSelected(item); expanded = false }
                )
            }
        }
    }
}


@Preview
@Composable
private fun DropDownMenuPreview() {
    var selectedItem by remember { mutableStateOf(statusList[0]) }

    DevDashTheme {
        CustomDropdownMenu(
            items = statusList,
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it },
        )
    }
}
