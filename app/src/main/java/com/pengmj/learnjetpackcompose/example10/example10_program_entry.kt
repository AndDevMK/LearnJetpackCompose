package com.pengmj.learnjetpackcompose.example10

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * @author MinKin
 * @date 2024/7/12
 * @desc
 */

// 自定义CompositionLocal（注意：LocalContentAlpha在旧版本Jetpack Compose中是有提供的，新版本已经移除）
val LocalContentAlpha = compositionLocalOf { 1f }

object ContentAlpha {
    val high: Float
        get() = ContrastContentAlpha.high

    val medium: Float
        get() = ContrastContentAlpha.medium

    val disabled: Float
        get() = ContrastContentAlpha.disabled
}

private object ContrastContentAlpha {
    const val high: Float = 1.00f
    const val medium: Float = 0.74f
    const val disabled: Float = 0.38f
}

@Composable
fun Example10ProgramEntry(
    modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier,
    ) {
        // 通过CompositionLocal子类ProvidableCompositionLocal的provides中缀表达式返回ProvidedValue对象
        // 也就是CompositionLocalProvider的第一个参数
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {

            TextWithAlpha(text = "Hello Java")

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                TextWithAlpha(text = "Hello Kotlin")

                TextWithAlpha(text = "Hello Flutter")

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {

                    TextWithAlpha(text = "Hello Jetpack Compose")
                }
            }
        }
    }
}

@Composable
private fun TextWithAlpha(text: String) {
    Text(
            text = text,
            style = TextStyle(
                    color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            ),
    )
}
