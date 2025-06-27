package com.elfeky.devdash.ui.screens.main_screens.calender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.calender.components.CalenderIssuesSection
import com.elfeky.devdash.ui.screens.main_screens.calender.components.DaysSection

@Composable
fun CalenderScreen(
    modifier: Modifier = Modifier,
    viewModel: CalenderViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewModel.state.value.isCalendarLoading) {
            CircularProgressIndicator()
        } else if (viewModel.state.value.calendarError != "") {
            Text(
                text = viewModel.state.value.calendarError,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            var selectedIndex = remember { mutableIntStateOf(0) }

            DaysSection(
                modifier = Modifier.weight(1f),
                dates = viewModel.dates,
                selectedIndex = selectedIndex
            )
            CalenderIssuesSection(
                modifier = Modifier.weight(4f),
                issues = viewModel.issues[selectedIndex.intValue]
            )
        }

    }
}
