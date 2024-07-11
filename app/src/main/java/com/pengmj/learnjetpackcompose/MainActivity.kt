package com.pengmj.learnjetpackcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pengmj.learnjetpackcompose.example02.Example02ProgramEntry
import com.pengmj.learnjetpackcompose.ui.theme.LearnJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Example02ProgramEntry(modifier = Modifier.padding(innerPadding))
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
        Example02ProgramEntry()
    }
}