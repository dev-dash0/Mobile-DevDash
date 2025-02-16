package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.component.StatusIndicator

@Composable
fun IssueCardTitle(status: Status, issueTitle: String, textStyle: TextStyle) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusIndicator(status)
        Text(text = issueTitle, style = textStyle)
    }
}