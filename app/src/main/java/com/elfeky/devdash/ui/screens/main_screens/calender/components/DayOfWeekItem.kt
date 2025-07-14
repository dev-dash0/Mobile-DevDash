package com.elfeky.devdash.ui.screens.main_screens.calender.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DevDashTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayOfWeekItem(
    day: DayState,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val dayOfWeekName = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val backgroundColor =
        if (day.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier
            .width(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onDayClick(day.date) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = dayOfWeekName.uppercase(),
                color = textColor.copy(alpha = if (day.isSelected) 1f else 0.7f),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            if (day.hasIndicator) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(
                            if (day.isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.tertiary
                        )
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun DayOfWeekItemPreview() {
    DevDashTheme {
        DayOfWeekItem(
            day = DayState(
                date = LocalDate.now(),
                isSelected = false,
                hasIndicator = false
            ),
            onDayClick = {}
        )
    }
}

@Preview
@Composable
fun DayOfWeekItemSelectedPreview() {
    DevDashTheme {
        DayOfWeekItem(
            day = DayState(
                date = LocalDate.now(),
                isSelected = true,
                hasIndicator = false
            ),
            onDayClick = {}
        )
    }
}

@Preview
@Composable
fun DayOfWeekItemWithIndicatorPreview() {
    DevDashTheme {
        DayOfWeekItem(
            day = DayState(
                date = LocalDate.now(),
                isSelected = false,
                hasIndicator = true
            ),
            onDayClick = {}
        )
    }
}