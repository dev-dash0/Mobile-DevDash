package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.screens.main_screen.home.components.Label

@Composable
fun IssueCardLabels(labels: List<String>) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        labels.forEach { Label(it) }
    }
}