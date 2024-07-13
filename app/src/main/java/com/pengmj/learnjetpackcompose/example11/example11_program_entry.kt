package com.pengmj.learnjetpackcompose.example11

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

/**
 * 1、追踪读取
 * compositionLocalOf:
 * 当你创建一个通过 compositionLocalOf 定义的 CompositionLocal 并且它的值发生变化时，
 * 只有那些直接依赖并读取这个 CompositionLocal 的可组合函数（即在它们的作用域内直接调用这个 CompositionLocal 的函数）会被重新构建（recompose）。
 * 这意味着Compose只会追踪对这个 CompositionLocal 的直接读取，并仅在必要时更新UI部分。
 *
 * 2、性能影响
 * staticCompositionLocalOf:
 * 当你使用 staticCompositionLocalOf 创建一个 CompositionLocal，并且它的值发生变化时，
 * 整个提供这个 CompositionLocal 的可组合函数及其子树都将被重新构建，无论这些子树是否直接读取了这个 CompositionLocal。
 * 这是因为Compose不会追踪对 staticCompositionLocalOf 创建的 CompositionLocal 的读取，所以一旦值改变，它假设所有可能依赖于它的内容都需要更新。
 * 这通常用于那些很少或永远不会改变的值，因为它可以避免不必要的读取追踪，从而提高性能
 *
 * 3、选择使用哪一个？
 * - 如果你的值是动态的，经常需要更新，并且你只希望受影响的部分被重新构建，那么你应该使用 compositionLocalOf
 * - 如果你的值是静态的，几乎不会改变，或者你确定改变时需要整个子树都更新，那么 staticCompositionLocalOf 更合适，因为它可以提供更好的性能
 */

@Composable
fun Example11ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val (color, setColor) = remember {
        mutableStateOf(Color.Black)
    }

    Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = compositionLocalName)
        Spacer(modifier = Modifier.height(20.dp))
        CompositionLocalProvider(currentCompositionLocal provides color) {
            BoxWithTag(size = 400.dp, tag = "First $compositionFlag", backgroundColor = Color.Red) {
                BoxWithTag(size = 300.dp, tag = "Second $compositionFlag", backgroundColor = currentCompositionLocal.current) {
                    BoxWithTag(size = 200.dp, tag = "Third $compositionFlag", backgroundColor = Color.Blue)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
                onClick = {
                    compositionFlag = "Recomposition"
                    setColor(Color.Green)
                },
        ) {
            Text(text = "Change Second BackgroundColor")
        }
    }
}

const val static = true

var compositionLocalName = ""

@SuppressLint("CompositionLocalNaming") val currentCompositionLocal = if (static) {
    compositionLocalName = "StaticProvidableCompositionLocal场景"
    staticCompositionLocalOf { Color.Black }
}
else {
    compositionLocalName = "DynamicProvidableCompositionLocal场景"
    compositionLocalOf { Color.Black }
}

var compositionFlag = "init"

@Composable
private fun BoxWithTag(
    size: Dp,
    tag: String,
    backgroundColor: Color,
    content: @Composable (() -> Unit)? = null
) {
    Box(
            modifier = Modifier
                    .size(size)
                    .background(color = backgroundColor),
            contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                    text = tag,
                    color = Color.White,
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (content != null) {
                content()
            }
        }
    }
}