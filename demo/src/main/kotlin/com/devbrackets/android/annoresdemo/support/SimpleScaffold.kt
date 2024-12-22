package com.devbrackets.android.annoresdemo.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devbrackets.android.annoresdemo.support.theme.DemoTheme

/**
 * A wrapper around the [Scaffold] to standardize throughout
 * the Demo app.
 */
@Composable
fun SimpleScaffold(
  title: String,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(DemoTheme.colors.background),
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
        },
        contentColor = DemoTheme.colors.text05,
        windowInsets = WindowInsets.statusBars
      )
    },
    content = content
  )
}