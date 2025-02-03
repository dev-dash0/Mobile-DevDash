package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dialogs.model.labelList
import com.elfeky.devdash.ui.theme.Lavender
import com.elfeky.devdash.ui.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LabelRow(
    labelList: List<String>,
    onLabelSelected: (String) -> Unit,
    selectedLabels: SnapshotStateList<String>,
    onAddLabelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        labelList.forEach { label ->
            FilterChip(
                onClick = { onLabelSelected(label) },
                label = {
                    Text(
                        text = label,
                        color = White,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = selectedLabels.contains(label),
                leadingIcon = if (selectedLabels.contains(label)) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "selected",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Lavender.copy(alpha = .6f)
                ),
                border = null
            )
        }

        IconButton(
            onClick = { onAddLabelClick() },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .background(
                    shape = MaterialTheme.shapes.small,
                    color = Lavender.copy(alpha = .8f)
                )
                .size(32.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Label",
                tint = White
            )
        }
    }
}

@Preview
@Composable
private fun LabelRowPreview() {
    LabelRow(
        labelList = labelList,
        onLabelSelected = {},
        selectedLabels = emptyList<String>().toMutableStateList(),
        onAddLabelClick = {}
    )
}