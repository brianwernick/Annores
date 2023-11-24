package com.devbrackets.android.annoresdemo.sample.string

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devbrackets.android.annores.text.annotatedPluralStringResource
import com.devbrackets.android.annoresdemo.R
import com.devbrackets.android.annores.text.annotatedStringResource
import com.devbrackets.android.annores.text.annotation.AnnotatedStringConverter
import com.devbrackets.android.annoresdemo.sample.string.support.ColoredAnnotationHandler
import com.devbrackets.android.annoresdemo.support.SimpleScaffold
import com.devbrackets.android.annoresdemo.support.theme.DemoTheme

@Composable
internal fun AnnotatedStringScreen() {
  SimpleScaffold(
    title = stringResource(R.string.app_name)
  ) {
    ScreenContent()
  }
}

@Composable
private fun ScreenContent() {
  val colorHandlers = remember {
    AnnotatedStringConverter.defaultAnnotationHandlers + ColoredAnnotationHandler
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(DemoTheme.colors.surface)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 24.dp),
    verticalArrangement = Arrangement.spacedBy(32.dp)
  ) {
    Spacer(modifier = Modifier.height(8.dp))

    Sample(
      text = annotatedStringResource(R.string.annotated_simple_defaults),
      description = "These basic html tags (`<b>` & `<i>`) are supported by default",
      modifier = Modifier.fillMaxWidth()
    )

    Sample(
      text = annotatedStringResource(
        R.string.annotated_colors,
        annotationHandlers = colorHandlers
      ),
      description = "Adding support for custom annotations is fairly simple with AnnotationHandlers",
      modifier = Modifier.fillMaxWidth()
    )

    Sample(
      text = annotatedStringResource(R.string.annotated_with_args, 120, 0.3547128),
      description = "Annotations are supported even with string arguments",
      modifier = Modifier.fillMaxWidth()
    )

    Sample(
      text = annotatedPluralStringResource(R.plurals.annotated_simple_defaults, quantity = 12),
      description = "Plurals have the same support level as string resources",
      modifier = Modifier.fillMaxWidth()
    )

    Sample(
      text = annotatedPluralStringResource(R.plurals.annotated_with_args, quantity = 12, 12),
      description = "Including Plurals with string arguments",
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(56.dp))
  }
}

@Composable
private fun Sample(
  text: AnnotatedString,
  description: String,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    elevation = 4.dp
  ) {
    Column(
      modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = text,
        modifier = Modifier.fillMaxWidth()
      )

      Divider(modifier = Modifier.fillMaxWidth())

      Text(
        text = description,
        modifier = Modifier.fillMaxWidth(),
        color = DemoTheme.colors.text40,
        style = MaterialTheme.typography.subtitle2
      )
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun PreviewAnnotatedStringScreen() {
  DemoTheme {
    AnnotatedStringScreen()
  }
}