package com.elfeky.devdash.ui.screens.details_screens.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.tab_row.TabRowContainer
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun IssueScreen(
    companyId: Int, projectId: Int,
    modifier: Modifier = Modifier
) {
    TabRowContainer(modifier = modifier) { index ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$index", color = MaterialTheme.colorScheme.onBackground)
            Text("Company Id= $companyId", color = MaterialTheme.colorScheme.onBackground)
            Text("Project Id= $projectId", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview
@Composable
private fun IssueScreenPreview() {
    DevDashTheme {
        IssueScreen(0, 5)
    }
}