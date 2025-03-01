package com.elfeky.devdash.ui.common.component.swipe_calender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.component.swipe_calender.model.CalendarEvent
import com.elfeky.devdash.ui.common.component.swipe_calender.model.CalendarSection
import com.elfeky.devdash.ui.theme.B400
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.G500
import com.elfeky.devdash.ui.theme.R500
import com.elfeky.devdash.ui.theme.Y500
import java.time.LocalDateTime

@Composable
fun ScheduleCalendarDemo() {
    val viewSpan = remember { mutableLongStateOf(48 * 3600L) }
    val eventTimesVisible = remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxHeight()) {
        Row {
            IconButton(onClick = {
                viewSpan.longValue = (viewSpan.longValue * 2).coerceAtMost(96 * 3600)
            }) {
                Icon(imageVector = Icons.Default.ZoomOut, contentDescription = "increase")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                viewSpan.longValue = (viewSpan.longValue / 2).coerceAtLeast(3 * 3600)
            }) {
                Icon(imageVector = Icons.Default.ZoomIn, contentDescription = "decrease")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                eventTimesVisible.value = !(eventTimesVisible.value)
            }) {
                Icon(imageVector = Icons.Default.HideImage, contentDescription = "decrease")
            }
        }

        val calendarState = rememberScheduleCalendarState()

        Spacer(modifier = Modifier.height(8.dp))

        ScheduleCalendar(
            state = calendarState,
            now = LocalDateTime.now().plusHours(8),
            eventTimesVisible = eventTimesVisible.value,
            sections = listOf(
                CalendarSection(
                    "Platform Schedule",
                    events = listOf(
                        CalendarEvent(
                            startDate = LocalDateTime.now().minusHours(6),
                            endDate = LocalDateTime.now().plusHours(12),
                            name = "Halil Ozercan",
                            description = "",
                            color = R500
                        ),
                        CalendarEvent(
                            startDate = LocalDateTime.now().plusHours(24),
                            endDate = LocalDateTime.now().plusHours(48),
                            name = "And Ani Calik",
                            description = "",
                            color = G500
                        )
                    )
                ),
                CalendarSection(
                    "Compose Schedule",
                    events = listOf(
                        CalendarEvent(
                            startDate = LocalDateTime.now().plusHours(6),
                            endDate = LocalDateTime.now().plusHours(12),
                            name = "Halil Ozercan",
                            description = "",
                            color = Y500
                        ),
                        CalendarEvent(
                            startDate = LocalDateTime.now().plusHours(17),
                            endDate = LocalDateTime.now().plusHours(27),
                            name = "Taha Kirca",
                            description = "",
                            color = B400
                        )
                    )
                )
            ),
            viewSpan = viewSpan.longValue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevDashTheme {
        ScheduleCalendarDemo()
    }
}