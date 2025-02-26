package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CircularProgressCard(
            title = "Total Projects",
            progress = 0.5f,
            modifier = Modifier.weight(1f),
            colors = listOf(Color.Blue, Color.Cyan)
        )

        CircularProgressCard(
            title = "Total Issues Completed",
            progress = 0.75f,
            modifier = Modifier.weight(1f),
            colors = listOf(Color.Cyan, Color.Blue)
        )

        CircularProgressCard(
            title = "Total Issues In Progress",
            progress = 0.25f,
            modifier = Modifier.weight(1f),
            colors = listOf(Color.Magenta, Color.Blue)
        )

        CircularProgressCard(
            title = "Total Issues Overdue",
            progress = 0.75f,
            modifier = Modifier.weight(1f),
            colors = listOf(Color.Red, Color.Gray)
        )
    }
}