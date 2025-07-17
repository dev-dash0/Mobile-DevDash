package com.elfeky.devdash.ui.screens.details_screens.sprint.issue_comment

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

@Composable
fun getUserAccentColor(user: String): Color {
    val hashCode = user.hashCode()
    val hue = (abs(hashCode) % 360).toFloat()
    return Color.hsl(
        hue = hue,
        saturation = 1f,
        lightness = 1f
    )
}