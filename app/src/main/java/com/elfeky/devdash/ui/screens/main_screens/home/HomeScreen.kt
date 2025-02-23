package com.elfeky.devdash.ui.screens.main_screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.card.IssueCard
import com.elfeky.devdash.ui.screens.main_screens.home.components.issueItem
import com.elfeky.devdash.ui.screens.main_screens.home.components.progressItem
import com.elfeky.devdash.ui.screens.main_screens.home.model.IssueUiModel
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    urgentIssueList: List<IssueUiModel>,
    pinnedIssueList: List<IssueUiModel>,
    modifier: Modifier = Modifier,
    mainNavController: NavController,
    appNavController: NavController
) {
    var itemHeight by remember { mutableStateOf(48.dp) }
    val local = LocalDensity.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        stickyHeader {
            Text(
                text = "HI, User!",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        progressItem(
            icon = R.drawable.progress_ic,
            title = "Progress",
            progressCards = progressCards
        )

        issueItem(R.drawable.alert_ic, "Urgent Issues", urgentIssueList, itemHeight) {
            IssueCard(it,
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    if (it == urgentIssueList[0]) itemHeight =
                        with(local) { layoutCoordinates.size.height.toDp() }
                })
        }

        issueItem(R.drawable.pin_ic, "Pinned", pinnedIssueList, itemHeight) {
            IssueCard(it)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val navController = rememberNavController()
    DevDashTheme {
        HomeScreen(
            issueList.take(5),
            issueList, Modifier.fillMaxSize(), navController, navController
        )
    }
}