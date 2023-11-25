package com.devbrackets.android.annores.text.annotation.handler

import android.text.Annotation
import androidx.compose.ui.text.AnnotatedString

/**
 * Defines an interface for applying the styling defined by [Annotation]s
 * to an [AnnotatedString]. This is used during the conversion process from
 * the Android View systems Spanned to a Composable supported [AnnotatedString].
 */
interface AnnotationHandler {
  /**
   * Checks if this instance can apply the styling defined in the [Annotation].
   *
   * @param annotation The [Annotation] to determine if this [AnnotationHandler] can apply styling
   * @return `true` if this [AnnotationHandler] instance can apply styling for [annotation]
   */
  fun handles(annotation: Annotation): Boolean

  /**
   * Handles applying the actual styling from the [Annotation] into the [builder]. This should
   * only be called if [handles] returns `true`.
   *
   * @param annotation The [Annotation] to apply the styles from into the [builder]
   * @param startIndex The start index (inclusive) in the [builder] to apply styling to
   * @param endIndex The end index (inclusive) in the [builder] to apply styling to
   * @param builder The [AnnotatedString.Builder] to apply styling from the [annotation] to
   * @return `true` if the styling was applied to the [builder]
   */
  fun apply(
    annotation: Annotation,
    startIndex: Int,
    endIndex: Int,
    builder: AnnotatedString.Builder
  ): Boolean
}