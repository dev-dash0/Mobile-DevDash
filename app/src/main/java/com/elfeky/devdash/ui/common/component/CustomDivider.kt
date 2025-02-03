package com.elfeky.devdash.ui.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.LightGray

@Composable
fun CustomDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(.9f),
        color = LightGray
    )
}