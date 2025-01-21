package com.sendme.coreui.component.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pingpad.coreui.component.ui.theme.DarkBackground
import com.pingpad.coreui.component.ui.theme.DarkError
import com.pingpad.coreui.component.ui.theme.DarkOnBackground
import com.pingpad.coreui.component.ui.theme.DarkOnPrimary
import com.pingpad.coreui.component.ui.theme.DarkOnSecondary
import com.pingpad.coreui.component.ui.theme.DarkPrimary
import com.pingpad.coreui.component.ui.theme.DarkSecondary
import com.pingpad.coreui.component.ui.theme.LightBackground
import com.pingpad.coreui.component.ui.theme.LightError
import com.pingpad.coreui.component.ui.theme.LightOnBackground
import com.pingpad.coreui.component.ui.theme.LightOnPrimary
import com.pingpad.coreui.component.ui.theme.LightOnSecondary
import com.pingpad.coreui.component.ui.theme.LightPrimary
import com.pingpad.coreui.component.ui.theme.LightSecondary

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    error = DarkError

)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary, // primary actions like send button
    onPrimary = LightOnPrimary, // text on primary button
    secondary = LightSecondary, // secondary container like search area
    onSecondary = LightOnSecondary, // like search hint
    background = LightBackground, // app main background
    onBackground = LightOnBackground, // text on background
    error = LightError
)

@Composable
fun SendMeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
        content = content
    )
}