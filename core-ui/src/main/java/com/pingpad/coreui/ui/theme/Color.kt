package com.pingpad.coreui.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors
val LightPrimary = Color(0xFF007AFF)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightSecondary_SurfaceSubbed = Color(0xFFF2F2F2)
val LightOnSecondary_TextSubbed = Color(0xFF8E8E93)
val LightBackground_SurfacePrimary = Color(0xFFFFFFFF)
val LightOnBackground_TextPrimary = Color(0xFF000000)
val LightError = Color.Red

// Dark Theme Colors
val DarkPrimary = Color(0xFF19A3FE) // Primary actions in dark mode
val DarkOnPrimary = Color(0xFFFFFFFF) // High contrast for content on primary
val DarkSecondary = LightOnSecondary_TextSubbed
val DarkOnSecondary = LightSecondary_SurfaceSubbed
val DarkBackground = LightOnBackground_TextPrimary
val DarkOnBackground = LightBackground_SurfacePrimary
val DarkError = Color.Red // Error indicators in dark mode
