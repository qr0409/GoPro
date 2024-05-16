package com.example.gopro.ui.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gopro.R

private const val TOTAL_QUESTIONS = 3

@Composable
fun QuizResultScreen(
    quizResult: Int,
    onQuitButtonClicked: () -> Unit,
    onRetryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resultString = "$quizResult/$TOTAL_QUESTIONS"
    var img = R.drawable.trophy
    val imgNum = (0..4).random()
    when (imgNum) {
        0 -> {
            img = R.drawable.trophy
        }
        1 -> {
            img = R.drawable.robot_greatjobs
        }
        2 -> {
            img = R.drawable.robot_goodjob
        }
        3 -> {
            img = R.drawable.robot_keepitup
        }
        4 -> {
            img = R.drawable.robot_nicework
        }
    }
    Box(modifier) {
        Image(
            painter = painterResource(id = R.drawable.gopro_background3),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .width(300.dp)
                        .height(350.dp),
                    color = Color(0xFFFFFFFF).copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Image(
                            painter = painterResource(img),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(text = "You've scored", fontWeight = FontWeight.Bold)
                        Text(text = resultString)
                        Spacer(modifier = Modifier.height(40.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                modifier = Modifier.weight(1f),
                                onClick = onRetryButtonClicked
                            ) {
                                Text(stringResource(R.string.retry))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = onQuitButtonClicked
                            ) {
                                Text(stringResource(R.string.quit))
                            }
                        }
                    }
                }
            }
        }
    }
}