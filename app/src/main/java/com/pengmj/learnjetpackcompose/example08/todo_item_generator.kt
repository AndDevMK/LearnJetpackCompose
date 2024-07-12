package com.pengmj.learnjetpackcompose.example08

/**
 * @author MinKin
 * @date 2024/7/12
 * @desc
 */

fun generateTodoItem(): TodoItemModel {
    val text = listOf(
            "发邮件",
            "打电话",
            "去购物",
            "修手机",
            "去收藏",
    ).random()
    return TodoItemModel(
            text = text,
            icon = TodoIcon.entries.toTypedArray().random(),
    )
}