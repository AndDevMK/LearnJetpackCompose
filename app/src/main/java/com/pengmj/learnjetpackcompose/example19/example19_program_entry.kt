package com.pengmj.learnjetpackcompose.example19

import android.util.Log
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * @author MinKin
 * @date 2024/7/14
 * @desc
 */

@Composable
fun Example19ProgramEntry(
    modifier: Modifier = Modifier,
) {
    GestureForTransformable(modifier = modifier)
}

/**
 * 手势之点击
 */
@Composable
private fun GestureForClick(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
    ) {

        val (count, setCount) = remember { mutableIntStateOf(0) }

        Box(
                modifier = Modifier
                        // pointerInput的detectTapGestures和clickable互斥，谁后设置就执行谁
                        // .pointerInput(Unit) {
                        //     detectTapGestures(
                        //             onDoubleTap = {
                        //                 Log.e("MinKin", "onDoubleTap事件")
                        //             },
                        //             onLongPress = {
                        //                 Log.e("MinKin", "onLongPress事件")
                        //             },
                        //             onPress = {
                        //                 Log.e("MinKin", "onPress事件")
                        //             },
                        //             onTap = {
                        //                 Log.e("MinKin", "onTap事件")
                        //             },
                        //     )
                        // }
                        .clickable {
                            Log.e("MinKin", "clickable")
                            setCount(count + 1)
                        }
                        .size(100.dp)
                        .background(color = Color.Gray)
                        .align(Alignment.Center),
        ) {
            Text(
                    text = "$count",
                    modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

/**
 * 手势之滚动修饰符
 */
@Composable
private fun GestureForScroll(modifier: Modifier) {

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        scrollState.animateScrollTo(2000)  // 滚动到2000px位置
    }

    Column(
            modifier = modifier.verticalScroll(scrollState),
    ) {
        repeat(30) {
            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
            ) {
                Text(
                        text = "Item $it",
                        modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

/**
 * 手势之可滚动修饰符
 */
@Composable
private fun GestureForScrollable(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
    ) {

        var offset by remember { mutableFloatStateOf(0f) }

        Box(
                modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Gray)
                        .align(Alignment.Center)
                        .scrollable(
                                state = rememberScrollableState { delta ->
                                    offset += delta
                                    delta
                                },
                                orientation = Orientation.Vertical,
                        ),
        ) {
            Text(
                    text = "$offset",
                    modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

/**
 * 手势之嵌套滚动
 * Tips：简单的嵌套滚动无需您执行任何操作。启动滚动操作的手势会自动从子级传播到父级，这样一来，当子级无法进一步滚动时，手势就会由其父元素处理。
 */
@Composable
private fun GestureForNestedScroll(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
    ) {
        Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            repeat(10) {
                Box(
                        modifier = Modifier
                                .size(200.dp)
                                .border(width = 10.dp, color = Color.LightGray)
                                .padding(all = 10.dp)
                                .verticalScroll(rememberScrollState()),
                ) {
                    Box(
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .background(Color.Yellow)
                    ) {
                        Text(
                                text = "Item $it start",
                                modifier = Modifier.align(Alignment.TopStart),
                        )

                        Text(
                                text = "Item $it end",
                                modifier = Modifier.align(Alignment.BottomStart),
                        )
                    }
                }
            }
        }
    }
}

/**
 * 手势之拖动
 */
@Composable
private fun GestureForDraggable(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
    ) {

        Column {

            Box(
                    modifier = Modifier
                            .size(200.dp)
                            .background(Color.Gray),
            ) {

                var offsetX by remember { mutableFloatStateOf(0f) }

                Box(
                        modifier = Modifier
                                .offset {
                                    IntOffset(x = offsetX.roundToInt(), y = 0)
                                }
                                .align(Alignment.Center)
                                .size(30.dp)
                                .background(Color.Blue)
                                .draggable(
                                        orientation = Orientation.Horizontal,
                                        state = rememberDraggableState { delta ->
                                            offsetX += delta
                                        },
                                ),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                    modifier = Modifier
                            .size(200.dp)
                            .background(Color.Gray),
            ) {

                var offsetX by remember { mutableFloatStateOf(0f) }
                var offsetY by remember { mutableFloatStateOf(0f) }

                Box(
                        modifier = Modifier
                                .offset {
                                    IntOffset(x = offsetX.roundToInt(), y = offsetY.roundToInt())
                                }
                                .size(30.dp)
                                .background(Color.Blue)
                                .align(Alignment.Center)
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                    }
                                },
                )
            }
        }
    }
}


enum class DragAnchors {
    Start, End,
}

/**
 * 手势之滑动
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GestureForAnchoredDraggable(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
    ) {

        Box(
                modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .background(Color.DarkGray),
        ) {

            // 拖动速度阈值
            val velocityThreshold = with(LocalDensity.current) {
                10.dp.toPx()
            }

            // 滚动锚点结束位置
            val dragAnchorsEnd = with(LocalDensity.current) {
                50.dp.toPx()
            }

            val anchoredDraggableState = remember {
                AnchoredDraggableState(
                        initialValue = DragAnchors.Start,
                        // 用于根据锚点之间的距离确定内容是以动画形式呈现到下一个锚点还是返回到原始锚点
                        positionalThreshold = { totalDistance ->
                            totalDistance * 0.3f
                        },
                        // 如果拖动速度超过此阈值，那么我们将对下一个锚点进行动画处理，否则使用positionalThreshold
                        velocityThreshold = {
                            velocityThreshold
                        },
                        animationSpec = TweenSpec(
                                durationMillis = 500,
                                easing = EaseInOut,
                        ),
                        // 可用于否决对可拖动内容的更改
                        confirmValueChange = {
                            true
                        },
                ).apply {
                    // 指定 2 个锚点，以便可以在这 2 个锚点之间拖动内容，但我们可以根据需要添加任意数量的锚点
                    updateAnchors(
                            // 当状态为“开始”时，内容将偏移量为 0 像素，当状态为“结束”时，内容偏移量将为 dragAnchorsEnd 像素。
                            DraggableAnchors {
                                DragAnchors.Start at 0f
                                DragAnchors.End at dragAnchorsEnd
                            },
                    )
                }
            }

            Box(
                    modifier = Modifier
                            .offset {
                                IntOffset(
                                        x = anchoredDraggableState
                                                .requireOffset()
                                                .roundToInt(),
                                        y = 0,
                                )
                            }
                            .size(50.dp)
                            .background(Color.LightGray)
                            .anchoredDraggable(
                                    state = anchoredDraggableState,
                                    orientation = Orientation.Horizontal,
                            ),
            )
        }
    }
}


/**
 * 手势之多点触控
 */
@Composable
private fun GestureForTransformable(modifier: Modifier) {
    Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
    ) {

        var scale by remember { mutableFloatStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        var rotation by remember { mutableFloatStateOf(0f) }

        val transformableState = rememberTransformableState { zoomChange, panChange, rotationChange ->
            scale *= zoomChange
            offset += panChange
            rotation += rotationChange
        }

        Box(
                modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            rotationZ = rotation
                            translationX = offset.x
                            translationY = offset.y
                        }
                        .transformable(transformableState)
                        .width(50.dp)
                        .height(100.dp)
                        .background(Color.Gray),
        )
    }
}