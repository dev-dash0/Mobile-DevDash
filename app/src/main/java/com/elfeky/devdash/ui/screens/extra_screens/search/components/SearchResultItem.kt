package com.elfeky.devdash.ui.screens.extra_screens.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalFoundationApi::class)
fun <T> LazyListScope.searchResultItem(
    title: String,
    key: ((T) -> Any)? = null,
    results: List<T>,
    showDivider: Boolean = true,
    itemContent: @Composable (LazyItemScope.(T) -> Unit)
) {
    if (results.isNotEmpty()) {
        stickyHeader {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }

        items(results, key = key, itemContent = itemContent)

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
                )
            ) {}

            searchResultItem(
                title = "Search Results",
                results = listOf(
                    SearchItem(4, "Kotlin Coroutines"),
                    SearchItem(5, "SwiftUI Basics"),
                    SearchItem(6, "Flutter Widgets")
                ),
                showDivider = false
            ) {}
        }
    }
}