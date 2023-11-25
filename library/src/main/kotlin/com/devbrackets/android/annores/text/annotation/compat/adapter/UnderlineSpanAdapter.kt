package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.text.Annotation
import android.text.style.UnderlineSpan
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter.PositionedAnnotation
import com.devbrackets.android.annores.text.annotation.handler.FontStyleAnnotationHandler

/**
 * Adapts [UnderlineSpan]s to [PositionedAnnotation]s using the values provided by
 * [FontStyleAnnotationHandler].
 */
object UnderlineSpanAdapter: AbstractSpanAdapter<UnderlineSpan>(UnderlineSpan::class) {
  override fun convertSpan(
    span: UnderlineSpan,
    startIndex: Int,
    endIndex: Int,
  ): List<PositionedAnnotation> {
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_UNDERLINE)
    return listOf(PositionedAnnotation(annotation, startIndex, endIndex))
  }
}