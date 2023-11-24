package com.devbrackets.android.annoresdemo.support

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devbrackets.android.annoresdemo.sample.string.AnnotatedStringScreen
import com.devbrackets.android.annoresdemo.support.theme.DemoTheme
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

enum class DemoDestination(
  val route: String
) {
  ANNOTATED_STRINGS("annotatedStrings")
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun DemoNavGraph() {
  val navController = rememberAnimatedNavController()

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