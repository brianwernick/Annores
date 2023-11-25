package com.devbrackets.android.annores.text.annotation.handler

import android.text.Annotation
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

/**
 * An [AnnotationHandler] that adds support for a `fontStyle` annotation. This
 * supports the following values:
 *   * `normal`
 *   * `bold`
 *   * `italic`
 *   * `underline`
 *   * `strike` (strike through)
 *
 * e.g.
 * ```xml
 *   <string name="styled_annotation"><annotation fontStyle="bold">Bold Text</annotation></string>
 * ```
 *
 * TODO: should we support multiple, e.g. `fontStyle="bold,underline"`?
 */
object FontStyleAnnotationHandler: AnnotationHandler {
  const val KEY = "fontStyle"

  const val VALUE_NORMAL = "normal"
  const val VALUE_BOLD = "bold"
  const val VALUE_ITALIC = "italic"

  const val VALUE_UNDERLINE = "underline"
  const val VALUE_STRIKE_THROUGH = "strike"

  override fun handles(annotation: Annotation): Boolean {
    return annotation.key.equals(KEY, true)
  }

  override fun apply(
    annotation: Annotation,
    startIndex: Int,
    endIndex: Int,
    builder: AnnotatedString.Builder
  ): Boolean {
    if (!handles(annotation)) {
      return false
    }

    val spanStyle = SpanStyle(
      fontWeight = determineWeight(annotation.value),
      fontStyle = determineStyle(annotation.value),
      textDecoration = determineDecoration(annotation.value)
    )

    builder.addStyle(spanStyle, startIndex, endIndex)

    return true
  }

  private fun determineWeight(description: String): FontWeight? {
    return when(description.lowercase()) {
      VALUE_NORMAL -> FontWeight.Normal
      VALUE_BOLD -> FontWeight.Bold
      else -> null
    }
  }

  private fun determineStyle(description: String): FontStyle? {
    return when(description.lowercase()) {
      VALUE_ITALIC -> FontStyle.Italic
      else -> null
    }
  }

  private fun determineDecoration(description: String): TextDecoration? {
    return when(description.lowercase()) {
      VALUE_UNDERLINE -> TextDecoration.Underline
      VALUE_STRIKE_THROUGH -> TextDecoration.LineThrough
      else -> null
    }
  }
}