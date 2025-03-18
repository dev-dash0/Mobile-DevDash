package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status

@Composable
fun StatusIndicator(
    status: Status,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Outlined.Circle,
            status.text,
            tint = status.color
        )
        status.percentage?.let {
            Canvas(modifier = Modifier.size(12.dp)) {
                drawArc(
                    color = status.color,
                    startAngle = -90f,
                    useCenter = true,
                    sweepAngle = it * 360f,
                )
            }
        } ?: Icon(
            imageVector = when (status) {
                Status.Completed -> Icons.Default.Check
                Status.Postponed -> Icons.Outlined.Error
                else -> Icons.Default.Close
            },
            contentDescription = "",
            modifier = Modifier.size(12.dp),
            tint = status.color
        )
    }
}

@Preview
@Composable
private fun StatusIndicatorPreview() {
    StatusIndicator(Status.Planning)
}