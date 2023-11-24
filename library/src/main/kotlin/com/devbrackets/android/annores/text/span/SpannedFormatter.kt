package com.devbrackets.android.annores.text.span

import android.text.ParcelableSpan
import android.text.SpannableStringBuilder
import android.text.Spanned
import java.util.Locale

/**
 * Provides the functionality to format a [Spanned], retaining any
 * configured Spans. Supported formatting matches the spec defined
 * by the [java.util.Formatter]
 */
object SpannedFormatter {
  // Groups are [Index], [flags, width, precision], [conversion]
  private val FORMAT_REGEX = Regex("%(\\d+\\$|<)?([-#+ 0,(]*\\d*\\.\\d+)?([tT]?[a-zA-Z%])")

  /**
   * Formats the [spanned] with the [formatArgs], retaining any configured
   * Spans using the default [Locale].
   *
   * @param spanned The [Spanned] to format
   * @param formatArgs The arguments to format the [Spanned] with
   *
   * @return The [spanned] that's been formatted with [formatArgs]
   */
  fun format(
    spanned: Spanned,
    vararg formatArgs: Any
  ): Spanned {
    return format(Locale.getDefault(), spanned, *formatArgs)
  }

  /**
   * Formats the [spanned] with the [formatArgs], retaining any configured
   * Spans using the provided [locale].
   *
   * @param locale The [Locale] to use when formatting the [spanned]
   * @param spanned The [Spanned] to format
   * @param formatArgs The arguments to format the [Spanned] with
   *
   * @return The [spanned] that's been formatted with [formatArgs]
   */
  fun format(
    locale: Locale,
    spanned: Spanned,
    vararg formatArgs: Any
  ): Spanned {
    val builder = SpannableStringBuilder(spanned).also { builder ->
      spanned.getSpans(0, spanned.length, ParcelableSpan::class.java).forEach { span ->
        builder.setSpan(
          span,
          spanned.getSpanStart(span),
          spanned.getSpanEnd(span),
          Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }
    }

    return builder.replaceAll(locale, formatArgs)
  }

  /**
   * Replaces all formatting references within the [SpannableStringBuilder] using the [locale] and
   * [formatArgs].
   *
   * @param locale The [Locale] to use when formatting the [formatArgs]
   * @param formatArgs The arguments to use when replacing values within the [SpannableStringBuilder]
   *
   * @return The [SpannableStringBuilder] being modified
   */
  private fun SpannableStringBuilder.replaceAll(
    locale: Locale,
    formatArgs: Array<out Any>
  ): SpannableStringBuilder {
    val state = FormatState(locale, formatArgs)

    FORMAT_REGEX.findAll(this, 0).forEach { match ->
      replace(state, match)
    }

    return this
  }

  /**
   * Replaces the formatting [MatchResult] from the [FORMAT_REGEX] with
   * the appropriately formatted argument from the [state] when applicable.
   *
   * @param state The [FormatState] to use for replacing the format placeholder from [match]
   * @param match The [MatchResult] holding a match from the [FORMAT_REGEX] which should be replaced
   */
  @Suppress("MoveVariableDeclarationIntoWhen")
  private fun SpannableStringBuilder.replace(
    state: FormatState,
    match: MatchResult
  ) {
    val argIndex = match.groupValues[1]
    val format = match.groupValues[2]
    val conversion = match.groupValues[3]

    val replacementValue = when(conversion) {
      "%" -> "%" // %% represents an escaped %
      "n" -> System.lineSeparator() // %n represents a system new-line
      else -> "%$format$conversion".format(state.locale, getFormatArg(state, argIndex))
    }

    replace(match.range.first, match.range.last + 1, replacementValue)
  }

  /**
   * Retrieves the requested argument from the [state], paying attention to ordinary (positional)
   * indexes, relative indexes, and defined indexes.
   *
   * @param state The [FormatState] to use when retrieving the format argument and fetching the
   *              ordinary position information
   * @param indexString The string representation of the requested format index. This should be one
   *                    of `["", "<"]` or a number such as `"2"`
   *
   * @return The format argument from the [state] that is specified by the [indexString]
   * @throws IndexOutOfBoundsException when the specified index doesn't represent a format argument
   */
  @Throws(IndexOutOfBoundsException::class)
  private fun getFormatArg(
    state: FormatState,
    indexString: String
  ): Any {
    val argIndex = when (indexString) {
      "" -> ++state.previousImplicitIndex // Index not specified (ordinary indexing)
      "<" -> state.previousImplicitIndex // Relative index (use last ordinary/implicit index)
      else -> indexString.substring(0, indexString.length - 1).toInt() - 1
    }

    // Be more explicit on the error
    if (argIndex < 0) {
      throw IndexOutOfBoundsException("Relative argument indexes must follow an inferred/ordinary index.")
    } else if (argIndex > state.formatArgs.lastIndex) {
      throw IndexOutOfBoundsException("Argument index of $argIndex cannot be satisfied, only ${state.formatArgs.size} arguments provided")
    }

    return state.formatArgs[argIndex]
  }

  /**
   * A simple state to keep track of shared values when formatting
   * a [SpannableStringBuilder].
   *
   * @param locale The [Locale] to use when formatting the individual [formatArgs]
   * @param formatArgs The arguments to format and replace the format placeholders with
   * @param previousImplicitIndex The last ordinary (positional) index used when formatting.
   *                              A value of `-1` indicates no previous ordinary index was found
   */
  @Suppress("ArrayInDataClass")
  private data class FormatState(
    val locale: Locale,
    val formatArgs: Array<out Any>,

    /**
     * Implicit indexes refer to the non-indexed format specifiers such as "%s" instead of
     * the explicitly indexed specifier "%1$s"
     */
    var previousImplicitIndex: Int = -1,
  )
}