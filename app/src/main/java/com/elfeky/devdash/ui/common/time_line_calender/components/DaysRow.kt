package com.elfeky.devdash.ui.common.time_line_calender.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.time_line_calender.model.ScheduleCalendarState
import com.elfeky.devdash.ui.common.time_line_calender.model.daysBetween
import java.time.format.DateTimeFormatter

@Composable
fun DaysRow(
    state: ScheduleCalendarState,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        (state.startDateTime daysBetween state.endDateTime).forEach { localDateTime ->
            val (width, offsetX) = state.widthAndOffsetForEvent(
                start = localDateTime,
                end = localDateTime.plusDays(1),
                totalWidth = constraints.maxWidth
            )
            Column(modifier = Modifier
                .width(with(LocalDensity.current) { width.toDp() })
                .offset { IntOffset(offsetX, 0) }
                .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = localDateTime.format(DateTimeFormatter.ofPattern("MMM, dd")),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}