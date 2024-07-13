package com.pengmj.learnjetpackcompose.example17

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pengmj.learnjetpackcompose.R

/**
 * @author MinKin
 * @date 2024/7/13
 * @desc
 */

@Composable
fun Example17ProgramEntry(
    modifier: Modifier = Modifier,
) {

    val (loading, setLoading) = remember { mutableStateOf(true) }

    Box(
            modifier = modifier.fillMaxSize(),
    ) {

        UserAreas()

        if (loading) {
            UserAreasLoadingSkeleton()
        }

        Button(
                onClick = {
                    setLoading(!loading)
                },
                modifier = Modifier.align(Alignment.Center),
        ) {
            Text(text = if (loading) "停止加载" else "重新加载")
        }
    }
}

@Composable
private fun UserAreas() {
    Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(all = 20.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp,
            ),
    ) {
        Row(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "头像",
                    modifier = Modifier
                            .size(50.dp)
                            .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape,
                            )
                            .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                    text = "MinKin",
                    style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                    ),
            )
        }
    }
}

@Composable
private fun UserAreasLoadingSkeleton() {

    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")

    val alpha by infiniteTransition.animateFloat(
            initialValue = 0.0f,
            targetValue = 1.0f,
            animationSpec = InfiniteRepeatableSpec(
                    animation = keyframes {
                        durationMillis = 1000
                        // 执行到500ms时，动画alpha值为1.0f
                        1.0f at 500
                    },
                    repeatMode = RepeatMode.Reverse,
            ),
            label = "FloatAnimation",
    )

    Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(all = 20.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp,
            ),
    ) {
        Row(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                    modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color = Color.LightGray.copy(alpha = alpha)),
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(color = Color.LightGray.copy(alpha = alpha)),
            )
        }
    }
}