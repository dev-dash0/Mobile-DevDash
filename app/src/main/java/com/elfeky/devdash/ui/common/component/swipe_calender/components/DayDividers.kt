package com.elfeky.devdash.ui.common.component.swipe_calender.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.elfeky.devdash.ui.common.component.swipe_calender.model.ScheduleCalendarState
import com.elfeky.devdash.ui.common.component.swipe_calender.model.daysBetween

@Composable
fun DayDividers(
    state: ScheduleCalendarState,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        (state.startDateTime daysBetween state.endDateTime).forEach { localDateTime ->
            val offsetPercent = state.offsetFraction(localDateTime)
            drawLine(
                color = Color.Gray,
                strokeWidth = 4f,
                start = Offset(offsetPercent * size.width, 0f),
                end = Offset(offsetPercent * size.width, size.height),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(10f, 20f),
                    phase = 5f
                )
            )
        }
    }
}