package com.example.ui.theme

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

private val LightColorScheme = lightColorScheme(
  primary = SynapseIris,
  onPrimary = Color.White,
  primaryContainer = SynapseIrisContainer,
  onPrimaryContainer = SynapseIrisOnContainer,

  secondary = SynapseCyan,
  onSecondary = Color.White,
  secondaryContainer = SynapseCyanContainer,
  onSecondaryContainer = SynapseCyanOnContainer,

  tertiary = DomainAttentionTeal,
  onTertiary = Color.White,
  tertiaryContainer = DomainAttentionContainer,
  onTertiaryContainer = DomainAttentionOn,

  background = SynapseCanvasBg,
  onBackground = SynapseTextPrimary,
  surface = SynapseCardWhite,
  onSurface = SynapseTextPrimary,
  surfaceVariant = SynapseSurfaceVariant,
  onSurfaceVariant = SynapseTextSecondary,
  outline = SynapseCardBorder,
  outlineVariant = SynapseCardBorderSubtle
)

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF818CF8),
  onPrimary = Color(0xFF1E1B4B),
  primaryContainer = Color(0xFF312E81),
  onPrimaryContainer = Color(0xFFE0E7FF),

  secondary = Color(0xFF38BDF8),
  onSecondary = Color(0xFF082F49),
  secondaryContainer = Color(0xFF075985),
  onSecondaryContainer = Color(0xFFE0F2FE),

  tertiary = Color(0xFF2DD4BF),
  onTertiary = Color(0xFF042F2E),

  background = Color(0xFF0B0F19),
  onBackground = Color(0xFFF1F5F9),
  surface = Color(0xFF111827),
  onSurface = Color(0xFFF1F5F9),
  surfaceVariant = Color(0xFF1E293B),
  onSurfaceVariant = Color(0xFF94A3B8),
  outline = Color(0xFF334155),
  outlineVariant = Color(0xFF1E293B)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Default to luminous airy theme requested by user
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
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
