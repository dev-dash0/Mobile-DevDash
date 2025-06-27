package com.elfeky.devdash.ui.common.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.elfeky.devdash.ui.utils.formatDisplayDate

@Composable
fun StartEndDateText(
    startDate: String,
    endDate: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    style: TextStyle = MaterialTheme.typography.labelSmall
) {
    val formattedStartDate = remember(startDate) { formatDisplayDate(startDate) }
    val formattedEndDate = remember(endDate) { formatDisplayDate(endDate) }

    Text(
        text = "$formattedStartDate | $formattedEndDate",
        modifier = modifier,
        color = color,
        style = style
    )
}