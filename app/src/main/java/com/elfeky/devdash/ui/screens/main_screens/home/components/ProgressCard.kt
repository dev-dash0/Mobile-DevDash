package com.elfeky.devdash.ui.screens.main_screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var startAnimation by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = startAnimation,
        animationSpec = tween(durationMillis = 1500),
        label = "Progress Animation",
    )

    LaunchedEffect(key1 = true) {
        startAnimation = progress
    }

    Card(modifier = modifier.fillMaxHeight()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Brush.verticalGradient(listOf(Indigo, DarkBlue)))
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = textColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
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
