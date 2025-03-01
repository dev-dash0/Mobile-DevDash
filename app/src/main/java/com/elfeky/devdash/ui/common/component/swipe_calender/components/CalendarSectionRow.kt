package com.elfeky.devdash.ui.common.component.swipe_calender.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.swipe_calender.model.CalendarSection
import com.elfeky.devdash.ui.common.component.swipe_calender.model.ScheduleCalendarState
import java.time.format.DateTimeFormatter

@Composable
fun CalendarSectionRow(
    section: CalendarSection,
    state: ScheduleCalendarState,
    eventTimesVisible: Boolean
) {
    Column(Modifier.animateContentSize()) {
        val eventMap = section.events.map { event ->
            Triple(
                event,
                event.startDate.isAfter(state.startDateTime) && event.startDate.isBefore(state.endDateTime),
                event.endDate.isAfter(state.startDateTime) && event.endDate.isBefore(state.endDateTime),
            )
        }.filter { (event, startHit, endHit) ->
            startHit || endHit || (event.startDate.isBefore(state.startDateTime) && event.endDate.isAfter(
                state.endDateTime
            ))
        }

        Text(
            text = section.name,
            fontSize = 12.sp,
            fontWeight = W500,
            modifier = Modifier.padding(4.dp)
        )
        if (eventMap.isNotEmpty()) {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                eventMap.forEach { (event, startHit, endHit) ->
                    val (width, offsetX) = state.widthAndOffsetForEvent(
                        start = event.startDate,
                        end = event.endDate,
                        totalWidth = constraints.maxWidth
                    )

                    val shape = when {
                        startHit && endHit -> RoundedCornerShape(4.dp)
                        startHit -> RoundedCornerShape(
                            topStart = 4.dp,
                            bottomStart = 4.dp
                        )

                        endHit -> RoundedCornerShape(
                            topEnd = 4.dp,
                            bottomEnd = 4.dp
                        )

                        else -> RoundedCornerShape(4.dp)
                    }

                    Column(modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(with(LocalDensity.current) { width.toDp() })
                        .offset { IntOffset(offsetX, 0) }
                        .background(event.color, shape = shape)
                        .clip(shape)
                        .clickable { }
                        .padding(4.dp)
                    ) {
                        Text(
                            text = event.name,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        AnimatedVisibility(visible = eventTimesVisible) {
                            Text(
                                text = event.startDate.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " +
                                        event.endDate.format(DateTimeFormatter.ofPattern("HH:mm")),
                                fontSize = 12.sp,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}