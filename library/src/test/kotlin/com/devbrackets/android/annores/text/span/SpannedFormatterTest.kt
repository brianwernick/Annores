package com.devbrackets.android.annores.text.span

import android.graphics.Typeface
import android.text.Annotation
import android.text.ParcelableSpan
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
import android.text.style.StyleSpan
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IndexOutOfBoundsException
import java.util.Locale

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SpannedFormatterTest {

  @Test
  fun formatNothing() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A string")

    // When
    val actual = SpannedFormatter.format(locale, spanned)

    // Then
    Assert.assertEquals("A string", actual.toString())
  }

  @Test
  fun formatPercent() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A string %%")

    // When
    val actual = SpannedFormatter.format(locale, spanned)

    // Then
    Assert.assertEquals("A string %", actual.toString())
  }

  @Test
  fun formatNewLine() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A%nstring")

    // When
    val actual = SpannedFormatter.format(locale, spanned)

    // Then
    Assert.assertEquals("A${System.lineSeparator()}string", actual.toString())
  }

  @Test
  fun formatSimplePlaceholder() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %s")

    // When
    val actual = SpannedFormatter.format(locale, spanned, "strings")

    // Then
    Assert.assertEquals("A placeholder for strings", actual.toString())
  }

  @Test
  fun formatPositionalPlaceholder() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %1\$s")

    // When
    val actual = SpannedFormatter.format(locale, spanned, "strings")

    // Then
    Assert.assertEquals("A placeholder for strings", actual.toString())
  }

  @Test
  fun formatIncorrectPositionalPlaceholder() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %2\$s")

    // When / Then
    Assert.assertThrows(IndexOutOfBoundsException::class.java) {
      SpannedFormatter.format(locale, spanned, "strings")
    }
  }

  @Test
  fun formatPositionalPlaceholders() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %2\$s and %1\$s")

    // When
    val actual = SpannedFormatter.format(locale, spanned, "more strings", "strings")

    // Then
    Assert.assertEquals("A placeholder for strings and more strings", actual.toString())
  }

  @Test
  fun formatRelativePlaceholders() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %s and %<s")

    // When
    val actual = SpannedFormatter.format(locale, spanned, "strings")

    // Then
    Assert.assertEquals("A placeholder for strings and strings", actual.toString())
  }

  @Test
  fun formatIncorrectRelativePlaceholder() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %<s")

    // When / Then
    Assert.assertThrows(IndexOutOfBoundsException::class.java) {
      SpannedFormatter.format(locale, spanned, "strings")
    }
  }

  @Test
  fun formatPlaceholderWithDecimals() {
    // Given
    val locale = Locale("en", "us")
    val spanned = SpannedString("A placeholder for %.2f")

    // When
    val actual = SpannedFormatter.format(locale, spanned, 0.1234)

    // Then
    Assert.assertEquals(actual.toString(), "A placeholder for 0.12")
  }

  @Test
  fun formatSimplePlaceholderWithSpan() {
    // Given
    val locale = Locale("en", "us")
    val originalSpanned = SpannableStringBuilder("A placeholder %s").apply {
      // Bold "%s"
      setSpan(StyleSpan(Typeface.BOLD), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    // When
    val actual = SpannedFormatter.format(locale, originalSpanned, "with spans")

    // Then
    Assert.assertEquals("A placeholder with spans", actual.toString())
    actual.assertSpanIs<StyleSpan>(0, 14, 24) {
      Assert.assertEquals(Typeface.BOLD, it.style)
    }
  }

  @Test
  fun formatSimplePlaceholderWithNestedSpans() {
    // Given
    val locale = Locale("en", "us")
    val originalSpanned = SpannableStringBuilder("A placeholder %s").apply {
      // Bold "%s"
      setSpan(StyleSpan(Typeface.BOLD), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      setSpan(Annotation("key", "value"), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    // When
    val actual = SpannedFormatter.format(locale, originalSpanned, "with spans")

    // Then
    Assert.assertEquals("A placeholder with spans", actual.toString())
    actual.assertSpanIs<StyleSpan>(0, 14, 24) {
      Assert.assertEquals(Typeface.BOLD, it.style)
    }
    actual.assertSpanIs<Annotation>(1, 14, 24)
  }

  @Test
  fun formatSimplePlaceholderWithMultipleSpans() {
    // Given
    val locale = Locale("en", "us")
    val originalSpanned = SpannableStringBuilder("A %s placeholder with %s").apply {
      // Bold first "%s"
      setSpan(StyleSpan(Typeface.BOLD), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      // Italic second "%s"
      setSpan(StyleSpan(Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    // When
    val actual = SpannedFormatter.format(locale, originalSpanned, "bold", "italic text")

    // Then
    Assert.assertEquals("A bold placeholder with italic text", actual.toString())
    actual.assertSpanIs<StyleSpan>(0, 2, 6) {
      Assert.assertEquals(Typeface.BOLD, it.style)
    }

    actual.assertSpanIs<StyleSpan>(1, 24, 35) {
      Assert.assertEquals(Typeface.ITALIC, it.style)
    }
  }

  private inline fun <reified T: ParcelableSpan> Spanned.assertSpanIs(
    spanIndex: Int,
    startIndex: Int,
    endIndex: Int,
    additionalAssertion: (T) -> Unit = {}
  ) {
    val spans = getSpans(0, length, ParcelableSpan::class.java)
    if (spanIndex >= spans.size) {
      Assert.fail("Unable to assert span at index $spanIndex, only $length spans exist")
    }

    val span = spans[spanIndex]
    Assert.assertTrue("Expected span of type ${T::class.simpleName}, found ${span::class.simpleName}", span is T)

    val actualStart = getSpanStart(span)
    Assert.assertEquals("Expected a start index of $startIndex, found $actualStart", startIndex, actualStart)

    val actualEnd = getSpanEnd(span)
    Assert.assertEquals("Expected an end index of $endIndex, found $actualEnd", endIndex, actualEnd)

    additionalAssertion(span as T)
  }
}