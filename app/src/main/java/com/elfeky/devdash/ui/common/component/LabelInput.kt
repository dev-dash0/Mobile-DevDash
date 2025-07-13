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
    labels: String?,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter label",
    onAddLabel: (String) -> Unit,
) {
    var inputText by remember { mutableStateOf("") }
    var currentLabels by remember {
        mutableStateOf(
            labels?.trim()?.split(" ")?.filter { it.isNotBlank() } ?: emptyList())
    }

    Column(modifier = modifier.fillMaxWidth()) {
        InputField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholderText = placeholder,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (inputText.isNotBlank()) {
                        currentLabels = currentLabels + inputText.trim()
                        onAddLabel(currentLabels.joinToString(" "))
                        inputText = ""
                    }
                }
            )
        )

        if (currentLabels.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                currentLabels.forEach { label ->
                    AssistChip(
                        onClick = {
                            currentLabels = currentLabels.filterNot { it == label }
                            onAddLabel(currentLabels.joinToString(" "))
                        },
                        label = {
                            Text(
                                text = label,
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
        LabelInput("hi developer") {}
    }
}