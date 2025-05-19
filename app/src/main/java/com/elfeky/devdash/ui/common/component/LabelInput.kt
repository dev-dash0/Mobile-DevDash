package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.common.dropdown_menu.DropMenuContainer
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelInput(
    options: List<String>,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter label",
    onSelectedOptionsChange: (List<String>) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    val selectedOptions = remember { mutableStateListOf<String>() }

    val filteredOptions = remember(textState, options) {
        if (textState.isEmpty()) {
            emptyList()
        } else {
            options.filter { it.contains(textState, ignoreCase = true) }
                .sortedWith(compareBy { !it.equals(textState, ignoreCase = true) })
        }
    }

    DropMenuContainer(
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            ) {
                InputField(
                    value = textState,
                    onValueChange = { newValue ->
                        textState = newValue
                    },
                    placeholderText = placeholder,
                    modifier = Modifier.fillMaxWidth()
                )
                if (selectedOptions.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(selectedOptions) { option ->
                            AssistChip(
                                onClick = {
                                    selectedOptions.remove(option)
                                    onSelectedOptionsChange(selectedOptions.toList())
                                },
                                label = {
                                    Text(
                                        text = option,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Remove",
                                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = AssistChipDefaults.assistChipColors(
                                    labelColor = MaterialTheme.colorScheme.onBackground,
                                    leadingIconContentColor = MaterialTheme.colorScheme.onBackground,
                                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .6f)
                                )
                            )
                        }
                    }
                }
            }
        }) {
        if (filteredOptions.isNotEmpty()) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        if (!selectedOptions.contains(option)) {
                            selectedOptions.add(option)
                            onSelectedOptionsChange(selectedOptions.toList())
                        }
                        textState = ""

                    },
                    text = {
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        } else if (textState.isNotEmpty()) {
            DropdownMenuItem(
                onClick = {
                    val newOption = textState.trim()
                    if (newOption.isNotEmpty() && !selectedOptions.contains(newOption)) {
                        selectedOptions.add(newOption)
                        onSelectedOptionsChange(selectedOptions.toList())
                    }
                    textState = ""
                },
                text = {
                    Text(
                        text = "Add \"${textState}\"",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}

@Preview
@Composable
private fun LabelInputPreview() {
    DevDashTheme {
        LabelInput(labelList) {}
    }
}