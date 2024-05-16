package com.example.gopro.ui.quiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gopro.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class QuizQuestion(
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val correctAnswer: String
)

@Composable
fun CountdownTimer(
    timerValue: Long,
    onFinish: () -> Unit
) {
    var time by remember { mutableStateOf(timerValue) }

    LaunchedEffect(Unit) {
        while (time > 0) {
            delay(1000)
            time -= 1000
        }
        onFinish()
    }

    val hours = TimeUnit.MILLISECONDS.toHours(time)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60

    Text(
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        fontSize = 24.sp,
        color = if (time > 0) Color.White else Color.Red
    )
}

@Composable
fun QuizAnswerLayout(quizName: String, quizQuestions: List<QuizQuestion>, onFinish: (Int) -> Unit, modifier: Modifier = Modifier) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var isQuizStarted by remember { mutableStateOf(false) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var timerValue by remember { mutableStateOf(60_000L) }
    var finishAnswer by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf(-1) }
    var quizResult by remember { mutableStateOf(0) }
    var showCorrectAnswer by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .padding(40.dp)
    ) {
        if (!isQuizStarted) {
            Image(
                painter = painterResource(R.drawable.robot_pic),
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isQuizStarted = true
                    isTimerRunning = true
                },
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    ),
                colors = ButtonDefaults.buttonColors(Color(0xFF4A4389))
            ) {
                Text("Start Quiz", color = Color.White)
            }
        } else {
            CountdownTimer(
                timerValue = timerValue,
                onFinish = {
                    isTimerRunning = false
                    finishAnswer = 1
                    onFinish(quizResult)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (currentQuestionIndex < 3) {
                val currentQuestion = quizQuestions[currentQuestionIndex]

                Text(
                    text = currentQuestion.question,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                currentQuestion.apply {
                    val answers = listOf(answer1, answer2, answer3)

                    answers.forEachIndexed { index, answer ->
                        val currentAnswer = answers[index]

                        Surface(
                            color = when {
                                showCorrectAnswer && currentAnswer == correctAnswer -> Color(0xFF00C853)
                                showCorrectAnswer && selectedAnswerIndex == index -> Color(0xFFD50000)
                                else -> Color(0xFFFFFFFF).copy(alpha = 0.8f)
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                                .height(60.dp)
                                .fillMaxWidth()
                                .clickable(enabled = selectedAnswerIndex == -1) {
                                    if (isTimerRunning && selectedAnswerIndex == -1) {
                                        selectedAnswerIndex = index
                                        if (answer == correctAnswer) {
                                            quizResult += 1
                                        }
                                        showCorrectAnswer = true
                                        coroutineScope.launch {
                                            delay(2000)
                                            if (currentQuestionIndex < 2) {
                                                currentQuestionIndex++
                                                isTimerRunning = true
                                                timerValue = 10_000L
                                                selectedAnswerIndex = -1
                                                showCorrectAnswer = false
                                            } else {
                                                val db = Firebase.firestore

                                                if (quizName.isNotEmpty() && quizResult != null) {
                                                    db.collection("result")
                                                        .whereEqualTo("quizName", quizName)
                                                        .get()
                                                        .addOnSuccessListener { querySnapshot ->
                                                            // Delete documents matching the query
                                                            for (document in querySnapshot.documents) {
                                                                document.reference.delete()
                                                                    .addOnSuccessListener {
                                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!")
                                                                        // After successful deletion, add the new result
                                                                        addResult(db, quizName, quizResult)
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Log.w(TAG, "Error deleting document", e)
                                                                    }
                                                            }
                                                        }
                                                        .addOnFailureListener { e ->
                                                            Log.w(TAG, "Error getting documents", e)
                                                        }
                                                } else {
                                                    Log.e(TAG, "One or more input fields are empty. Result data not saved.")
                                                }
                                                isTimerRunning = false
                                                finishAnswer = 1
                                                onFinish(quizResult)
                                            }
                                        }
                                    }
                                }
                        ) {
                            Text(
                                text = answer,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun QuizAnswerScreen(quizName: String, onFinish: (Int) -> Unit) {
    var quizQuestions by remember { mutableStateOf(emptyList<QuizQuestion>()) }

    LaunchedEffect(quizName) {
        val newQuizQuestions = mutableListOf<QuizQuestion>()
        val db = com.google.firebase.Firebase.firestore
        db.collection("quiz")
            .whereEqualTo("name", quizName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val question1 = document.getString("question1")
                    val answer1a = document.getString("answer1a")
                    val answer1b = document.getString("answer1b")
                    val answer1c = document.getString("answer1c")
                    val correctAnswer1 = document.getString("correctAnswer1")
                    val question2 = document.getString("question2")
                    val answer2a = document.getString("answer2a")
                    val answer2b = document.getString("answer2b")
                    val answer2c = document.getString("answer2c")
                    val correctAnswer2 = document.getString("correctAnswer2")
                    val question3 = document.getString("question3")
                    val answer3a = document.getString("answer3a")
                    val answer3b = document.getString("answer3b")
                    val answer3c = document.getString("answer3c")
                    val correctAnswer3 = document.getString("correctAnswer3")
                    if (question1 != null && answer1a != null && answer1b != null && answer1c != null && correctAnswer1 != null &&
                        question2 != null && answer2a != null && answer2b != null && answer2c != null && correctAnswer2 != null &&
                        question3 != null && answer3a != null && answer3b != null && answer3c != null && correctAnswer3 != null) {
                        val quizQuestion1 = QuizQuestion(question1, answer1a, answer1b, answer1c, correctAnswer1)
                        val quizQuestion2 = QuizQuestion(question2, answer2a, answer2b, answer2c, correctAnswer2)
                        val quizQuestion3 = QuizQuestion(question3, answer3a, answer3b, answer3c, correctAnswer3)
                        newQuizQuestions.addAll(listOf(quizQuestion1, quizQuestion2, quizQuestion3))
                    }
                }
                quizQuestions = newQuizQuestions
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.gopro_background2),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            QuizAnswerLayout(quizName, quizQuestions, onFinish)
        }
    }
}

// Function to add result after deletion
private fun addResult(db: FirebaseFirestore, quizName: String, quizResult: Int) {
    val resultData = hashMapOf(
        "user" to "Jessica",
        "quizName" to quizName,
        "quizResult" to quizResult
    )
    db.collection("result")
        .add(resultData)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}