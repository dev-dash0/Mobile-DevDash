package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.cardGradientBackground

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardContainer(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Brush = cardGradientBackground,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    verticalSpaceBetweenItems: Dp = 8.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.combinedClickable(onClick = onClick, onLongClick = onLongClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        ),
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(verticalSpaceBetweenItems),
            content = content
        )
    }
}

@Preview
@Composable
private fun CardContainerPreview() {
    DevDashTheme {
        CardContainer(
            onClick = {},
            onLongClick = {},
            modifier = Modifier.height(100.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {}
        }
    }
}