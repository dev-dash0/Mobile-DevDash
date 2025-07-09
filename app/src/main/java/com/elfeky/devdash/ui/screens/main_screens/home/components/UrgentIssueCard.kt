package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.card.component.CardContainer
import com.elfeky.devdash.ui.common.dropdown_menu.model.toPriority
import com.elfeky.devdash.ui.utils.formatDisplayDate
import com.elfeky.domain.model.issue.Issue

@Composable
fun UrgentIssueCard(issue: Issue, modifier: Modifier = Modifier) {
    CardContainer(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            VerticalDivider(
                modifier = Modifier.clip(CircleShape),
                color = issue.priority.toPriority().color,
                thickness = 6.dp
            )

            Text(
                text = issue.title,
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = issue.deadline?.let { formatDisplayDate(it) } ?: "",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}