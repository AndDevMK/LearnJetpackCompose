package com.pengmj.learnjetpackcompose.example08

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import java.util.UUID

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

data class TodoItemModel(
    val id: UUID = UUID.randomUUID(),
    val text: String,
    val icon: TodoIcon = TodoIcon.Default,
)

enum class TodoIcon(
    val imageVector: ImageVector,
    val contentDescription: String,
) {

    Email(imageVector = Icons.Filled.Email, contentDescription = "发邮件"),

    Call(imageVector = Icons.Filled.Call, contentDescription = "打电话"),

    ShoppingCart(imageVector = Icons.Filled.ShoppingCart, contentDescription = "去购物"),

    Build(imageVector = Icons.Filled.Build, contentDescription = "修手机"),

    Star(imageVector = Icons.Filled.Star, contentDescription = "去收藏");

    companion object {
        val Default = Email
    }

}
