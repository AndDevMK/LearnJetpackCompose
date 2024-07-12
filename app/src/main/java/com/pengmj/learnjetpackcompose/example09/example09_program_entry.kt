package com.pengmj.learnjetpackcompose.example09

import android.os.Parcelable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize

/**
 * @author MinKin
 * @date 2024/7/12
 * @desc
 */

/**
 * 使用parcelize序列化数据，并将数据用rememberSaveable存储，便于页面重组时能够恢复数据（例如：屏幕旋转）
 */
@Composable
fun Example09ProgramEntry(
    modifier: Modifier = Modifier,
) {
    UsingParcelizeAndRememberSaveableWithListSaver(modifier = modifier)
}

@Parcelize
data class User(
    val name: String,
    val age: Int,
) : Parcelable

/**
 * 方式一、Parcelize + rememberSaveable
 */
@Composable
private fun UsingParcelizeAndRememberSaveable(
    modifier: Modifier = Modifier,
) {
    val (user, setUser) = rememberSaveable {
        mutableStateOf(User(name = "MinKin", age = 28))
    }
    Column(modifier = modifier) {
        Button(
                onClick = {
                    setUser(User(name = "John", age = 30))
                },
        ) {
            Text(text = "修改User")
        }
        Text(text = "$user")
    }
}

/**
 * 方式二、Parcelize + rememberSaveable（mapSaver）
 */
@Composable
private fun UsingParcelizeAndRememberSaveableWithMapSaver(
    modifier: Modifier = Modifier,
) {

    val userStateSaver = mapSaver(
            save = {
                mapOf("name" to it.name, "age" to it.age)
            },
            restore = {
                User(name = it["name"] as String, age = it["age"] as Int)
            },
    )

    val (user, setUser) = rememberSaveable(stateSaver = userStateSaver) {
        mutableStateOf(User(name = "MinKin", age = 28))
    }

    Column(modifier = modifier) {
        Button(
                onClick = {
                    setUser(User(name = "John", age = 30))
                },
        ) {
            Text(text = "修改User")
        }
        Text(text = "$user")
    }
}

/**
 * 方式三、Parcelize + rememberSaveable（listSaver）
 */
@Composable
private fun UsingParcelizeAndRememberSaveableWithListSaver(
    modifier: Modifier = Modifier,
) {

    val userStateSaver = listSaver<User, Any>(
            save = {
                listOf(it.name, it.age)
            },
            restore = {
                User(name = it[0] as String, age = it[1] as Int)
            },
    )

    val (user, setUser) = rememberSaveable(stateSaver = userStateSaver) {
        mutableStateOf(User(name = "MinKin", age = 28))
    }

    Column(modifier = modifier) {
        Button(
                onClick = {
                    setUser(User(name = "John", age = 30))
                },
        ) {
            Text(text = "修改User")
        }
        Text(text = "$user")
    }
}