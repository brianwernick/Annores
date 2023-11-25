package com.devbrackets.android.annores.text.annotation.handler

import android.text.Annotation
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

/**
 * Supports the `fontStyle` annotation. This supports the following values:
 *   * `normal`
 *   * `bold`
 *   * `italic`
 *   * `underline`
 *   * `strike` (strike through)
 *
 * Multiple values can be added by separating each with a comma (`,`), if multiple conflicting values
 * are defined the first one will be used; `normal` and `bold` are two such conflicting values.
 *
 * An example of this annotation in action is:
 * ```xml
 *   <string name="styled_annotation"><annotation fontStyle="bold,underline">Bold & Underlined Text</annotation></string>
 * ```
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

    val values = annotation.value.lowercase().split(",")
    val spanStyle = SpanStyle(
      fontWeight = determineWeight(values),
      fontStyle = determineStyle(values),
      textDecoration = determineDecoration(values),
    )

    builder.addStyle(spanStyle, startIndex, endIndex)

    return true
  }

  private fun determineWeight(values: List<String>): FontWeight? {
    return values.firstNotNullOfOrNull { value ->
      when(value) {
        VALUE_NORMAL -> FontWeight.Normal
        VALUE_BOLD -> FontWeight.Bold
        else -> null
      }
    }
  }

  private fun determineStyle(values: List<String>): FontStyle? {
    return values.firstNotNullOfOrNull { value ->
      when(value) {
        VALUE_ITALIC -> FontStyle.Italic
        else -> null
      }
    }
  }

  private fun determineDecoration(values: List<String>): TextDecoration? {
    return values.firstNotNullOfOrNull { value ->
      when(value) {
        VALUE_UNDERLINE -> TextDecoration.Underline
        VALUE_STRIKE_THROUGH -> TextDecoration.LineThrough
        else -> null
      }
    }
  }
}