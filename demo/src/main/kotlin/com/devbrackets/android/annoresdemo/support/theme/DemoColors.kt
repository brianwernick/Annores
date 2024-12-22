package com.devbrackets.android.annoresdemo.support.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class DemoColors(
  val primary: Color,
  val primaryDark: Color,
  val primaryVariant: Color,
  val secondary: Color,
  val error: Color,

  val surface: Color,
  val background: Color,
  val backgroundStripe: Color,

  val text100: Color,
  val text80: Color,
  val text60: Color,
  val text40: Color,
  val text05: Color
)

val lightColors = DemoColors(
  primary = Color(0xFF0A5462),
  primaryDark = Color(0xFF088CA5),
  primaryVariant = Color(0xffffffff),
  secondary = Color(0xff23a5cc),
  error = Color(0xffbd0f0f),

  surface = Color(0xffffffff),
  background = Color(0xfff6f9f9),
  backgroundStripe = Color(0x14838c89),

  text100 = Color(0xff101112),
  text80 = Color(0xff17181a),
  text60 = Color(0xff585f66),
  text40 = Color(0xff838c89),
  text05 = Color(0xfff6f9f9)
)

val darkColors = DemoColors(
  primary = Color(0xff17181a),
  primaryDark = Color(0xff101112),
  primaryVariant = Color(0xff585f66),
  secondary = Color(0xff1d85a5),
  error = Color(0xffbd0f0f),

  surface = Color(0xff242629),
  background = Color(0xff17181a),
  backgroundStripe = Color(0x14585f66),

  text100 = Color(0xfff6f9f9),
  text80 = Color(0xffacb7b2),
  text60 = Color(0x99acb7b2),
  text40 = Color(0xff585f66),
  text05 = Color(0xff101112)
)

internal val LocalAppColors = staticCompositionLocalOf { lightColors }