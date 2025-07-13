package com.elfeky.devdash.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Indigo,
    onPrimary = White,
    primaryContainer = NavyBlue,
    onPrimaryContainer = White,
    secondary = IceBlue,
    onSecondary = BlueGray,
    tertiary = Pink,
    onTertiary = White,
    background = DarkBlue,
    onBackground = White,
    surface = Lavender,
    onSurface = Black,
    surfaceVariant = LightBlue,
    onSurfaceVariant = Gray,
    outline = Gray,
    outlineVariant = LightGray,
    surfaceContainer = DarkPurple,
    surfaceContainerHigh = LightBlueGray,
    surfaceContainerHighest = White,
    error = Red
)

private val LightColorScheme = lightColorScheme(
    primary = Indigo,
    secondary = White,
    tertiary = Pink,
    background = Indigo,
    onBackground = Black,
    onSurface = NavyBlue,
    onPrimary = White
)

@Composable
fun DevDashTheme(
    darkTheme: Boolean = true,//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes
    )
}