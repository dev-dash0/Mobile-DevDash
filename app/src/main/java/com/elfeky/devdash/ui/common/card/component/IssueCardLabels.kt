package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IssueCardLabels(labels: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        labels.forEach { label ->
            Text(label)
        }
    }
}