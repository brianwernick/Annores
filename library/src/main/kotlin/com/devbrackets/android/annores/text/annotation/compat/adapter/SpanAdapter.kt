package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.text.Annotation
import android.text.ParcelableSpan
import android.text.Spanned

/**
 * Provides the interface used to adapt Spans within the [Spanned] to
 * [PositionedAnnotation]s that can be used by Composables.
 */
interface SpanAdapter {

  /**
   * Checks if this instance can convert [S] into [PositionedAnnotation]s.
   *
   * @param span The [S] to determine if it can be converted to [PositionedAnnotation]s
   * @return `true` if this [SpanAdapter] can convert [S] into [PositionedAnnotation]s
   */
  fun <S: ParcelableSpan> adapts(span: S): Boolean

  /**
   * Converts the [span] into a list of [PositionedAnnotation]s. Typically
   * this conversion process will result in a list of size 1, however there
   * are cases where a single [ParcelableSpan] will be converted into multiple
   * [PositionedAnnotation]s.
   *
   * @param span The [ParcelableSpan] of [S] to convert to [PositionedAnnotation]s
   * @param startIndex The start index for the [span]
   * @param endIndex The end index for the [span]
   * @return A list of [PositionedAnnotation]s that represent the converted [span]
   */
  fun <S: ParcelableSpan> adapt(span: S, startIndex: Int, endIndex: Int): List<PositionedAnnotation>

  /**
   * Provides the information necessary to apply the [annotation]
   */
  data class PositionedAnnotation(
    val annotation: Annotation,
    val startIndex: Int,
    val endIndex: Int
  )
}