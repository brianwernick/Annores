package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.text.Annotation
import android.text.Spanned

/**
 * Provides the interface used to adapt Spans within the [Spanned] to
 * [PositionedAnnotation]s that can be used by Composables.
 */
interface SpanAdapter {

  /**
   * Converts any Span types supported by the implementing [SpanAdapter]
   * into [PositionedAnnotation]s.
   *
   * @param source The [Spanned] to look for and adapt any supported Spans with
   * @return A list of the Spans that have been adapted to [PositionedAnnotation]s
   */
  fun adapt(source: Spanned): List<PositionedAnnotation>

  /**
   * Provides the information necessary to apply the [annotation]
   */
  data class PositionedAnnotation(
    val annotation: Annotation,
    val startIndex: Int,
    val endIndex: Int
  )
}