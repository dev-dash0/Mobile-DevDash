package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IssueCardLabels(labels: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        labels.forEach { label ->
            Text(
                text = label,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}