package com.elfeky.devdash.ui.common.test

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.B400
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.G500
import com.elfeky.devdash.ui.theme.R500
import com.elfeky.devdash.ui.theme.Y500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.roundToLong

@Composable
fun rememberScheduleCalendarState(
    referenceDateTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS),
    onDateTimeSelected: (LocalDateTime) -> Unit = {}
): ScheduleCalendarState {
    val coroutineScope = rememberCoroutineScope()
    return remember(coroutineScope) {
        ScheduleCalendarState(
            referenceDateTime = referenceDateTime,
            onDateTimeSelected = onDateTimeSelected,
            coroutineScope = coroutineScope,
        )
    }
}

class ScheduleCalendarState(
    referenceDateTime: LocalDateTime,
    private val onDateTimeSelected: (LocalDateTime) -> Unit,
    private val coroutineScope: CoroutineScope
) {
    val startDateTime: LocalDateTime by derivedStateOf {
        referenceDateTime.plusSeconds(secondsOffset.value)
    }

    val endDateTime: LocalDateTime by derivedStateOf {
        startDateTime.plusSeconds(this.viewSpanSeconds.value)
    }

    private val viewSpanSeconds = Animatable(ChronoUnit.DAYS.duration.seconds, LongToVector)
    private val secondsOffset = Animatable(0L, LongToVector)
    private val width = mutableIntStateOf(1)

    internal fun updateView(newViewSpanSeconds: Long, newWidth: Int) {
        this.width.intValue = newWidth
        val currentViewSpanSeconds = viewSpanSeconds.value
        coroutineScope.launch {
            viewSpanSeconds.animateTo(newViewSpanSeconds)
        }
        coroutineScope.launch {
            if (newViewSpanSeconds != currentViewSpanSeconds) {
                updateAnchors(newViewSpanSeconds)
            }
        }
    }

    internal val scrollableState = ScrollableState {
        coroutineScope.launch {
            secondsOffset.snapTo(secondsOffset.value - it.toSeconds())
        }
        it
    }

    private val secondsInPx by derivedStateOf {
        this.viewSpanSeconds.value.toFloat() / width.intValue.toFloat()
    }

    private fun Float.toSeconds(): Long {
        return (this * secondsInPx).roundToLong()
    }

    private fun Long.toPx(): Float {
        return this / secondsInPx
    }

    internal val scrollFlingBehavior = object : FlingBehavior {
        val decay = exponentialDecay<Float>(frictionMultiplier = 2f)
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            val endPosition = decay.calculateTargetValue(0f, -initialVelocity)
            flingToNearestAnchor(secondsOffset.value.toPx() + endPosition)
            return 0f
        }
    }

    internal val visibleHours by derivedStateOf {
        val startHour = startDateTime.truncatedTo(ChronoUnit.HOURS)
        val endHour = endDateTime.truncatedTo(ChronoUnit.HOURS).plusHours(1)

        if (anchorRangeSeconds == 24 * 3600L) {
            emptyList()
        } else {
            startHour.between(endHour) { plusHours(1L) }.filter {
                it.hour % (anchorRangeSeconds / 3600) == 0L && it.hour != 0
            }.toList()
        }
    }

    private var anchorRangeSeconds by mutableLongStateOf(Long.MAX_VALUE)
    private var anchorRangePx by mutableFloatStateOf(Float.MAX_VALUE)
    private suspend fun updateAnchors(viewSpanInSeconds: Long) {
        anchorRangeSeconds = if (viewSpanInSeconds > 24 * 3600) {
            24 * 3600
        } else if (viewSpanInSeconds.toInt() == 24 * 3600 && viewSpanInSeconds > 12 * 3600) {
            6 * 3600
        } else if (viewSpanInSeconds <= 12 * 3600 && viewSpanInSeconds > 6 * 3600) {
            3 * 3600
        } else if (viewSpanInSeconds <= 6 * 3600 && viewSpanInSeconds > 3 * 3600) {
            2 * 3600
        } else {
            3600
        }
        anchorRangePx = anchorRangeSeconds.toPx()
        flingToNearestAnchor(secondsOffset.value.toPx())
    }

    private suspend fun flingToNearestAnchor(target: Float) {
        val nearestAnchor = target - (target.absRem(anchorRangePx))
        val nearestAnchor2 = nearestAnchor + anchorRangePx

        val newAnchoredPosition =
            (abs(target - nearestAnchor) to abs(target - nearestAnchor2)).let {
                if (it.first > it.second) nearestAnchor2 else nearestAnchor
            }
        secondsOffset.animateTo(newAnchoredPosition.toSeconds())
        onDateTimeSelected(startDateTime)
    }

    internal fun offsetFraction(localDateTime: LocalDateTime): Float {
        return ChronoUnit.SECONDS.between(startDateTime, localDateTime)
            .toFloat() / (viewSpanSeconds.value).toFloat()
    }

    internal fun widthAndOffsetForEvent(
        start: LocalDateTime, end: LocalDateTime, totalWidth: Int
    ): Pair<Int, Int> {
        val startOffsetPercent = offsetFraction(start).coerceIn(0f, 1f)
        val endOffsetPercent = offsetFraction(end).coerceIn(0f, 1f)

        val width = ((endOffsetPercent - startOffsetPercent) * totalWidth).toInt() + 1
        val offsetX = (startOffsetPercent * totalWidth).toInt()
        return width to offsetX
    }
}

private val LongToVector: TwoWayConverter<Long, AnimationVector1D> =
    TwoWayConverter({ AnimationVector1D(it.toFloat()) }, { it.value.toLong() })

private fun Float.absRem(modular: Float): Float {
    return ((this % modular) + modular) % modular
}

@Composable
fun ScheduleCalendar(
    state: ScheduleCalendarState,
    modifier: Modifier = Modifier,
    viewSpan: Long = 48 * 3600L, // in seconds
    sections: List<CalendarSection> = emptyList(),
    now: LocalDateTime = LocalDateTime.now(),
    eventTimesVisible: Boolean = true
) = BoxWithConstraints(
    modifier
        .fillMaxWidth()
        .scrollable(
            state.scrollableState, Orientation.Horizontal, flingBehavior = state.scrollFlingBehavior
        )
) {
    state.updateView(viewSpan, constraints.maxWidth)

    Column {
        DaysRow(
            state = state, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        HoursRow(state)
        val maxEventsHeight = remember { mutableIntStateOf(0) }
        Column(modifier = Modifier.fillMaxWidth()) {

            sections.forEachIndexed { index, section ->
                val rowHeight = remember { mutableIntStateOf(0) }

                CalendarSectionRow(
                    section = section,
                    state = state,
                    eventTimesVisible = eventTimesVisible
                )
                if (rowHeight.intValue > 0) {
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) { maxEventsHeight.intValue.toDp() })
            ) {
                state.visibleHours.forEach { localDateTime ->
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val offsetPercent = state.offsetFraction(localDateTime)
                        drawLine(
                            color = Color.Gray,
                            strokeWidth = 2f,
                            start = Offset(offsetPercent * size.width, 0f),
                            end = Offset(offsetPercent * size.width, size.height),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(10f, 20f), phase = 5f
                            )
                        )
                    }
                }
            }
        }
    }

    DayDividers(
        state = state, modifier = Modifier.matchParentSize()
    )

    // "now" indicator
    Canvas(modifier = Modifier.matchParentSize()) {
        val offsetPercent = state.offsetFraction(now)
        drawLine(
            color = Color.Magenta,
            strokeWidth = 6f,
            start = Offset(offsetPercent * size.width, 0f),
            end = Offset(offsetPercent * size.width, size.height)
        )
        drawCircle(
            Color.Magenta, center = Offset(offsetPercent * size.width, 12f), radius = 12f
        )
    }
}

@Composable
fun CalendarSectionRow(
    section: CalendarSection,
    state: ScheduleCalendarState,
    eventTimesVisible: Boolean
) {
    Column(
        Modifier
            .animateContentSize()
            .padding(horizontal = 8.dp)
    ) {
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
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    items(eventMap) { (event, startHit, endHit) ->
                        val (width, offsetX) = state.widthAndOffsetForEvent(
                            start = event.startDate,

                            end = event.endDate,
                            totalWidth = constraints.maxWidth
                        )

                        val shape = when {
                            startHit && endHit -> RoundedCornerShape(4.dp)
                            startHit -> RoundedCornerShape(
                                topStart = 4.dp, bottomStart = 4.dp
                            )

                            endHit -> RoundedCornerShape(
                                topEnd = 4.dp, bottomEnd = 4.dp
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
                                    text = event.startDate.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.endDate.format(
                                        DateTimeFormatter.ofPattern("HH:mm")
                                    ),
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

@Composable
internal fun DaysRow(
    state: ScheduleCalendarState, modifier: Modifier = Modifier
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
                .padding(horizontal = 8.dp)) {
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

@Composable
internal fun HoursRow(
    state: ScheduleCalendarState
) {
    AnimatedVisibility(visible = state.visibleHours.isNotEmpty()) {
        Layout(content = {
            state.visibleHours.forEach { localDateTime ->
                Text(
                    localDateTime.format(DateTimeFormatter.ofPattern("hh a")),
                    fontSize = 12.sp,
                    modifier = Modifier.then(
                        LocalDateTimeData(localDateTime)
                    )
                )
            }
        }) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) to it.localDateTime }

            val width = constraints.maxWidth
            val height = if (placeables.isNotEmpty()) {
                placeables.maxOf { it.first.height }
            } else {
                0
            }
            layout(width, height) {
                placeables.forEach { (placeable, localDateTime) ->
                    val origin = state.offsetFraction(localDateTime) * width
                    val x = origin.toInt() - placeable.width / 2
                    placeable.placeRelative(x.coerceAtLeast(0), 0)
                }
            }
        }
    }
}

@Composable
fun DayDividers(
    state: ScheduleCalendarState, modifier: Modifier = Modifier
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
                    intervals = floatArrayOf(10f, 20f), phase = 5f
                )
            )
        }
    }
}

data class CalendarSection(
    val name: String, val events: List<CalendarEvent>
)

data class CalendarEvent(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val name: String = "",
    val description: String = "",
    val color: Color = G500,
    val offsetY: MutableIntState = mutableIntStateOf(0)
)

private data class LocalDateTimeData(
    val localDateTime: LocalDateTime,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@LocalDateTimeData
}

private val Measurable.localDateTime: LocalDateTime
    get() = (parentData as? LocalDateTimeData)?.localDateTime
        ?: error("No LocalDateTime for measurable $this")

fun LocalDateTime.between(
    target: LocalDateTime, increment: LocalDateTime.() -> LocalDateTime
): Sequence<LocalDateTime> {
    return generateSequence(seed = this, nextFunction = {
        val next = it.increment()
        if (next.isBefore(target)) next else null
    })
}

infix fun LocalDateTime.daysBetween(target: LocalDateTime): Sequence<LocalDateTime> {
    val start = truncatedTo(ChronoUnit.DAYS)
    return start.between(target.truncatedTo(ChronoUnit.DAYS).plusDays(1)) {
        plusDays(1)
    }
}

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
                        CalendarEvent(startDate = LocalDateTime.now().minusHours(6),
                            endDate = LocalDateTime.now().plusHours(12),
                            name = "Halil Ozercan",
                            description = "",
                            color = R500,
                            offsetY = remember { mutableIntStateOf(10) }), CalendarEvent(
                            startDate = LocalDateTime.now().plusHours(24),
                            endDate = LocalDateTime.now().plusHours(48),
                            name = "And Ani Calik",
                            description = "",
                            offsetY = remember { mutableIntStateOf(30) },
                            color = G500
                        )
                    )
                ), CalendarSection(
                    "Compose Schedule", events = listOf(
                        CalendarEvent(
                            startDate = LocalDateTime.now().plusHours(6),
                            endDate = LocalDateTime.now().plusHours(12),
                            name = "Halil Ozercan",
                            description = "",
                            color = Y500
                        ), CalendarEvent(
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