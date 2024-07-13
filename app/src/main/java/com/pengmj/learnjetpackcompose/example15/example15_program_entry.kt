package com.pengmj.learnjetpackcompose.example15

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example15ProgramEntry(
    modifier: Modifier = Modifier,
) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(
            modifier = modifier
                    .clickable { isExpanded = !isExpanded }
                    .animateContentSize(),
    ) {
        Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
        ) {
            Text(
                    text = "余华简介",
                    modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 10.dp),
            )
        }

        if (isExpanded) {

            Text(
                    text = "余华，汉族，1960年4月3日出生于浙江省杭州市，浙江嘉兴人，祖籍山东高唐， [3]中国当代作家，中国作家协会委员会委员。 ",
                    modifier = Modifier.padding(all = 10.dp),
            )
        }
    }
}