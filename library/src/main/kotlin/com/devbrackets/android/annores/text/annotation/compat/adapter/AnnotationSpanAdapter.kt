package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.text.Annotation
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter.PositionedAnnotation

/**
 * Adapts [Annotation] Spans to [PositionedAnnotation]s. This is effectively just encapsulates
 * the [Annotation]s with the indexes needed to apply then.
 */
object AnnotationSpanAdapter: AbstractSpanAdapter<Annotation>(Annotation::class) {
  override fun convertSpan(
    span: Annotation,
    startIndex: Int,
    endIndex: Int,
  ): List<PositionedAnnotation> {
    return listOf(
      PositionedAnnotation(span, startIndex, endIndex)
    )
  }
}