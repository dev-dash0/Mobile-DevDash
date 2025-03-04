package com.elfeky.devdash.ui.screens.main_screens.calender.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.calender.CalenderViewModel
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun DayChip(
    modifier: Modifier = Modifier,
    date: String,
    dateIndex: Int,
    selectedIndex: MutableIntState,
    viewModel: CalenderViewModel = hiltViewModel()
) {
    val dayOfWeek = viewModel.getDayOfWeek(date)

    Card(
        modifier = modifier
            .clickable {
                selectedIndex.intValue = dateIndex
            },
        colors = CardDefaults.cardColors(
            containerColor = if (selectedIndex.intValue != dateIndex) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.primary
        ),
    ) {
        Text(
            text = date,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = dayOfWeek,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun DayChipPreview() {
    DevDashTheme {
        DayChip(date = "04-03-2025", dateIndex = 1, selectedIndex = mutableIntStateOf(1))
    }
}