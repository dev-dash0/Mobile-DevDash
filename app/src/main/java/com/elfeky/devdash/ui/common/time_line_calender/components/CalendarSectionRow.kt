package com.elfeky.devdash.ui.common.time_line_calender.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.time_line_calender.model.CalendarSection
import com.elfeky.devdash.ui.common.time_line_calender.model.ScheduleCalendarState
import java.time.format.DateTimeFormatter

private var eventHeight = 0.dp
private val eventCornerRadius = 12.dp
fun Modifier.setEventHeight(height: androidx.compose.ui.unit.Dp): Modifier =
    this.then(Modifier
        .height(height)
        .also {
            eventHeight = height
        }
    )

@Composable
fun CalendarSectionRow(
    modifier: Modifier = Modifier,
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

        BoxWithConstraints(Modifier.fillMaxWidth()) {
            Column(modifier.fillMaxWidth()) {
                if (eventMap.isNotEmpty()) {
                    eventMap.forEachIndexed { index, (event, startHit, endHit) ->
                        BoxWithConstraints(modifier.fillMaxWidth()) {
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
                                .width(with(LocalDensity.current) { width.toDp() })
                                .offset { IntOffset(offsetX, index * 40) }
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

    }
}

@Composable
fun calendarSectionItem(
    event: com.elfeky.devdash.ui.common.time_line_calender.model.CalendarEvent,
    startHit: Boolean,
    endHit: Boolean,
    state: ScheduleCalendarState,
    width: Int,
    offset: Int,
    eventTimesVisible: Boolean
): Int {
    val shape =
        when { // Determine shape based on whether the event hits the start and/or end of the visible period
            startHit && endHit -> RoundedCornerShape(eventCornerRadius)
            startHit -> RoundedCornerShape(
                topStart = eventCornerRadius,
                bottomStart = eventCornerRadius
            )

            endHit -> RoundedCornerShape(
                topEnd = eventCornerRadius,
                bottomEnd = eventCornerRadius
            )

            else -> RoundedCornerShape(eventCornerRadius)
        }

    val (eventWidth, offsetX) = state.widthAndOffsetForEvent(
        start = event.startDate,
        end = event.endDate,
        totalWidth = width
    )

    val animatedOffset =
        animateDpAsState(targetValue = offset.dp, label = "Animated Event offset").value

    var eventCurrentHeight = 0

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(with(LocalDensity.current) { eventWidth.toDp() })
            .offset { IntOffset(offsetX, animatedOffset.roundToPx()) }
            .background(event.color, shape = shape)
            .clip(shape)
            .clickable {

            }
            .onGloballyPositioned { eventCurrentHeight = it.size.height }
    ) {
        Text(
            text = event.name,
            color = Color.White,
            maxLines = 1, // Limit to one line
            overflow = TextOverflow.Ellipsis, // Indicate overflow with ellipsis
            textAlign = TextAlign.Center, // Center the text
            fontWeight = W600,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )


        AnimatedVisibility(visible = eventTimesVisible) { // Conditionally show time range
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
    return offset + eventCurrentHeight // Update the vertical offset for the next event
}

