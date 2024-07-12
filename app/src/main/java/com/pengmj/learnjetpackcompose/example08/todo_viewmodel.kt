package com.pengmj.learnjetpackcompose.example08

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author MinKin
 * @date 2024/7/12
 * @desc
 */

class TodoViewModel : ViewModel() {

    // 如果采用LiveData，则需要把LiveData转化为State对象，所以这里直接使用State对象就好
    var todoItems = mutableStateListOf<TodoItemModel>()
        private set

    // 当前编辑的Todo位置
    private var editingPosition by mutableStateOf(-1)

    // 当前编辑的Todo对象
    val editingTodoItem: TodoItemModel?
        get() = todoItems.getOrNull(editingPosition)

    fun onAddItem(todoItemModel: TodoItemModel) {
        todoItems.add(todoItemModel)
    }

    fun onRemoveItem(todoItemModel: TodoItemModel) {
        todoItems.remove(todoItemModel)
        onEditDone()
    }

    fun onEditDone() {
        editingPosition = -1
    }

    fun onStartEdit(todoItemModel: TodoItemModel) {
        editingPosition = todoItems.indexOf(todoItemModel)
    }

    fun onEditChanged(todoItemModel: TodoItemModel) {
        todoItems[editingPosition] = todoItemModel
    }
}