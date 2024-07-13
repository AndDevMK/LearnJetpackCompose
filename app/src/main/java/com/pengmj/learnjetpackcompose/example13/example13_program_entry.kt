package com.pengmj.learnjetpackcompose.example13

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example13ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val lazyListState = rememberLazyListState()

    Box(
            modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
                state = lazyListState,
        ) {
            items(30) {
                Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                ) {
                    Text(
                            text = "Item $it",
                    )
                }
            }
        }

        FloatingActionButton(
                modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 40.dp, end = 20.dp),
                onClick = {},
        ) {
            Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
            ) {
                Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "编辑",
                )

                // 可见性动画
                AnimatedVisibility(
                        visible = lazyListState.isScrollingUp(),
                ) {
                    Text(
                            text = "Edit",
                            modifier = Modifier.padding(start = 10.dp),
                            style = TextStyle(
                                    fontSize = 20.sp,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            }
            else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
