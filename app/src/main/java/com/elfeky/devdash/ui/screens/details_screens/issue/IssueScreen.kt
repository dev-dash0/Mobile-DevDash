package com.elfeky.devdash.ui.screens.details_screens.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun IssueScreen(companyId: Int, projectId: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Company Id= $companyId", color = MaterialTheme.colorScheme.onBackground)
        Text("Project Id= $projectId", color = MaterialTheme.colorScheme.onBackground)
    }
}