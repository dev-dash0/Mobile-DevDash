package com.elfeky.devdash.ui.screens.extra_screens.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.notificationList
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.formatDisplayDate
import com.elfeky.domain.model.notification.Notification

@Composable
fun NotificationCard(item: Notification, modifier: Modifier = Modifier) {
    CardContainer(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = formatDisplayDate(item.createdAt),
            modifier = Modifier.align(Alignment.End),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = item.message,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun NotificationCardPreview() {
    DevDashTheme {
        NotificationCard(notificationList[0])
    }
}