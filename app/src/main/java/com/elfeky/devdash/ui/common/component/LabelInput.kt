package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LabelInput(
    modifier: Modifier = Modifier,
    placeholder: String = "Enter label",
    onAddLabel: (String) -> Unit
) {
    var textState by remember { mutableStateOf("") }
    var labels by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        InputField(
            value = textState,
            onValueChange = { newValue ->
                textState = newValue
            },
            placeholderText = placeholder,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (textState.isNotBlank()) {
                        labels = labels + " " + textState.trim()
                        onAddLabel(labels)
                        textState = ""
                    }
                }
            )
        )

        if (labels.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                labels.split(" ").forEach { option ->
                    AssistChip(
                        onClick = {
//                            labels = labels.filterNot { it == option }
//                            onAddLabel(labels)
                        },
                        label = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.onBackground,
                            trailingIconContentColor = MaterialTheme.colorScheme.onBackground,
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .6f)
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LabelInputPreview() {
    DevDashTheme {
        LabelInput {}
    }
}