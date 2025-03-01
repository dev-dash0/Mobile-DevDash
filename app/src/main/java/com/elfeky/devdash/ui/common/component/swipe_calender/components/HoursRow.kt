package com.elfeky.devdash.ui.common.component.swipe_calender.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.component.swipe_calender.model.LocalDateTimeData
import com.elfeky.devdash.ui.common.component.swipe_calender.model.ScheduleCalendarState
import com.elfeky.devdash.ui.common.component.swipe_calender.model.localDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HoursRow(
    state: ScheduleCalendarState
) {
    AnimatedVisibility(visible = state.visibleHours.isNotEmpty()) {
        Layout(
            content = {
                state.visibleHours.forEach { localDateTime ->
                    Text(
                        localDateTime.format(DateTimeFormatter.ofPattern("hh a")),
                        fontSize = 12.sp,
                        modifier = Modifier.then(
                            LocalDateTimeData(localDateTime)
                        )
                    )
                }
            }
        ) { measurable, constraints ->
            val placeable = measurable.map { it.measure(constraints) to it.localDateTime }

            val width = constraints.maxWidth
            val height = if (placeable.isNotEmpty()) {
                placeable.maxOf { it.first.height }
            } else {
                0
            }
            layout(width, height) {
                placeable.forEach { (placeable, localDateTime) ->
                    val origin = state.offsetFraction(localDateTime) * width
                    val x = origin.toInt() - placeable.width / 2
                    placeable.placeRelative(x.coerceAtLeast(0), 0)
                }
            }
        }
    }
}