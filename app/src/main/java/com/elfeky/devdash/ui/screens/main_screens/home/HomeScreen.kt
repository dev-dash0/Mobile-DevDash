package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.Status
import com.elfeky.devdash.ui.common.card.IssueCard
import com.elfeky.devdash.ui.common.dialogs.assigneeList
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.screens.main_screen.home.components.Item
import com.elfeky.devdash.ui.screens.main_screen.home.components.ProgressRow
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.Red
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainNavController: NavController,
    appNavController: NavController
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
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
                    "DevDash",
                    "12 Feb | 18 Feb",
                    Status.Canceled,
                    "Issue Title",
                    labelList.take(3),
                    assigneeList,
                    Red
                )
            }
        }

        item {
            Item(R.drawable.pin_ic, "Pinned") {
                IssueCard(
                    "DevDash",
                    "12 Feb | 18 Feb",
                    Status.Canceled,
                    "Issue Title",
                    labelList.take(3),
                    assigneeList,
                    Red
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    DevDashTheme {
        HomeScreen(Modifier.fillMaxSize(), navController, navController)
    }
}