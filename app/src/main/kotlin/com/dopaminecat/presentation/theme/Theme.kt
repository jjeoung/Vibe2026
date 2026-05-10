// filepath: app/src/main/kotlin/com/dopaminecat/presentation/theme/Theme.kt
package com.dopaminecat.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CatOrange       = Color(0xFFFF8C00)
private val CatOrangeLight  = Color(0xFFFFB74D)
private val CatPurple       = Color(0xFF9C27B0)
private val CatGreen        = Color(0xFF4CAF50)
private val CatCream        = Color(0xFFFFF8E1)
private val CatDarkBg       = Color(0xFF1C1A16)

private val LightColorScheme = lightColorScheme(
    primary          = CatOrange,
    onPrimary        = Color.White,
    primaryContainer = Color(0xFFFFE0B2),
    secondary        = CatPurple,
    tertiary         = CatGreen,
    background       = CatCream,
    surface          = CatCream,
    surfaceVariant   = Color(0xFFFFF3E0),
    error            = Color(0xFFD32F2F),
)

private val DarkColorScheme = darkColorScheme(
    primary          = CatOrangeLight,
    onPrimary        = Color.Black,
    primaryContainer = Color(0xFF5D3A00),
    secondary        = Color(0xFFCE93D8),
    tertiary         = Color(0xFF81C784),
    background       = CatDarkBg,
    surface          = CatDarkBg,
    surfaceVariant   = Color(0xFF2D2B22),
    error            = Color(0xFFEF9A9A),
)

@Composable
fun DopamineCatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content,
    )
}
