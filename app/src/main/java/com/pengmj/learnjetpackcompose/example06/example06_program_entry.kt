package com.pengmj.learnjetpackcompose.example06

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example06ProgramEntry(
    modifier: Modifier = Modifier,
) {
    SuperUsingConstraintLayout(modifier = modifier)
}

@Composable
private fun SimpleUsingConstraintLayout(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
            modifier = modifier
                    .fillMaxSize()
                    .background(Color.Gray),
    ) {

        // 创建引用
        val (button1, button2, text1, text2) = createRefs()

        // 创建垂直链
        createVerticalChain(button1, text1, chainStyle = ChainStyle.Spread)

        // button1、button2右侧创建屏障
        val endBarrier = createEndBarrier(button1, button2)

        Button(
                modifier = Modifier.constrainAs(ref = button1) {
                    // 顶部的约束为父容器顶部
                    top.linkTo(anchor = parent.top)
                    // 左侧的约束为父容器左侧
                    // 右侧的约束为父容器右侧
                    centerHorizontallyTo(other = parent)
                    // 底部的约束为Text顶部
                    bottom.linkTo(anchor = text1.top)
                    // 宽度为屏幕1/3
                    width = Dimension.percent(0.33f)
                },
                onClick = {

                },
        ) {
            Text(text = "Button1")
        }

        Text(
                modifier = Modifier.constrainAs(ref = text1) {
                    // 顶部的约束为Button底部
                    top.linkTo(anchor = button1.bottom)
                    // 左侧的约束为父容器左侧
                    // 右侧的约束为父容器右侧
                    centerHorizontallyTo(other = parent)
                    // 底部的约束为父容器底部
                    bottom.linkTo(anchor = parent.bottom)
                },
                text = "MinKin",
        )

        Button(
                modifier = Modifier.constrainAs(ref = button2) {
                    // 顶部的约束为Button1底部
                    top.linkTo(anchor = button1.bottom)
                    // 左侧的约束为Button左侧
                    start.linkTo(anchor = button1.start)
                    // 宽度为屏幕1/4
                    width = Dimension.percent(0.25f)
                },
                onClick = {

                },
        ) {
            Text(text = "Button2")
        }

        Text(
                modifier = Modifier.constrainAs(text2) {
                    // 顶部的约束为Button1顶部
                    // 底部的约束为Button1底部
                    centerVerticallyTo(button1)
                    // 左侧的约束为屏障
                    start.linkTo(endBarrier, margin = 10.dp)
                    // 右侧的约束为父容器右侧
                    end.linkTo(parent.end)

                    // Text换行
                    width = Dimension.preferredWrapContent
                },
                text = "左侧采用endBarrier定位",
        )
    }
}

@Composable
private fun SuperUsingConstraintLayout(
    modifier: Modifier = Modifier,
) {

    val constraintSet = getConstrainSet()

    ConstraintLayout(
            modifier = modifier
                    .fillMaxSize()
                    .background(Color.Gray),
            constraintSet = constraintSet,
    ) {


        Button(
                modifier = Modifier.layoutId("button"),
                onClick = {

                },
        ) {
            Text(text = "Button")
        }

        Text(
                modifier = Modifier.layoutId("text"),
                text = "MinKin",
        )
    }
}

/**
 * 抽取约束关系
 */
@Composable
private fun getConstrainSet(): ConstraintSet {
    return ConstraintSet {
        val (button, text) = createRefsFor("button", "text")

        // 创建垂直链
        createVerticalChain(button, text, chainStyle = ChainStyle.Spread)

        constrain(ref = button) {
            // 顶部的约束为父容器顶部
            top.linkTo(anchor = parent.top)
            // 左侧的约束为父容器左侧
            // 右侧的约束为父容器右侧
            centerHorizontallyTo(other = parent)
            // 底部的约束为Text顶部
            bottom.linkTo(anchor = text.top)
            // 宽度为屏幕1/3
            width = Dimension.percent(0.33f)
        }

        constrain(ref = text) {
            // 顶部的约束为Button底部
            top.linkTo(anchor = button.bottom)
            // 左侧的约束为父容器左侧
            // 右侧的约束为父容器右侧
            centerHorizontallyTo(other = parent)
            // 底部的约束为父容器底部
            bottom.linkTo(anchor = parent.bottom)
        }
    }
}
