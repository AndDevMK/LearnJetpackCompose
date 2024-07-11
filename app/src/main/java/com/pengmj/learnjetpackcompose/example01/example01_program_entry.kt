package com.pengmj.learnjetpackcompose.example01

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pengmj.learnjetpackcompose.R

/**
 * @author MinKin
 * @date 2024/7/10
 * @desc
 */

@Composable
fun Example01ProgramEntry(
    modifier: Modifier = Modifier,
) {
    Conversation(
            modifier = modifier,
            messages = MessageService.messages,
    )
}

@Composable
private fun Conversation(
    modifier: Modifier = Modifier,
    messages: List<MessageModel>,
) {
    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            MessageCard(messageModel = message)
        }
    }
}

@Composable
private fun MessageCard(messageModel: MessageModel) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "头像",
                modifier = Modifier
                        // Set image size to 40 dp
                        .size(size = 40.dp)
                        // Clip image to be shaped as a circle
                        .clip(shape = CircleShape)
                        .border(
                                width = 1.5.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                        ),
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface, label = "",
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                    text = messageModel.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall,
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    // surfaceColor color will be changing gradually from primary to surface
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier
                            .animateContentSize()
                            .padding(1.dp),
            ) {
                Text(
                        text = messageModel.body,
                        modifier = Modifier.padding(all = 4.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}