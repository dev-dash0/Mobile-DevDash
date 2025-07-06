package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardTitle(
    date: String,
    issueTitle: String,
    modifier: Modifier = Modifier,
    dateTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
    leadingIcon: @Composable (RowScope.() -> Unit)
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = date,
            modifier = Modifier.align(Alignment.End),
            style = dateTextStyle,
            color = MaterialTheme.colorScheme.secondary
        )

        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon()

            Text(text = issueTitle, style = titleTextStyle)
        }
    }
}