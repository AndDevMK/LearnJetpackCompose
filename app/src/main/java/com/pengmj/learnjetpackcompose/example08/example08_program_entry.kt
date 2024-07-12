package com.pengmj.learnjetpackcompose.example08

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * @author MinKin
 * @date 2024/7/11
 * @desc
 */

@Composable
fun Example08ProgramEntry(
    modifier: Modifier = Modifier,
    items: List<TodoItemModel>,
    onAddItem: (TodoItemModel) -> Unit,
    onRemoveItem: (TodoItemModel) -> Unit,
    editingTodoItem: TodoItemModel?,
    onStartEdit: (TodoItemModel) -> Unit,
    onEditChanged: (TodoItemModel) -> Unit,
    onEditDone: () -> Unit,
) {

    Column(
            modifier = modifier,
    ) {

        // 当前不是编辑状态？
        val isNotEditing = editingTodoItem == null

        TodoInputWrapperBackground(
                modifier = Modifier,
                showElevation = isNotEditing,
        ) {
            if (isNotEditing) {
                TodoInputWrapper(
                        onSubmitted = onAddItem,
                )
            }
            else {
                Text(
                        text = "Editing",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                )
            }

        }

        TodoList(
                modifier = Modifier,
                items = items,
                onAddItem = onAddItem,
                onRemoveItem = onRemoveItem,
                editingTodoItem = editingTodoItem,
                onStartEdit = onStartEdit,
                onEditChanged = onEditChanged,
                onEditDone = onEditDone,
        )
    }
}

/**
 * Todo编辑区域背景
 */
@Composable
private fun TodoInputWrapperBackground(
    modifier: Modifier = Modifier,
    showElevation: Boolean,
    content: @Composable RowScope.() -> Unit,
) {

    // 阴影大小变化的动画
    val animatedShadowElevation by animateDpAsState(
            targetValue = if (showElevation) 0.5.dp else 0.dp,
            animationSpec = TweenSpec(durationMillis = 300, easing = EaseInOut),
            label = "DpAnimation",
    )

    Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
            shadowElevation = animatedShadowElevation,
    ) {
        Row(
                // slot内容大小变化的动画
                modifier = modifier.animateContentSize(
                        animationSpec = TweenSpec(
                                durationMillis = 300, easing = EaseInOut
                        ),
                ),
                content = content,
        )
    }
}

/**
 * Todo编辑区域
 */
@Composable
private fun TodoInputWrapper(
    onSubmitted: (TodoItemModel) -> Unit
) {

    val (text, setText) = remember {
        mutableStateOf("")
    }

    val (todoIcon, setTodoIcon) = remember {
        mutableStateOf(TodoIcon.Default)
    }

    val controller = LocalSoftwareKeyboardController.current

    val doSubmit = {
        // 隐藏软键盘
        controller?.hide()
        // 提交todo对象
        onSubmitted(TodoItemModel(text = text, icon = todoIcon))
        // 提交后reset
        setTodoIcon(TodoIcon.Default)
        setText("")
    }

    TodoInputWrapperAddState(
            text = text,
            onTextChanged = setText,
            todoIcon = todoIcon,
            onTodoIconChanged = setTodoIcon,
            onSubmitted = doSubmit,
    )
}

/**
 * 添加Todo状态的输入区域
 */
@Composable
private fun TodoInputWrapperAddState(
    text: String,
    onTextChanged: (String) -> Unit,
    todoIcon: TodoIcon,
    onTodoIconChanged: (TodoIcon) -> Unit,
    onSubmitted: () -> Unit,
) {
    TodoInputWrapperState(
            modifier = Modifier,
            text = text,
            onTextChanged = onTextChanged,
            todoIcon = todoIcon,
            onTodoIconChanged = onTodoIconChanged,
            onSubmitted = onSubmitted,
    ) {
        Button(
                onClick = onSubmitted,
                enabled = text.isNotBlank(),
        ) {
            Text(text = "Add")
        }
    }
}

/**
 * 输入区域状态统一封装(添加Todo状态 + 修改Todo状态)
 */
@Composable
private fun TodoInputWrapperState(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    todoIcon: TodoIcon,
    onTodoIconChanged: (TodoIcon) -> Unit,
    onSubmitted: () -> Unit,
    buttonSlot: @Composable () -> Unit,
) {
    Column(
            modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Row {
            TodoInputWrapperEditor(
                    modifier = Modifier.weight(1.0f),
                    value = text,
                    onValueChanged = onTextChanged,
                    onDone = onSubmitted,
            )

            Box(
                    modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 20.dp),
            ) {
                buttonSlot()
            }
        }

        TodoInputWrapperIconRows(
                modifier = Modifier,
                value = todoIcon,
                onValueChanged = onTodoIconChanged,
                visible = text.isNotBlank(),
        )
    }
}

/**
 * 修改Todo状态的输入区域
 */
@Composable
private fun TodoInputWrapperUpdateState(
    todoItemModel: TodoItemModel,
    onUpdateChanged: (TodoItemModel) -> Unit,
    onDone: () -> Unit,
    onRemove: () -> Unit,
) {
    TodoInputWrapperState(
            modifier = Modifier,
            text = todoItemModel.text,
            onTextChanged = {
                onUpdateChanged(todoItemModel.copy(text = it))
            },
            todoIcon = todoItemModel.icon,
            onTodoIconChanged = {
                onUpdateChanged(todoItemModel.copy(icon = it))
            },
            onSubmitted = onDone,
    ) {

        Row {
            Button(
                    onClick = onDone,
            ) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                    onClick = onRemove,
            ) {
                Text(text = "Remove")
            }
        }
    }
}

/**
 * 输入区域input框
 */
@Composable
private fun TodoInputWrapperEditor(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    onDone: () -> Unit,
) {
    val controller = LocalSoftwareKeyboardController.current

    TextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = modifier,
            maxLines = 1,
            colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                    onDone = {
                        // 隐藏软键盘
                        controller?.hide()
                        onDone()
                    },
            ),
    )
}

/**
 * 输入区域底部Icon列表
 */
@Composable
private fun TodoInputWrapperIconRows(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    value: TodoIcon,
    onValueChanged: (TodoIcon) -> Unit
) {
    val enter = remember {
        fadeIn(animationSpec = TweenSpec(durationMillis = 300, easing = EaseIn))
    }

    val exit = remember {
        fadeOut(animationSpec = TweenSpec(durationMillis = 300, easing = EaseOut))
    }

    AnimatedVisibility(
            modifier = modifier.padding(top = 10.dp),
            visible = visible,
            enter = enter,
            exit = exit,
    ) {

        Row {
            for (todoIcon in TodoIcon.entries) {

                val isSelected = todoIcon == value

                val tint =
                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

                TextButton(
                        onClick = {
                            onValueChanged(todoIcon)
                        },
                        contentPadding = PaddingValues(all = 0.dp),
                        shape = CircleShape,
                        modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .size(50.dp),
                ) {
                    Column {
                        Icon(
                                imageVector = todoIcon.imageVector,
                                contentDescription = todoIcon.contentDescription,
                                tint = tint,
                        )

                        if (isSelected) {
                            Box(
                                    modifier = Modifier
                                            .padding(top = 5.dp)
                                            .width(todoIcon.imageVector.defaultWidth)
                                            .height(1.dp)
                                            .background(color = tint),
                            )
                        }
                        else {
                            // top为6dp（padding 5dp + height 1dp），用于指示器占位
                            Spacer(
                                    modifier = Modifier.padding(top = 6.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * todo列表
 */
@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    items: List<TodoItemModel>,
    onAddItem: (TodoItemModel) -> Unit,
    onRemoveItem: (TodoItemModel) -> Unit,
    editingTodoItem: TodoItemModel?,
    onStartEdit: (TodoItemModel) -> Unit,
    onEditChanged: (TodoItemModel) -> Unit,
    onEditDone: () -> Unit,
) {
    Column(
            modifier = modifier,
    ) {
        LazyColumn(
                modifier = Modifier.weight(1.0f),
        ) {
            items(items) {
                if (it.id == editingTodoItem?.id) {
                    TodoInputWrapperUpdateState(
                            todoItemModel = it,
                            onUpdateChanged = onEditChanged,
                            onDone = onEditDone,
                            onRemove = {
                                onRemoveItem(it)
                            },
                    )
                }
                else {
                    TodoItem(
                            todoItemModel = it,
                            onItemClick = onStartEdit,
                    )
                }

            }
        }

        Button(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 20.dp),
                onClick = { onAddItem(generateTodoItem()) },
        ) {
            Text(text = "添加一条随机Todo")
        }
    }
}

/**
 * 一个todo item
 */
@Composable
private fun TodoItem(
    modifier: Modifier = Modifier,
    todoItemModel: TodoItemModel,
    onItemClick: (TodoItemModel) -> Unit,
) {
    Row(
            modifier = modifier
                    .clickable {
                        onItemClick(todoItemModel)
                    }
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = todoItemModel.text)

        // 随机alpha，超过一屏滚动有bug
        val alpha = remember(todoItemModel.id) {
            Random.nextFloat().coerceIn(0.3f, 1.0f)
        }

        Icon(
                imageVector = todoItemModel.icon.imageVector,
                contentDescription = todoItemModel.icon.contentDescription,
                tint = LocalContentColor.current.copy(alpha = alpha),
        )
    }
}
