package com.pengmj.learnjetpackcompose.example05

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pengmj.learnjetpackcompose.R
import kotlin.math.max

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example05ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val tags = listOf(
            "Java",
            "Kotlin",
            "Android",
            "Jetpack Compose",
            "NDK",
            "Harmony NEXT",
            "Flutter",
            "Vue",
            "React",
            "UniApp",
            "Dart",
            "Javascript",
            "Typescript",
            "C",
            "C++",
            "JNI",
            "CSS",
            "HTML",
    )

    val scrollState = rememberScrollState()
    Row(
            modifier = modifier
                    .background(Color.Gray)
                    .horizontalScroll(state = scrollState),
    ) {
        LikeStaggeredGrid(modifier = Modifier) {
            tags.forEach {
                LikeStaggeredGridItem(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        text = it,
                )
            }
        }
    }

}

@Composable
private fun LikeStaggeredGrid(
    modifier: Modifier = Modifier,
    rowCount: Int = 3,
    content: @Composable () -> Unit,
) {

    Layout(content = content, modifier = modifier) { measurables, constraints ->

        // 记录每行宽度
        val rowWidths = IntArray(size = rowCount) { 0 }
        // 记录每行高度
        val rowHeights = IntArray(size = rowCount) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            // 计算每一行的宽高
            // 确保所有元素都能被分布到每一行中去
            val rowIndex = index % rowCount

            // 每行宽度
            rowWidths[rowIndex] += placeable.width

            // 每行高度（以该行中元素的最高高度为准）
            rowHeights[rowIndex] = max(rowHeights[rowIndex], placeable.height)

            placeable
        }

        // 计算LikeStaggeredGrid宽高
        val width = rowWidths.max()
        val height = rowHeights.sum()

        // 每一行y坐标
        val rowYs = IntArray(size = rowCount) { 0 }
        // 索引从1开始，因为第一行Y坐标为0，row[0] = 0
        for (i in 1 until rowCount) {
            // 第二行y坐标 = 第一行y坐标 + 第一行高度，以此类推
            rowYs[i] = rowYs[i - 1] + rowHeights[i - 1]
        }

        layout(width = width, height = height) {
            // 每一行布局最新一个元素时的x坐标
            val rowXs = IntArray(size = rowCount) { 0 }
            placeables.mapIndexed { index, placeable ->
                val rowIndex = index % rowCount
                placeable.placeRelative(
                        x = rowXs[rowIndex],
                        y = rowYs[rowIndex],
                )
                rowXs[rowIndex] += placeable.width
            }
        }
    }
}

@Composable
private fun LikeStaggeredGridItem(
    modifier: Modifier = Modifier,
    text: String,
) {
    Card(
            modifier = modifier,
            border = BorderStroke(width = Dp.Hairline, color = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(25.dp),
    ) {
        Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "头像",
                    modifier = Modifier
                            // Set image size to 40 dp
                            .size(size = 36.dp)
                            // Clip image to be shaped as a circle
                            .clip(shape = CircleShape)
                            .border(
                                    width = 1.5.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape,
                            ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                    text = text,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

