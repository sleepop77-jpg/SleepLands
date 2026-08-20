package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CoralPrimaryLight,
    onPrimary = TextWhite,
    primaryContainer = CoralPrimaryDark,
    onPrimaryContainer = TextWhite,
    secondary = AccentGold,
    onSecondary = TextDark,
    background = CoralBackgroundDark,
    onBackground = TextWhite,
    surface = Color(0xFF381D1D),
    onSurface = TextWhite,
    surfaceVariant = Color(0xFF552D2D),
    onSurfaceVariant = TextWhiteMuted
)

private val LightColorScheme = lightColorScheme(
    primary = CoralPrimary,
    onPrimary = TextWhite,
    primaryContainer = CoralPrimaryLight,
    onPrimaryContainer = TextWhite,
    secondary = AccentGold,
    onSecondary = TextDark,
    background = CoralBackground,
    onBackground = TextWhite,
    surface = SurfaceLight,
    onSurface = TextDark,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextWhite
)

@Composable
fun StudyOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

