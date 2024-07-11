package com.pengmj.learnjetpackcompose.example03

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example03ProgramEntry(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(
                modifier = Modifier
                        .size(200.dp)
                        .background(Color.LightGray),
        ) {
            Text(
                    text = "MinKin",
                    fontSize = 50.sp,
                    modifier = Modifier.padding(top = 100.dp),
            )
        }

        Box(
                modifier = Modifier
                        .size(200.dp)
                        .background(Color.Gray),
        ) {
            Text(
                    text = "MinKin",
                    fontSize = 50.sp,
                    modifier = Modifier.firstBaselineToTop(firstBaselineToTop = 100.dp),
            )
        }
    }
}


private fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = this.then(
        layout { measurable, constraints ->

            // 测量元素
            val placeable = measurable.measure(constraints)
            // 获取元素基线值（Placeable父类Measured中，运算符重载get方法，入参为AlignmentLine）
            val firstBaseline = placeable[FirstBaseline]
            // firstBaselineToTop = y + firstBaseline
            val y = firstBaselineToTop.roundToPx() - firstBaseline

            // 元素高度
            val height = y + placeable.height

            layout(width = 0, height = height) {
                // 元素布局
                placeable.placeRelative(x = 0, y = y)
            }
        },
)