package com.pengmj.learnjetpackcompose.example07

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example07ProgramEntry(
    modifier: Modifier = Modifier,
) {

    Row(
            modifier = modifier
                    .background(Color.Gray)
                    // 重点：Row的高度根据子元素高度决定
                    .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        Text(
                modifier = Modifier
                        .weight(1.0f)
                        .background(Color.Red.copy(alpha = 0.1f))
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.Start),
                text = "Left",
        )

        VerticalDivider(
                color = Color.Black,
                modifier = Modifier
                        .width(1.0.dp)
                        .fillMaxHeight(),
        )

        Text(
                modifier = Modifier
                        .weight(1.0f)
                        .background(Color.Red.copy(alpha = 0.2f))
                        .padding(10.dp)
                        .wrapContentWidth(Alignment.End),
                text = "Right",
        )
    }
}

