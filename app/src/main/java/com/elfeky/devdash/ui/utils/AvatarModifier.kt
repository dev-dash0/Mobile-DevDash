package com.elfeky.devdash.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.avatarModifier(backgroundColor: Color, size: Dp = 42.dp): Modifier {
    return this.then(
        Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .size(size)
    )
}