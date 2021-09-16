package com.hdesrosiers.animatedprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
      ) {
        CircularProgressBar(
          percentage = 0.75f,
          maxNumber = 300
        )
      }
    }
  }
}

@Composable
fun CircularProgressBar(
  percentage: Float,
  maxNumber: Int,
  fontSize: TextUnit = 28.sp,
  radius: Dp = 50.dp,
  color: Color = Color.Green,
  strokeWidth: Dp = 8.dp,
  animDuration: Int = 1000,
  animDelay: Int = 0
) {

  /**
   * The original tutorial with [animateFloatAsState]
   */
//  var animationPlayed by remember { mutableStateOf(false) }

//  val currentPercentage = animateFloatAsState(
//    targetValue = if (animationPlayed) percentage else 0f,
//    animationSpec = tween(
//      durationMillis = animDuration,
//      delayMillis = animDelay
//    )
//  )

//  LaunchedEffect(key1 = true) {
//    animationPlayed = true
//  }

  /**
   * Using remember { Animatable() } instead
   */

  val currentPercentage = remember { Animatable(0f) }

  LaunchedEffect(key1 = percentage) {
    currentPercentage.animateTo(
      targetValue = percentage,
      animationSpec = tween(
        durationMillis = animDuration,
        delayMillis = animDelay
      )
    )
  }

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.size(radius * 2)
  ) {
    Canvas(
      modifier = Modifier.fillMaxSize()
    ) {
      drawArc(
        color = color,
        startAngle = 270f,
        sweepAngle = 360 * currentPercentage.value,
        useCenter = false,
        style = Stroke(
          width = strokeWidth.toPx(),
          cap = StrokeCap.Round
        )
      )
    }
  }
  Text(
    text = "${(currentPercentage.value * maxNumber).toInt()}",
    textAlign = TextAlign.Center,
    fontSize = fontSize,
    fontWeight = FontWeight.Bold
  )
}













































