package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.graphics.Typeface
import android.text.Annotation
import android.text.style.StyleSpan
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter.PositionedAnnotation
import com.devbrackets.android.annores.text.annotation.handler.FontStyleAnnotationHandler

/**
 * Adapts [StyleSpan]s to [PositionedAnnotation]s using the values provided by
 * [FontStyleAnnotationHandler]. Currently this supports:
 *   * Normal (default)
 *   * Bold
 *   * Italic
 *   * Bold & Italic
 */
object StyleSpanAdapter: AbstractSpanAdapter<StyleSpan>(StyleSpan::class) {
  override fun convertSpan(
    span: StyleSpan,
    startIndex: Int,
    endIndex: Int,
  ): List<PositionedAnnotation> {
    return when(span.style) {
      Typeface.NORMAL -> listOf(
        Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_NORMAL)
      )
      Typeface.BOLD -> listOf(
        Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_BOLD)
      )
      Typeface.ITALIC -> listOf(
        Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_ITALIC)
      )
      Typeface.BOLD_ITALIC -> listOf(
        Annotation(FontStyleAnnotationHandler.KEY, "${FontStyleAnnotationHandler.VALUE_BOLD},${FontStyleAnnotationHandler.VALUE_ITALIC}"),
      )
      else -> return emptyList()
    }.map { annotation ->
      PositionedAnnotation(annotation, startIndex, endIndex)
    }
  }
}