package com.devbrackets.android.annoresdemo.sample.string.support

import android.text.Annotation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.core.graphics.toColorInt
import com.devbrackets.android.annores.text.annotation.handler.AnnotationHandler
import java.lang.IllegalArgumentException

/**
 * An [AnnotationHandler] that adds support for a `color` annotation.
 *
 * e.g.
 * ```xml
 *   <string name="colored_annotation"><annotation color="#ffff0000">Red</annotation>, <annotation color="#ff00ff00">Green</annotation>, &amp; <annotation color="#ff0000ff">Blue</annotation></string>
 * ```
 */
object ColoredAnnotationHandler: AnnotationHandler {
  private const val KEY = "color"

  override fun handles(annotation: Annotation): Boolean {
    return annotation.key.equals(KEY, ignoreCase = true)
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

    val color = try {
      annotation.value.toColorInt()
    } catch (e: IllegalArgumentException) {
      return false
    }

    val spanStyle = SpanStyle(
      color = Color(color)
    )

    builder.addStyle(spanStyle, startIndex, endIndex)
    return true
  }
}