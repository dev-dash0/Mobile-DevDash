package com.elfeky.devdash.ui.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.theme.Black
import com.elfeky.devdash.ui.theme.DarkIndigo
import com.elfeky.devdash.ui.theme.DeepViolet
import com.elfeky.devdash.ui.theme.Indigo
import com.elfeky.devdash.ui.theme.Lavender

val gradientBackground = Brush.linearGradient(colors = listOf(Black, Indigo))
val cardGradientBackground = Brush.horizontalGradient(colors = listOf(DarkIndigo, DeepViolet))
val boardColumnGradient = Brush.verticalGradient(
    colors = listOf(Lavender.copy(alpha = 0.4f), Lavender.copy(alpha = 0.2f))
)
val glassGradient = Brush.verticalGradient(
    colors = listOf(Color.Transparent, Indigo.copy(.15f), Indigo)
)