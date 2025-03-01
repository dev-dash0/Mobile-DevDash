package com.elfeky.devdash.ui.common.component.swipe_calender.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.roundToLong

/**
 * Represents the state of a schedule calendar view.
 *
 * This class manages the visible time range, scrolling behavior, and
 * calculation of event positions within the calendar view. It also handles
 * animating changes to the view and updating the selected date/time.
 *
 * @property referenceDateTime The base date/time used to calculate the current
 *                              visible time range.
 * @property onDateTimeSelected A callback function invoked when the user interacts
 *                              with the calendar and a new date/time should be
 *                              considered as selected.
 * @property coroutineScope The coroutine scope used for launching animations and
 *                          other asynchronous tasks.
 */
class ScheduleCalendarState(
    val referenceDateTime: LocalDateTime,
    private val onDateTimeSelected: (LocalDateTime) -> Unit,
    private val coroutineScope: CoroutineScope
) {
    /**
     * The calculated start date and time.
     *
     * This property represents the start date and time derived from the [referenceDateTime]
     * plus the number of seconds specified in [secondsOffset]. Any changes to either
     * [referenceDateTime] or [secondsOffset] will automatically trigger a recalculation of this value.
     *
     * @see referenceDateTime
     * @see secondsOffset
     */
    val startDateTime: LocalDateTime by derivedStateOf {
        referenceDateTime.plusSeconds(secondsOffset.value)
    }

    /**
     * The calculated end date and time, based on the [startDateTime] and the [viewSpanSeconds].
     * This value is dynamically updated whenever either [startDateTime] or [viewSpanSeconds] changes.
     * It is derived by adding the number of seconds specified by [viewSpanSeconds] to the [startDateTime].
     *
     * For example, if [startDateTime] is 2023-10-27T10:00:00 and [viewSpanSeconds] is 3600 (1 hour),
     * then [endDateTime] will be 2023-10-27T11:00:00.
     *
     * @see startDateTime
     * @see viewSpanSeconds
     */
    val endDateTime: LocalDateTime by derivedStateOf {
        startDateTime.plusSeconds(this.viewSpanSeconds.value)
    }

    /**
     * The duration of the visible time span in seconds.
     *
     * This property controls the length of the time period that is currently
     * displayed in the view. For example, if set to the duration of a day (86400 seconds),
     * the view will show a full day's worth of data. If set to the duration of an hour (3600 seconds),
     * it will only show an hour's worth.
     *
     * The value is represented as a [Long] and can be animated using [Animatable].
     *
     * Initialized with the duration of a day in seconds, calculated using [ChronoUnit.DAYS.duration.seconds].
     */
    private val viewSpanSeconds = Animatable(ChronoUnit.DAYS.duration.seconds, LongToVector)

    /**
     * Represents a converter between a Long value and a Float based Vector
     */
    private val secondsOffset = Animatable(0L, LongToVector)

    /**
     * Represents the width of the component.
     *
     * The width is an integer value that defines the horizontal dimension of the component.
     * It can be modified and its changes will trigger recomposition of the composable.
     *
     * The default width is 1.
     */
    private val width = mutableIntStateOf(1)

    /**
     * Updates the view's span and width, triggering animations and anchor updates.
     *
     * This function is responsible for:
     * 1. Updating the view's width with the provided `newWidth`.
     * 2. Animating the `viewSpanSeconds` to the new `newViewSpanSeconds` value.
     * 3. If the `newViewSpanSeconds` is different from the current `viewSpanSeconds`,
     *    it triggers an update of the anchors using `updateAnchors(newViewSpanSeconds)`.
     *
     * The updates to `viewSpanSeconds` and anchor recalculations happen in separate coroutines, allowing for concurrent operations.
     *
     * @param newViewSpanSeconds The new time span, in seconds, for the view. This value will be animated to.
     * @param newWidth The new width of the view. This value is directly assigned.
     * @throws IllegalArgumentException if `newWidth` is negative.
     */
    fun updateView(newViewSpanSeconds: Long, newWidth: Int) {
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

    val scrollableState = ScrollableState {
        coroutineScope.launch {
            secondsOffset.snapTo(secondsOffset.value - it.toSeconds())
        }
        it
    }

    /**
     * The number of seconds represented by one pixel.
     *
     * This value is calculated based on the `viewSpanSeconds` (the total number of seconds
     * visible in the view) and the `width` (the width of the view in pixels).
     *
     * It's a derived state, meaning it automatically updates whenever either `viewSpanSeconds` or
     * `width` changes. This value is useful for converting between pixel distances and time
     * durations.
     *
     * For example, if `secondsInPx` is 0.01, then a movement of 100 pixels represents 1 second.
     *
     * Calculation: `viewSpanSeconds` / `width`
     */
    private val secondsInPx by derivedStateOf {
        this.viewSpanSeconds.value.toFloat() / width.intValue.toFloat()
    }

    /**
     * Converts a floating-point value representing a time duration in pixels to a duration in seconds.
     *
     * This function is an extension function for [Float] and assumes that the input value represents
     * a duration in pixels, where the conversion factor between pixels and seconds is determined by
     * the `secondsInPx` constant.
     *
     * @receiver The floating-point value representing the time duration in pixels.
     * @return The equivalent time duration in seconds, rounded to the nearest whole number.
     * @see secondsInPx
     *
     * Example:
     * ```
     * val pixels = 100.0f
     * val secondsPerPixel = 0.01f // Example: 0.01 seconds per pixel
     * val secondsInPx = secondsPerPixel
     * val durationInSeconds = pixels.toSeconds()
     * println(durationInSeconds) // Output will depend on the value of secondsPerPixel, e.g., 1
     * ```
     */
    private fun Float.toSeconds(): Long {
        return (this * secondsInPx).roundToLong()
    }

    /**
     * Converts a time duration in milliseconds (represented as a Long) to a pixel value (represented as a Float).
     *
     * This function assumes a fixed conversion rate defined by [secondsInPx]. It calculates the pixel value by dividing
     * the time duration (in milliseconds) by the number of milliseconds represented by one pixel.
     *
     * @return The calculated pixel value representing the time duration.
     *
     * Example:
     * ```kotlin
     * val durationInMillis: Long = 5000 // 5 seconds
     * val pixelValue: Float = durationInMillis.toPx() // Assuming secondsInPx is 1000 (1 pixel = 1 second), pixelValue will be 5.0
     * ```
     *
     * @see secondsInPx
     */
    private fun Long.toPx(): Float {
        return this / secondsInPx
    }

    /**
     * A custom [FlingBehavior] that simulates a "snap-to-anchor" fling effect.
     *
     * This behavior modifies the standard fling behavior to force the scrolling to
     * stop at the nearest "anchor" point. It calculates the target end position
     * based on the initial velocity and an exponential decay function, then
     * uses the [flingToNearestAnchor] method to move to that closest anchor.
     *
     * The "anchors" are implicitly determined by the [secondsOffset] value, which
     * likely represents positions in a time-based layout.
     *
     * Key aspects of this behavior:
     *   - **Exponential Decay:**  Uses an exponential decay function to calculate how far
     *     the fling should travel based on its initial velocity. The `frictionMultiplier`
     *     controls the rate of deceleration.
     *   - **Snap to Anchors:** After calculating the theoretical end position, the
     *     `flingToNearestAnchor` function is called to ensure the scroll stops at
     *     a predefined anchor.
     *   - **Zero Velocity at End:** The function always returns 0f, indicating that the
     *     velocity after the fling is always zero.
     */
    val scrollFlingBehavior = object : FlingBehavior {
        val decay = exponentialDecay<Float>(frictionMultiplier = 2f)
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            val endPosition = decay.calculateTargetValue(0f, -initialVelocity)
            flingToNearestAnchor(secondsOffset.value.toPx() + endPosition)
            return 0f
        }
    }

    /**
     * Represents the visible hours within the defined time range, considering the anchor range.
     *
     * This property calculates a list of `LocalDateTime` objects representing the hours that should
     * be visually represented within a time-based display.  The visibility of hours is determined
     * by several factors:
     *
     * - **Start and End Time:** The `startDateTime` and `endDateTime` define the overall time window.
     * - **Anchor Range:** The `anchorRangeSeconds` determines the granularity of the displayed hours.
     * - **Hourly Granularity**: The output is based on hourly intervals starting from the truncated start time to the end time.
     * - **Filtering:** Only hours that are a multiple of the `anchorRangeSeconds` (in hours) are included. It also filters out the midnight hour (0)
     * - **Special Case (24-hour anchor):** If `anchorRangeSeconds` is equal to 24 hours (86400 seconds), an empty list is returned.
     *   This signifies that in a full 24-hour range, no specific hour markers are needed.
     *
     * **Example:**
     *   If `startDateTime` is 2023-10-27T08:00:00, `endDateTime` is 2023-10-27T18:00:00, and `anchorRangeSeconds` is 4 hours (14400 seconds),
     *   then `visibleHours` would likely contain:
     *   - 2023-10-27T12:00:00
     *   - 2023-10-27T16:00:00
     *
     * **Use Case:**
     *   This property is typically used to determine which hour markers should be drawn or displayed
     *   in a time-based UI component, such as a timeline or a calendar view.
     *
     * @return A list of `LocalDateTime` objects representing the visible hours. If anchorRange is 24 hours, returns an empty list.
     */
    val visibleHours by derivedStateOf {
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

    /**
     * The maximum allowed time difference (in seconds) between the anchor frame's timestamp and
     * the current frame's timestamp for a successful anchor association.
     *
     * When associating frames with an anchor, this property defines the acceptable time window.
     * If the time difference between the anchor frame and the current frame exceeds this value,
     * the association attempt will be considered invalid or out of range.
     *
     * A value of [Long.MAX_VALUE] effectively disables this time-based filtering, allowing
     * any time difference between the anchor and the current frame.
     *
     * Defaults to [Long.MAX_VALUE] (no time-based filtering).
     */
    private var anchorRangeSeconds by mutableLongStateOf(Long.MAX_VALUE)

    /**
     * The maximum distance, in pixels, that a gesture can be from the calculated anchor point
     * to still be considered anchored to that point.  If a gesture moves outside of this range,
     * the anchor point will be recalculated.
     *
     * This value defaults to [Float.MAX_VALUE], effectively disabling the anchor range check.
     * Setting this to a finite value can help prevent jumps in the target position when a gesture
     * moves slightly outside of the intended area, especially in cases where multiple potential
     * anchor points exist.
     *
     * For example, if this is set to 100.0f, and the calculated anchor point is (50, 50), then
     * any touch within the bounds of (x: -50 to 150, y: -50 to 150) will be considered anchored
     * to (50,50). If the touch goes outside this range, the anchor will be reevaluated.
     */
    private var anchorRangePx by mutableFloatStateOf(Float.MAX_VALUE)

    /**
     * Updates the anchor range based on the current view span in seconds.
     *
     * This function calculates the appropriate anchor range (in seconds and pixels)
     * based on the provided `viewSpanInSeconds`. The anchor range determines the
     * granularity of snap points for scrolling or other time-based interactions.
     * The logic defines different anchor ranges for different view span lengths, making sure that bigger spans will have bigger time chunks.
     *
     * The function then updates the `anchorRangeSeconds` and `anchorRangePx` properties
     * and calls `flingToNearestAnchor` to adjust the current position to the nearest anchor.
     *
     * @param viewSpanInSeconds The current view span in seconds. This determines the visible time range.
     *   - If `viewSpanInSeconds` is greater than 24 hours (24 * 3600 seconds), the anchor range is set to 24 hours.
     *   - If `viewSpanInSeconds` is exactly 24 hours and bigger than 12 hours, the anchor range is set to 6 hours.
     *   - If `viewSpanInSeconds` is between 6 and 12 hours, the anchor range is set to 3 hours.
     *   - If `viewSpanInSeconds` is between 3 and 6 hours, the anchor range is set to 2 hours.
     *   - Otherwise, the anchor range is set to 1 hour.
     *
     * @see flingToNearestAnchor
     * @see toPx
     */
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

    /**
     *  Calculates the nearest anchor point to a given target value and animates
     *  the `secondsOffset` to that anchor, then triggers an update of the selected date/time.
     *
     *  This function is designed for a scenario where a UI element (likely a scrubber or similar)
     *  can be "flung" or dragged to a target position.  Instead of landing exactly at the target,
     *  it snaps to the nearest predefined "anchor" point within a specified range.
     *
     *  @param target The target position (in pixels) to which the UI element was flung.
     *                This is typically the end position of a fling gesture.
     *
     *  @see anchorRangePx Defines the range (in pixels) between anchor points.
     *  @see secondsOffset A state object representing the current position as an offset in seconds.
     *                     This value is animated to the new anchor position.
     *  @see startDateTime The initial date/time, which is updated based on the selected anchor.
     *  @see onDateTimeSelected A callback function invoked when a new date/time is selected.
     *
     *  **Algorithm:**
     *  1. **Calculate Potential Anchors:** Determines two potential anchor points (`nearestAnchor` and `nearestAnchor2`)
     *     around the `target`. These are calculated by finding the remainder of the target divided by the
     *     `anchorRangePx` and subtracting it from the target (`nearestAnchor`). The second one is simply
     *     the first one plus `anchorRangePx`.
     *  2. **Determine Closest Anchor:** Calculates the distance from the `target` to each potential anchor.
     *     The anchor with the smaller distance is considered the nearest.
     *  3. **Animate to Anchor:** Animates the `secondsOffset` to the selected nearest anchor using `animateTo`.
     *     The anchor position (in pixels) is first converted to seconds using the `toSeconds()` extension.
     *  4. **Update Date/Time:** Calls the `onDateTimeSelected` callback to update the application's state with the
     *     newly selected date/time based on the adjusted anchor point.
     *
     *   **Example**
     *   If target = 120, anchorRangePx = 50
     *   - */
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

    /**
     * Calculates the fractional offset of a given LocalDateTime relative to a start time and a view span.
     *
     * This function determines how far along a given LocalDateTime is within a defined time window.
     * The window is defined by `startDateTime` (the beginning of the window) and `viewSpanSeconds` (the duration of the window in seconds).
     * The result is a float between 0.0 and 1.0 (inclusive) representing the position of the input `localDateTime` within that window.
     *
     * For example:
     * - If `localDateTime` is equal to `startDateTime`, the function returns 0.0.
     * - If `localDateTime` is `viewSpanSeconds` seconds after `startDateTime`, the function returns 1.0.
     * - If `localDateTime` is halfway between `startDateTime` and the end of the window, the function returns 0.5.
     * - If `localDateTime` is before startDateTime it will return a negative value
     * - If `localDateTime` is past the end of the viewspan it will return a value greater than 1
     *
     * @param localDateTime The LocalDateTime for which to calculate the fractional offset.
     * @return A Float representing the fractional offset of `localDateTime` within the defined time window.
     *         The value will be between 0.0 and 1.0 (inclusive) if `localDateTime` falls within the window.
     *          Values outside that range indicate localDateTime is outside the window.
     * @throws IllegalArgumentException if `viewSpanSeconds` is zero or negative.
     */
    fun offsetFraction(localDateTime: LocalDateTime): Float {
        return ChronoUnit.SECONDS.between(startDateTime, localDateTime)
            .toFloat() / (viewSpanSeconds.value).toFloat()
    }

    /**
     * Calculates the width and horizontal offset for an event within a time range.
     *
     * This function determines the visual representation (width and starting position) of an event
     * on a timeline or calendar display, based on its start and end times and the total available width.
     * It assumes that the time range it is dealing with is a single day.
     *
     * @param start The LocalDateTime representing the start time of the event.
     * @param end The LocalDateTime representing the end time of the event.
     * @param totalWidth The total width available for displaying the event within the timeline.
     * @return A Pair containing two Int values:
     *         - first: The calculated width of the event. This will be at least 1.
     *         - second: The calculated horizontal offset (starting position) of the event from the left edge of the timeline.
     *
     * @throws IllegalArgumentException if start time is after end time.
     *
     * Example:
     * ```
     * val start = LocalDateTime.of(2023, 10, 27, 9, 0) // 9:00 AM
     * val end = LocalDateTime.of(2023, 10, 27, 11, 0) // 11:00 AM
     * val totalWidth = 100
     * val (width, offset) = widthAndOffsetForEvent(start, end, totalWidth)
     * // width will be approximately 17 (2 hours out of 24, 2/24 = 0.08333, 0.08333 * 100 = 8.333, but this is between two offset points. from 0 to 9:00 and 0 to 11:00).
     * // And then 1-hour offset for 9AM, 9/24 = 0.375, 0.375 * 100 = 37.5
     * // Thus width can be approximately 17 and offset will be 37.
     * ```
     */
    fun widthAndOffsetForEvent(
        start: LocalDateTime, end: LocalDateTime, totalWidth: Int
    ): Pair<Int, Int> {
        val startOffsetPercent = offsetFraction(start).coerceIn(0f, 1f)
        val endOffsetPercent = offsetFraction(end).coerceIn(0f, 1f)

        val width = ((endOffsetPercent - startOffsetPercent) * totalWidth).toInt() + 1
        val offsetX = (startOffsetPercent * totalWidth).toInt()
        return width to offsetX
    }
}

/**
 * [TwoWayConverter] to convert between a [Long] and an [AnimationVector1D].
 *
 * This converter is useful for animating properties that are conceptually represented by
 * a single [Long] value, but need to be animated using Compose's animation system, which
 * operates on [AnimationVector1D] types.
 *
 * The conversion from [Long] to [AnimationVector1D] is done by taking the [Long] value and
 * casting it to a [Float], then using it to construct a new [AnimationVector1D].
 *
 * The reverse conversion, from [AnimationVector1D] to [Long], is done by taking the
 * [AnimationVector1D]'s `value` (which is a [Float]), and casting it to a [Long]. Note that this
 * cast may result in a loss of precision if the float value is not a whole number.
 */
val LongToVector: TwoWayConverter<Long, AnimationVector1D> =
    TwoWayConverter({ AnimationVector1D(it.toFloat()) }, { it.value.toLong() })

/**
 * Calculates the absolute remainder of a floating-point number when divided by a modular value.
 *
 * This function ensures that the remainder is always non-negative, regardless of the sign of the
 * dividend (the number on which the function is called). It achieves this by adding the modular
 * value to the result of the modulo operation and then taking the modulo again.
 *
 * @param modular The divisor or modular value.
 * @return The absolute remainder, which is always a non-negative value less than `modular`.
 *
 * @throws ArithmeticException if `modular` is zero, as this would result in division by zero.
 *
 * Example:
 * ```
 * val num1 = 7.5f
 * val mod1 = 3f
 * val result1 = num1.absRem(mod1) // result1 will be 1.5f (7.5 % 3 = 1.5)
 *
 * val num2 = -7.5f
 * val mod2 = 3f
 * val result2 = num2.absRem(mod2) // result2 will be 1.5f (-7.5 % 3 = -1.5, then (-1.5 + 3) % 3 = 1.5)
 *
 * val num3 = 7.5f
 * val mod3 = -3f
 * val result3 = num3.absRem(mod3) // result3 will be -1.5f (7.5 % -3 = 1.5 , then (1.5 + (-3)) % -3 = -1.5)
 *
 * val num4 = -7.5f
 * val mod4 = -3f
 * val result4 = num4.absRem(mod4) // result4 will be -1.5f (-7.5 % -3 = -1.5, then (-1.5 + (-3)) % -3 = -1.5)
 *
 * val num5 = 7.5f
 * val mod5 = 0f
 * val result5 = try { num5.absRem(mod5) } catch (e: ArithmeticException) { println("Error: ${e.message}") } // Error: Division by zero
 * ```
 */
fun Float.absRem(modular: Float): Float {
    return ((this % modular) + modular) % modular
}