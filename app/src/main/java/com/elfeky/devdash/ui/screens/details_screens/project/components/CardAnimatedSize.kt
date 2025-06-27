package com.elfeky.devdash.ui.screens.details_screens.project.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun CardAnimatedSize(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    var expanded by remember { mutableStateOf(true) }
    val rotateAnimation by animateFloatAsState(if (expanded) 180f else 0f)
    Card(
        modifier = modifier
            .wrapContentSize()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.rotate(rotateAnimation)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        AnimatedVisibility(expanded) {
            content()
        }
    }
}

@Preview
@Composable
private fun CardAnimatedSizePreview() {
    DevDashTheme {
        CardAnimatedSize("Sprint") { Box(modifier = Modifier.size(200.dp)) }
    }
}