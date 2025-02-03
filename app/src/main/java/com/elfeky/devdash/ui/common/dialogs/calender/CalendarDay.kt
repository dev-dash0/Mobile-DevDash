package com.elfeky.devdash.ui.common.dialogs.calender

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.BlueGray
import com.elfeky.devdash.ui.theme.IceBlue
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.Pink
import com.elfeky.devdash.ui.theme.White
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarDay(
    day: LocalDate,
    currentDisplayedMonth: LocalDate,
    selectedStartDay: LocalDate?,
    selectedEndDay: LocalDate?,
    onDayClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDate = LocalDate.now()
    val isFromCurrentMonth = day.month == currentDisplayedMonth.month
    val isStartDay = day == selectedStartDay
    val isEndDay = day == selectedEndDay
    val isInRange =
        selectedStartDay != null && selectedEndDay != null && day.isAfter(selectedStartDay) && day.isBefore(
            selectedEndDay
        )
    val isStartDayOfWeek = day.dayOfWeek == DayOfWeek.MONDAY
    val isEndDayOfWeek = day.dayOfWeek == DayOfWeek.SUNDAY
    val isEndDayOfMonth = day.dayOfMonth == currentDisplayedMonth.lengthOfMonth()

    val backgroundColor = when {
        (isStartDay || isEndDay) && isFromCurrentMonth -> Pink
        isInRange && isFromCurrentMonth -> IceBlue
        else -> Color.Transparent
    }

    val shape = when {
        isStartDay || isEndDay -> CircleShape
        isStartDayOfWeek -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
        isEndDayOfWeek || isEndDayOfMonth -> RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
        else -> RectangleShape
    }

    val textColor = when {
        !isFromCurrentMonth -> LightGray.copy(alpha = 0.5f)
        day.isBefore(currentDate) -> LightGray.copy(alpha = 0.5f)
        isStartDay || isEndDay -> White
        isInRange -> BlueGray
        else -> LightGray
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                if (isStartDay && selectedEndDay != null && !isEndDayOfWeek && !isEndDayOfMonth && !isEndDay) Modifier.background(
                    IceBlue,
                    RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                )
                else if (isEndDay && isFromCurrentMonth && !isStartDayOfWeek && !isStartDay) Modifier.background(
                    IceBlue,
                    RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                )
                else Modifier
            )
            .background(backgroundColor, shape)
            .then(
                if (isFromCurrentMonth && day.isAfter(currentDate.minusDays(1)))
                    Modifier.clickable { onDayClicked() }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}