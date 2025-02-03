package com.elfeky.devdash.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.elfeky.devdash.ui.theme.White

fun Modifier.dashBorder(
    strokeWidth: Dp,
    dashWidth: Dp,
    gapWidth: Dp,
    color: Color = White,
    cap: StrokeCap = StrokeCap.Round
): Modifier {
    return this.then(
        Modifier.drawBehind {
            val pathEffect =
                PathEffect.dashPathEffect(
                    floatArrayOf(
                        dashWidth.toPx(),
                        gapWidth.toPx()
                    ),
                    0f
                )
            val path = Path().apply {
                addOval(
                    Rect(
                        Offset(strokeWidth.toPx() / 2, strokeWidth.toPx() / 2),
                        Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx())
                    )
                )
            }
            drawPath(
                path = path,
                color = color,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = cap,
                    pathEffect = pathEffect
                )
            )
        }
    )
}