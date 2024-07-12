package com.pengmj.learnjetpackcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pengmj.learnjetpackcompose.example08.Example08ProgramEntry
import com.pengmj.learnjetpackcompose.example08.TodoViewModel
import com.pengmj.learnjetpackcompose.ui.theme.LearnJetpackComposeTheme

class MainActivity : ComponentActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Example08ProgramEntry(
                            modifier = Modifier.padding(innerPadding),
                            items = todoViewModel.todoItems,
                            onAddItem = todoViewModel::onAddItem,
                            onRemoveItem = todoViewModel::onRemoveItem,
                            editingTodoItem = todoViewModel.editingTodoItem,
                            onStartEdit = todoViewModel::onStartEdit,
                            onEditChanged = todoViewModel::onEditChanged,
                            onEditDone = todoViewModel::onEditDone,
                    )
                }
            }
        }
    }
}


@Preview(
        showBackground = true,
        showSystemUi = true,
        uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
        backgroundColor = 0xFFF8F8F8,
        device = "id:pixel_6",
)
@Composable
fun GreetingPreview() {
    LearnJetpackComposeTheme {

    }
}