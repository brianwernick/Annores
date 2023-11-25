package com.devbrackets.android.annores.text.annotation.compat.adapter

import android.text.ParcelableSpan
import android.util.Log
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter.PositionedAnnotation
import kotlin.reflect.KClass

/**
 * Provides common functionality used by [SpanAdapter]s to adapt a list of
 * [ParcelableSpan] of type [T] to the [PositionedAnnotation]s.
 */
abstract class AbstractSpanAdapter<T: ParcelableSpan>(
  protected val spanClass: KClass<T>
): SpanAdapter {
  override fun <S : ParcelableSpan> adapts(span: S): Boolean {
    return span::class == spanClass
  }

  override fun <S : ParcelableSpan> adapt(
    span: S,
    startIndex: Int,
    endIndex: Int
  ): List<PositionedAnnotation> {
    if (!adapts(span)) {
      val methodCallString = "adapt(${span::class.simpleName}, $startIndex, $endIndex)"
      Log.e("AbstractSpanAdapter", "$methodCallString was called when adapts() returned false")
      return emptyList()
    }

    @Suppress("UNCHECKED_CAST")
    return convertSpan(span as T, startIndex, endIndex)
  }

  /**
   * Converts the [span] into a list of [PositionedAnnotation]s. Typically
   * this conversion process will result in a list of size 1, however there
   * are cases where a single [ParcelableSpan] will be converted into multiple
   * [PositionedAnnotation]s.
   *
   * @param span The [ParcelableSpan] of [T] to convert to [PositionedAnnotation]s
   * @param startIndex The start index for the [span]
   * @param endIndex The end index for the [span]
   * @return A list of [PositionedAnnotation]s that represent the converted [span]
   */
  abstract fun convertSpan(
    span: T,
    startIndex: Int,
    endIndex: Int
  ): List<PositionedAnnotation>
}