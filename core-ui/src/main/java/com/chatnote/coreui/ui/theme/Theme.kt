package com.chatnote.coreui.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = darkColorScheme(
    primary = Color(0xFF007AFF),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF8E8E93),
    onSecondary = Color(0xFFF2F2F2),
    secondaryContainer = Color(0xFFF2F2F2),
    surface = Color(0xFFE6E6E6),
    onSurface = Color(0xFF55555A),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surfaceContainerLow = Color(0xFFFFCCEE), // Pink
    surfaceContainerHigh = Color(0xFFE6FFBA), // Green
    surfaceContainerHighest = Color(0xFFB6BFFF), // Blue
    error = Color.Red
)

private val DarkColorScheme = lightColorScheme(
    primary = Color(0xFF0467D3),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF8E8E93),
    secondaryContainer = Color(0xFF161616),
    onSecondary = Color(0xFF55555A),
    surface = Color(0xFFE6E6E6),
    onSurface = Color(0xFFFFFFFF),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFF9B3C7C), // Pink
    surfaceContainerHigh = Color(0xFF95B166), // Green
    surfaceContainerHighest = Color(0xFF5E6FDD), // Blue
    error = Color.Red
)

@Composable
fun AppTheme(
    darkModeSetting: DarkModeSetting = DarkModeSetting.SYSTEM,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (darkModeSetting) {
        DarkModeSetting.SYSTEM -> isSystemInDarkTheme()
        DarkModeSetting.DARK -> true
        DarkModeSetting.LIGHT -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
