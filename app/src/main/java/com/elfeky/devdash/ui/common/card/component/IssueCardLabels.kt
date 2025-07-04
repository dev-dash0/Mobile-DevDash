package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.screens.main_screens.home.components.Label

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IssueCardLabels(labels: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        labels.forEach { label ->
            Label(label = label)
        }
    }
}