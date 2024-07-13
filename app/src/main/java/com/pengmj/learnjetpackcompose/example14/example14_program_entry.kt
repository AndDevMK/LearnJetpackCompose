package com.pengmj.learnjetpackcompose.example14

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example14ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val (visible, setVisible) = remember { mutableStateOf(false) }

    Box(
            modifier = modifier.fillMaxSize(),
    ) {
        Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    setVisible(!visible)
                },
        ) {
            Text(text = "ShowMessage")
        }

        ShowMessage(visible = visible)
    }
}

@Composable
private fun ShowMessage(visible: Boolean) {
    AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                    // -fullHeight -> 0
                    initialOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = TweenSpec(
                            durationMillis = 300,
                            easing = EaseIn,
                    ),
            ),
            exit = slideOutVertically(
                    // 0 -> -fullHeight
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = TweenSpec(
                            durationMillis = 300,
                            easing = EaseIn,
                    ),
            ),
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.LightGray),
        ) {
            Text(
                    text = "“美国在台协会”新处长提“一中”，台媒发现在赖办新闻稿中“被消音”",
                    modifier = Modifier
                            .padding(all = 10.dp)
                            .align(Alignment.CenterStart),
            )
        }
    }
}