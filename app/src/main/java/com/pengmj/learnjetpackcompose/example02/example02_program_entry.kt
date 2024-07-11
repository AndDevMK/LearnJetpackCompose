package com.pengmj.learnjetpackcompose.example02

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example02ProgramEntry(
    modifier: Modifier = Modifier,
) {
    LazyScrollList(modifier = modifier)
}

@Composable
private fun NormalScrollList(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(state = scrollState)) {
        repeat(times = 100) {
            ListItem(index = it)
        }
    }
}

@Composable
private fun ListItem(index: Int = 0) {
    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
    ) {
        Text(
                text = "Item $index",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(alignment = Alignment.CenterStart),
        )
    }
}

@Composable
private fun LazyScrollList(modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100
    Column(modifier = modifier) {
        Row {
            Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index = 0)
                        }
                    },
            ) {
                Text(text = "scroll to top")
            }
            Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index = itemCount - 1)
                        }
                    },
            ) {
                Text(text = "scroll to end")
            }
        }
        LazyColumn(state = lazyListState) {
            items(itemCount) {
                ListItem(index = it)
            }
        }
    }
}
