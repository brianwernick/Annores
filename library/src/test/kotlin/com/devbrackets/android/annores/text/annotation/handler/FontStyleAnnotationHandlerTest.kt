package com.devbrackets.android.annores.text.annotation.handler

import android.text.Annotation
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class FontStyleAnnotationHandlerTest {

  @Test
  fun applyNotHandled() {
    // Given
    val annotation = Annotation("somethingElse", "value")
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)

    // Then
    Assert.assertFalse(actual)
  }

  @Test
  fun applyEmptyValue() {
    // Given
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, "")
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)

    // Then
    Assert.assertTrue(actual)
  }

  @Test
  fun applyBold() {
    // Given
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_BOLD)
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)
    val annotatedString = builder.toAnnotatedString()

    // Then
    Assert.assertTrue(actual)
    annotatedString.assertSpanStyle(0, 0, 8) {
      Assert.assertEquals(FontWeight.Bold, it.fontWeight)
    }
  }

  @Test
  fun applyItalic() {
    // Given
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_ITALIC)
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)
    val annotatedString = builder.toAnnotatedString()

    // Then
    Assert.assertTrue(actual)
    annotatedString.assertSpanStyle(0, 0, 8) {
      Assert.assertEquals(FontStyle.Italic, it.fontStyle)
    }
  }

  @Test
  fun applyUnderline() {
    // Given
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_UNDERLINE)
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)
    val annotatedString = builder.toAnnotatedString()

    // Then
    Assert.assertTrue(actual)
    annotatedString.assertSpanStyle(0, 0, 8) {
      Assert.assertEquals(TextDecoration.Underline, it.textDecoration)
    }
  }

  @Test
  fun applyStrike() {
    // Given
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, FontStyleAnnotationHandler.VALUE_STRIKE_THROUGH)
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)
    val annotatedString = builder.toAnnotatedString()

    // Then
    Assert.assertTrue(actual)
    annotatedString.assertSpanStyle(0, 0, 8) {
      Assert.assertEquals(TextDecoration.LineThrough, it.textDecoration)
    }
  }

  @Test
  fun applyMultiple() {
    // Given
    val styles = listOf(FontStyleAnnotationHandler.VALUE_BOLD, FontStyleAnnotationHandler.VALUE_ITALIC)
    val annotation = Annotation(FontStyleAnnotationHandler.KEY, styles.joinToString(separator = ","))
    val builder = AnnotatedString.Builder("The text")

    // When
    val actual = FontStyleAnnotationHandler.apply(annotation, 0, 8, builder)
    val annotatedString = builder.toAnnotatedString()

    // Then
    Assert.assertTrue(actual)
    annotatedString.assertSpanStyle(0, 0, 8) {
      Assert.assertEquals(FontWeight.Bold, it.fontWeight)
      Assert.assertEquals(FontStyle.Italic, it.fontStyle)
    }
  }

  private fun AnnotatedString.assertSpanStyle(
    spanIndex: Int,
    startIndex: Int,
    endIndex: Int,
    additionalAssertion: (SpanStyle) -> Unit = {}
  ) {
    Assert.assertTrue("SpanStyle for index $spanIndex doesn't exist", spanIndex in spanStyles.indices)

    val spanStyle = spanStyles[spanIndex]
    Assert.assertEquals("Expected a start index of $startIndex, found ${spanStyle.start}", startIndex, spanStyle.start)
    Assert.assertEquals("Expected an end index of $endIndex, found ${spanStyle.end}", endIndex, spanStyle.end)

    additionalAssertion(spanStyle.item)
  }
}