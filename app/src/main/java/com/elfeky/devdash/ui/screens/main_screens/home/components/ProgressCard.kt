package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.Indigo

@Composable
fun CircularProgressCard(
    title: String,
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
    colors: List<Color> = listOf(Color.Blue, Color.Magenta),
    backgroundColor: Color = Color.LightGray,
    textColor: Color = colors[1]
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "Progress Animation"
    )
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(Indigo, DarkBlue)))
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = textColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Canvas(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                ) {
                    val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

                    drawArc(
                        color = backgroundColor,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = stroke
                    )

                    val gradient = Brush.linearGradient(colors)
                    drawArc(
                        brush = gradient,
                        startAngle = -90f,
                        sweepAngle = animatedProgress * 360f,
                        useCenter = false,
                        style = stroke,
                        blendMode = BlendMode.Multiply
                    )
                }

                Text(
                    text = "${(animatedProgress * 100).toInt()}",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }

}

@Preview
@Composable
fun CircularProgressPreview() {
    val progress by remember { mutableFloatStateOf(.5f) }

    CircularProgressCard(
        title = "Total Issues in progress",
        progress = progress,
        modifier = Modifier.size(150.dp)
    )
}
