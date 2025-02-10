package com.elfeky.devdash.ui.common.dialogs.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


data class MenuDataModel(
    val icon: ImageVector, // Can be Icons or other drawable resources
    val color: Color,
    val text: String
)
