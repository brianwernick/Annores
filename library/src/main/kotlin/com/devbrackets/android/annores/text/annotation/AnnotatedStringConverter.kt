package com.devbrackets.android.annores.text.annotation

import android.text.Spanned
import android.text.SpannedString
import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import com.devbrackets.android.annores.text.annotation.compat.adapter.AnnotationSpanAdapter
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter
import com.devbrackets.android.annores.text.annotation.compat.adapter.StyleSpanAdapter
import com.devbrackets.android.annores.text.annotation.handler.AnnotationHandler
import com.devbrackets.android.annores.text.annotation.handler.FontStyleAnnotationHandler

/**
 * Provides the functionality for converting from a [SpannedString] to an [AnnotatedString]
 */
object AnnotatedStringConverter {
  val defaultSpanAdapters = listOf(
    StyleSpanAdapter,
    AnnotationSpanAdapter
  )

  val defaultAnnotationHandlers = listOf(
    FontStyleAnnotationHandler
  )

  /**
   * Converts the [source] to an [AnnotatedString] using the [spanAdapters] and [annotationHandlers]
   * for the conversion and styling.
   *
   * @param source The [Spanned] to convert to an [AnnotatedString]
   * @param spanAdapters A list of [SpanAdapter]s to be used in converting the Spans from [source] to Annotations
   *                     used by Composables
   * @param annotationHandlers A list of [AnnotationHandler]s that use the resulting Annotations from
   *                          [spanAdapters] to provide the actual styling in the resulting [AnnotatedString]
   *
   * @return The [AnnotatedString] with supported styles applied from the [source]
   */
  fun convert(
    source: Spanned,
    spanAdapters: List<SpanAdapter> = defaultSpanAdapters,
    annotationHandlers: List<AnnotationHandler> = defaultAnnotationHandlers
  ): AnnotatedString {
    val destination = AnnotatedString.Builder(source.toString())

    spanAdapters.forEach { adapter ->
      adapter.adapt(source).forEach { annotation ->
        applyAnnotation(
          annotation = annotation,
          destination = destination,
          annotationHandlers = annotationHandlers
        )
      }
    }

    return destination.toAnnotatedString()
  }

  /**
   * Sends the required information to the first [AnnotationHandler] in
   * [annotationHandlers] that indicates it can handle the [annotation].
   *
   * @return true if the annotation was handled
   */
  private fun applyAnnotation(
    annotation: SpanAdapter.PositionedAnnotation,
    destination: AnnotatedString.Builder,
    annotationHandlers: List<AnnotationHandler>
  ) {
    // Find the first handler that actually applies the annotation
    val handled = annotationHandlers.any { handler ->
      handler.handles(annotation.annotation) && handler.apply(
        annotation = annotation.annotation,
        startIndex = annotation.startIndex,
        endIndex = annotation.endIndex,
        builder = destination
      )
    }

    if (!handled) {
      Log.w("AnnotatedStrConverter", "No AnnotationHandler handles PositionedAnnotation $annotation")
    }
  }
}