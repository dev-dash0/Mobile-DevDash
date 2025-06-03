package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.common.labelList
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagFlowLayout(
    tags: List<String>,
    modifier: Modifier = Modifier,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        maxItemsInEachRow = maxItemsInEachRow
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                color = textColor,
                style = textStyle,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = .6f), CircleShape)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun LabelRowPreview() {
    DevDashTheme { TagFlowLayout(labelList) }
}