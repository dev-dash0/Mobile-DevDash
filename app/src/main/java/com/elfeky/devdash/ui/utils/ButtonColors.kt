package com.elfeky.devdash.ui.utils

import androidx.compose.material3.ButtonColors
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.NavyBlue
import com.elfeky.devdash.ui.theme.Pink
import com.elfeky.devdash.ui.theme.White

val defaultButtonColor = ButtonColors(
    containerColor = NavyBlue,
    contentColor = White,
    disabledContainerColor = Gray,
    disabledContentColor = White
)
val secondaryButtonColor = defaultButtonColor.copy(containerColor = Pink)
val cancelButtonColor = defaultButtonColor.copy(containerColor = DarkBlue)
