package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.common.dialogs.labelList
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagFlowLayout(
    tags: List<String>,
    modifier: Modifier = Modifier,
    maxItemsInEachRow: Int = 5
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = maxItemsInEachRow
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = .6f),
                        CircleShape
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Preview
@Composable
private fun LabelRowPreview() {
    DevDashTheme { TagFlowLayout(labelList) }
}