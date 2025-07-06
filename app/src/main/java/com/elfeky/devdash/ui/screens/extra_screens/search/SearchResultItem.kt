package com.elfeky.devdash.ui.screens.extra_screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.searchResultItem(
    title: String,
    results: List<SearchItem>,
    onItemClick: (id: Int) -> Unit,
    showDivider: Boolean = true,
) {
    if (results.isNotEmpty()) {
        stickyHeader {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        items(results, key = { it.id.toString() + it.title }) { item ->
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item.id) },
                headlineContent = { Text(item.title) },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                    headlineColor = MaterialTheme.colorScheme.secondary
                )
            )
        }

        if (showDivider) {
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(.95f),
                    thickness = 2.dp
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchResultItemPreview() {
    DevDashTheme {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            searchResultItem(
                title = "Recent Searches",
                results = listOf(
                    SearchItem(1, "Compose Multiplatform"),
                    SearchItem(2, "Ktor Server"),
                    SearchItem(3, "Android Jetpack Compose")
                ),
                onItemClick = {}
            )
            searchResultItem(
                title = "Search Results",
                results = listOf(
                    SearchItem(4, "Kotlin Coroutines"),
                    SearchItem(5, "SwiftUI Basics"),
                    SearchItem(6, "Flutter Widgets")
                ),
                onItemClick = {},
                showDivider = false
            )
        }
    }
}