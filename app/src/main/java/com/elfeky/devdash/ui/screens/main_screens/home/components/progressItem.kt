package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class CircularProgressCardUiModel(
    val title: String,
    val progress: Float,
    val colors: List<Color>
)

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.progressItem(
    @DrawableRes icon: Int,
    title: String,
    progressCards: List<CircularProgressCardUiModel>,
    modifier: Modifier = Modifier
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
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            progressCards.forEach { cardData ->
                CircularProgressCard(
                    title = cardData.title,
                    progress = cardData.progress,
                    modifier = Modifier.weight(1f),
                    colors = cardData.colors
                )
            }
        }
    }
}