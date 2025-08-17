package com.devbrackets.android.annores.text

import android.os.Build
import android.text.Spanned
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.AnnotatedString
import com.devbrackets.android.annores.text.annotation.AnnotatedStringConverter
import com.devbrackets.android.annores.text.annotation.compat.adapter.SpanAdapter
import com.devbrackets.android.annores.text.annotation.handler.AnnotationHandler
import com.devbrackets.android.annores.text.span.SpannedFormatter
import java.util.Locale

/**
 * Load an annotated string resource.
 *
 * @param id The resource identifier for the annotated string
 * @param spanAdapters A list of [SpanAdapter]s to be used in converting the View Spans to Annotations
 *                     that can be used by Composables
 * @param annotationHandlers A list of [AnnotationHandler]s that use the resulting Annotations from
 *                          [spanAdapters] to provide the actual styling used by Composables
 *
 * @return The [AnnotatedString] associated with the resource and converted using the
 *         [spanAdapters] and [annotationHandlers]
 */
@Composable
@ReadOnlyComposable
fun annotatedStringResource(
  @StringRes id: Int,
  spanAdapters: List<SpanAdapter> = AnnotatedStringConverter.defaultSpanAdapters,
  annotationHandlers: List<AnnotationHandler> = AnnotatedStringConverter.defaultAnnotationHandlers
): AnnotatedString {
  val text = LocalResources.current.getText(id)
  if (text !is Spanned) {
    return AnnotatedString(text.toString())
  }

  return AnnotatedStringConverter.convert(
    source = text,
    spanAdapters = spanAdapters,
    annotationHandlers = annotationHandlers
  )
}

/**
 * Load an annotated string resource with formatting.
 *
 * @param id The resource identifier for the annotated string
 * @param formatArgs The arguments to use when formatting the plain text associated with the resource
 * @param spanAdapters A list of [SpanAdapter]s to be used in converting the View Spans to Annotations
 *                     that can be used by Composables
 * @param annotationHandlers A list of [AnnotationHandler]s that use the resulting Annotations from
 *                          [spanAdapters] to provide the actual styling used by Composables
 *
 * @return The [AnnotatedString] associated with the resource and converted using the
 *         [spanAdapters] and [annotationHandlers]
 */
@Composable
@ReadOnlyComposable
fun annotatedStringResource(
  @StringRes id: Int,
  vararg formatArgs: Any,
  spanAdapters: List<SpanAdapter> = AnnotatedStringConverter.defaultSpanAdapters,
  annotationHandlers: List<AnnotationHandler> = AnnotatedStringConverter.defaultAnnotationHandlers
): AnnotatedString {
  val resources = LocalResources.current
  val text = resources.getText(id)
  if (text !is Spanned) {
    return AnnotatedString(resources.getString(id, *formatArgs))
  }

  return AnnotatedStringConverter.convert(
    source = SpannedFormatter.format(primaryLocale(), text, *formatArgs),
    spanAdapters = spanAdapters,
    annotationHandlers = annotationHandlers
  )
}

/**
 * Load an annotated plural string resource.
 *
 * @param id The resource identifier for the annotated plural string
 * @param quantity The quantity used to retrieve the appropriate annotated plural string from resources
 * @param spanAdapters A list of [SpanAdapter]s to be used in converting the View Spans to Annotations
 *                     that can be used by Composables
 * @param annotationHandlers A list of [AnnotationHandler]s that use the resulting Annotations from
 *                          [spanAdapters] to provide the actual styling used by Composables
 *
 * @return The [AnnotatedString] associated with the resource and converted using the
 *         [spanAdapters] and [annotationHandlers]
 */
@Composable
@ReadOnlyComposable
fun annotatedPluralStringResource(
  @PluralsRes id: Int,
  quantity: Int,
  spanAdapters: List<SpanAdapter> = AnnotatedStringConverter.defaultSpanAdapters,
  annotationHandlers: List<AnnotationHandler> = AnnotatedStringConverter.defaultAnnotationHandlers
): AnnotatedString {
  val text = LocalResources.current.getQuantityText(id, quantity)
  if (text !is Spanned) {
    return AnnotatedString(text.toString())
  }

  return AnnotatedStringConverter.convert(
    source = text,
    spanAdapters = spanAdapters,
    annotationHandlers = annotationHandlers
  )
}

/**
 * Load an annotated plural string resource with formatting.
 *
 * @param id The resource identifier for the annotated plural string
 * @param quantity The quantity used to retrieve the appropriate annotated plural string from resources
 * @param formatArgs The arguments to use when formatting the plain text associated with the resource
 * @param spanAdapters A list of [SpanAdapter]s to be used in converting the View Spans to Annotations
 *                     that can be used by Composables
 * @param annotationHandlers A list of [AnnotationHandler]s that use the resulting Annotations from
 *                          [spanAdapters] to provide the actual styling used by Composables
 *
 * @return The [AnnotatedString] associated with the resource and converted using the
 *         [spanAdapters] and [annotationHandlers]
 */
@Composable
@ReadOnlyComposable
fun annotatedPluralStringResource(
  @PluralsRes id: Int,
  quantity: Int,
  vararg formatArgs: Any,
  spanAdapters: List<SpanAdapter> = AnnotatedStringConverter.defaultSpanAdapters,
  annotationHandlers: List<AnnotationHandler> = AnnotatedStringConverter.defaultAnnotationHandlers
): AnnotatedString {
  val resources = LocalResources.current
  val text = resources.getQuantityText(id, quantity)
  if (text !is Spanned) {
    return AnnotatedString(resources.getQuantityString(id, quantity, *formatArgs))
  }

  return AnnotatedStringConverter.convert(
    source = SpannedFormatter.format(primaryLocale(), text, *formatArgs),
    spanAdapters = spanAdapters,
    annotationHandlers = annotationHandlers
  )
}

/**
 * Retrieves the current primary [Locale] for the active context
 */
@Composable
@ReadOnlyComposable
private fun primaryLocale(): Locale {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    LocalConfiguration.current.locales[0]
  } else {
    @Suppress("DEPRECATION")
    LocalConfiguration.current.locale
  }
}