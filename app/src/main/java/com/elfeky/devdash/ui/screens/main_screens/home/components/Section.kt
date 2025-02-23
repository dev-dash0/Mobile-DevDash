package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
fun <T> LazyListScope.issueItem(
    @DrawableRes icon: Int,
    title: String,
    items: List<T>,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
    maxItems: Int = 2,
    itemContent: @Composable (LazyItemScope.(item: T) -> Unit),
) {
    stickyHeader(contentType = "header") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                title,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
    item {
        LazyColumn(
            modifier = Modifier.heightIn(max = itemHeight * maxItems),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = items,
                contentType = { "content" }
            ) { item ->
                itemContent(item)
            }
        }
    }
}