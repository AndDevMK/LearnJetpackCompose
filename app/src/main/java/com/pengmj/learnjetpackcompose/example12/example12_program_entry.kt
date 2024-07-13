package com.pengmj.learnjetpackcompose.example12

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example12ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val (flag, setFlag) = remember { mutableStateOf(true) }

    // 除此之外，还有animateDpAsState、animateIntAsState、animateSizeAsState等等
    val backgroundColor by animateColorAsState(
            targetValue = if (flag) Color.White else Color.Gray,
            animationSpec = TweenSpec(
                    durationMillis = 300,
                    easing = EaseInOut,
            ),
            label = "ColorAnimation",
    )

    Box(
            modifier = modifier
                    .fillMaxSize()
                    .background(color = backgroundColor),
    ) {
        Button(
                onClick = {
                    setFlag(!flag)
                },
        ) {
            Text(text = "Change BackgroundColor")
        }
    }
}