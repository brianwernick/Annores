package com.devbrackets.android.annoresdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.devbrackets.android.annoresdemo.support.DemoNavGraph

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      DemoNavGraph()
    }
  }
}