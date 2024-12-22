package com.devbrackets.android.annoresdemo.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devbrackets.android.annoresdemo.sample.string.AnnotatedStringScreen
import com.devbrackets.android.annoresdemo.support.theme.DemoTheme

enum class DemoDestination(
  val route: String
) {
  ANNOTATED_STRINGS("annotatedStrings")
}

@Composable
fun DemoNavGraph() {
  val navController = rememberNavController()

  DemoTheme {
    DemoNavHost(
      navController = navController,
      startDestination = DemoDestination.ANNOTATED_STRINGS.route,
      modifier = Modifier
        .fillMaxSize()
        .background(DemoTheme.colors.background)
    ) {
      composable(DemoDestination.ANNOTATED_STRINGS.route) {
        AnnotatedStringScreen()
      }
    }
  }
}