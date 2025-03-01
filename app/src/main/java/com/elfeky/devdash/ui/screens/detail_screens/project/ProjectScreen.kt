package com.elfeky.devdash.ui.screens.detail_screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProjectScreen(id: Int, modifier: Modifier = Modifier, onIssueClick: (id: Int) -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("ID= $id", color = MaterialTheme.colorScheme.onBackground)
        Button(onClick = { onIssueClick(10) }) {
            Text("navigate")
        }
    }
}