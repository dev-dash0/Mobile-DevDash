package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.IssueCard
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.common.dropdown_menu.model.Priority
import com.elfeky.devdash.ui.common.dropdown_menu.model.Status
import com.elfeky.devdash.ui.common.labelList
import com.elfeky.devdash.ui.common.userList
import com.elfeky.devdash.ui.screens.main_screens.home.components.Item
import com.elfeky.devdash.ui.screens.main_screens.home.components.ProgressRow
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    if (state.isLoading) {
        LoadingIndicator()
    } else if (state.error.isNotEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = state.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    text = "HI, User!",
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            item {
                Item(R.drawable.progress_ic, "Your Progress") {
                    ProgressRow()
                }
            }

            item {
                Item(R.drawable.alert_ic, "Urgent Issues") {
                    IssueCard(
                        "12 Feb | 18 Feb",
                        Status.InProgress,
                        "Issue Title",
                        labelList.take(3),
                        userList.take(3),
                        Priority.Medium,
                        onClick = { },
                        onLongClick = { }
                    )
                }
            }

            item {
                Item(R.drawable.ic_pin, "Pinned") {
                    IssueCard(
                        "12 Feb | 18 Feb",
                        Status.Reviewing,
                        "Issue Title",
                        labelList.take(3),
                        userList.take(3),
                        Priority.Low,
                        onClick = { },
                        onLongClick = { }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DevDashTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}