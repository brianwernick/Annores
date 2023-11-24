package com.devbrackets.android.annoresdemo.support.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object DemoTheme {
  val colors: DemoColors
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColors.current
}

@Composable
fun DemoTheme(
  isDark: Boolean = isSystemInDarkTheme(),
  colors: DemoColors = if (isDark) { darkColors } else { lightColors },
  content: @Composable () -> Unit
) {
  val rememberedColors = remember { colors }

  CompositionLocalProvider(
    LocalAppColors provides rememberedColors
  ) {
    MaterialTheme(
      colors = LocalAppColors.current.toMaterialColors(isDark),
      content = content
    )
  }
}

private fun DemoColors.toMaterialColors(isDark: Boolean) = Colors(
  primary = primary,
  primaryVariant = primaryVariant,
  secondary = secondary,
  secondaryVariant = secondary,
  background = background,
  surface = surface,
  error = error,
  onPrimary = text80,
  onSecondary = text100,
  onBackground = text80,
  onSurface = text100,
  onError = text100,
  isLight = !isDark
)