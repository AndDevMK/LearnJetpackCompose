package com.pengmj.learnjetpackcompose.example16

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example16ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val tabs = listOf(
            BottomTabModel(tabPage = TabPage.HOME, text = "首页", icon = Icons.Filled.Home),
            BottomTabModel(tabPage = TabPage.MY, text = "我的", icon = Icons.Filled.Person),
    )

    val (currentTab, setCurrentTab) = remember { mutableStateOf(TabPage.HOME) }

    Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
    ) {
        TabRow(
                selectedTabIndex = currentTab.ordinal,
                indicator = { tabPositions ->
                    BottomTabIndicator(
                            tabPage = currentTab,
                            tabPositions = tabPositions,
                    )
                },
                divider = {

                },
        ) {
            tabs.forEach {
                BottomTab(
                        selected = currentTab == it.tabPage,
                        text = it.text,
                        icon = it.icon,
                        onClick = {
                            setCurrentTab(it.tabPage)
                        },
                )
            }

        }
    }
}

@Composable
private fun BottomTabIndicator(
    tabPage: TabPage,
    tabPositions: List<TabPosition>,
) {
    val transition = updateTransition(
            targetState = tabPage,
            label = "BottomTabIndicator",
    )

    val indicatorLeft by transition.animateDp(
            label = "IndicatorLeft",
            transitionSpec = {
                TweenSpec(
                        // 对于left边，如果targetState从TabPage.HOME切换到TabPage.MY，left边动画慢，right边动画快，反之亦然
                        durationMillis = if (TabPage.HOME isTransitioningTo TabPage.MY) 500 else 250,
                        easing = EaseInOut,
                )
            },
    ) {
        tabPositions[it.ordinal].left
    }

    val indicatorRight by transition.animateDp(
            label = "IndicatorRight",
            transitionSpec = {
                TweenSpec(
                        // 对于right边，如果targetState从TabPage.HOME切换到TabPage.MY，left边动画慢，right边动画快，反之亦然
                        durationMillis = if (TabPage.HOME isTransitioningTo TabPage.MY) 250 else 500,
                        easing = EaseInOut,
                )
            },
    ) {
        tabPositions[it.ordinal].right
    }

    val borderColor by transition.animateColor(
            label = "BorderColor",
            transitionSpec = {
                TweenSpec(
                        durationMillis = 500,
                        easing = EaseInOut,
                )
            },
    ) {
        if (it == TabPage.HOME) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.8f)
    }

    Box(
            modifier = Modifier
                    .wrapContentWidth(align = Alignment.Start)
                    .offset(x = indicatorLeft)
                    .width(indicatorRight - indicatorLeft)
                    .border(
                            width = 1.dp,
                            color = borderColor,
                    ),
    )
}


@Composable
private fun BottomTab(
    selected: Boolean,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Tab(
            selected = selected,
            text = {
                Text(text = text)
            },
            icon = {
                Icon(
                        imageVector = icon,
                        contentDescription = text,
                )
            },
            onClick = onClick,
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = Color.Gray.copy(alpha = 0.8f),
    )
}

data class BottomTabModel(
    val tabPage: TabPage,
    val text: String,
    val icon: ImageVector,
)

enum class TabPage {
    HOME, MY,
}
