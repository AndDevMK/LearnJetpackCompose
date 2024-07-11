package com.pengmj.learnjetpackcompose.example04

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example04ProgramEntry(
    modifier: Modifier = Modifier,
) {
    CustomColumn(modifier = modifier.background(Color.Gray)) {
        Text(text = "Java")
        Text(text = "Kotlin")
        Text(text = "Dart")
        Text(text = "Android")
        Text(text = "Flutter")
    }
}

@Composable
private fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
            content = content,
            modifier = modifier,
    ) { measurables, constraints ->

        val placeables = measurables.map { it.measure(constraints) }

        // 这里粗暴点，直接铺满父容器，不包裹内容
        layout(width = constraints.maxWidth, height = constraints.maxHeight) {

            var y = 0

            placeables.forEach {
                it.placeRelative(x = 0, y = y)
                y += it.height
            }
        }
    }
}