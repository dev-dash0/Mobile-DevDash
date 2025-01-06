package com.elfeky.devdash.ui.utils

import androidx.compose.material3.ButtonColors
import androidx.compose.ui.graphics.Color
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.NavyBlue
import com.elfeky.devdash.ui.theme.Pink

val defaultButtonColor: ButtonColors =
    ButtonColors(
        contentColor = Color.White,
        containerColor = NavyBlue,
        disabledContentColor = Color.White,
        disabledContainerColor = Color.Gray,
    )

val secondaryButtonColor: ButtonColors =
    ButtonColors(
        contentColor = Color.White,
        containerColor = Pink,
        disabledContentColor = Color.White,
        disabledContainerColor = Color.Gray,
    )

val cancelButtonColor: ButtonColors =
    ButtonColors(
        contentColor = Color.White,
        containerColor = DarkBlue,
        disabledContentColor = Color.White,
        disabledContainerColor = Color.Gray,
    )