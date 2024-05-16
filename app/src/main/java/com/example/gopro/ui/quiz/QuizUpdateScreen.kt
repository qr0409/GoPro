package com.example.gopro.ui.quiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gopro.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun QuizUpdateScreen(
    quizName: String,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(){
        Image(
            painter = painterResource(id = R.drawable.gopro_background2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            QuizUpdateForm(
                quizName = quizName,
                onSaveClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QuizUpdateForm(
    quizName: String,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
) {
    var courseState by remember { mutableStateOf("") }
    var question1State by remember { mutableStateOf("") }
    var question2State by remember { mutableStateOf("") }
    var question3State by remember { mutableStateOf("") }
    var answer1aState by remember { mutableStateOf("") }
    var answer1bState by remember { mutableStateOf("") }
    var answer1cState by remember { mutableStateOf("") }
    var answer2aState by remember { mutableStateOf("") }
    var answer2bState by remember { mutableStateOf("") }
    var answer2cState by remember { mutableStateOf("") }
    var answer3aState by remember { mutableStateOf("") }
    var answer3bState by remember { mutableStateOf("") }
    var answer3cState by remember { mutableStateOf("") }
    var correctAnswer1State by remember { mutableStateOf("") }
    var correctAnswer2State by remember { mutableStateOf("") }
    var correctAnswer3State by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("quiz").whereEqualTo("name", quizName).limit(1)
        val snapshot = query.get().await()
        for (document in snapshot) {
            courseState = document.getString("course") ?: ""
            question1State = document.getString("question1") ?: ""
            answer1aState = document.getString("answer1a") ?: ""
            answer1bState = document.getString("answer1b") ?: ""
            answer1cState = document.getString("answer1c") ?: ""
            correctAnswer1State = document.getString("correctAnswer1") ?: ""
            question2State = document.getString("question2") ?: ""
            answer2aState = document.getString("answer2a") ?: ""
            answer2bState = document.getString("answer2b") ?: ""
            answer2cState = document.getString("answer2c") ?: ""
            correctAnswer2State = document.getString("correctAnswer2") ?: ""
            question3State = document.getString("question3") ?: ""
            answer3aState = document.getString("answer3a") ?: ""
            answer3bState = document.getString("answer3b") ?: ""
            answer3cState = document.getString("answer3c") ?: ""
            correctAnswer3State = document.getString("correctAnswer3") ?: ""
        }
    }
    Box(modifier) {
        Column(modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState())) {
            OutlinedTextField(
                value = courseState,
                onValueChange = { courseState = it },
                label = { Text("Course") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = false
            )
            OutlinedTextField(
                value = quizName,
                onValueChange = { quizName },
                label = { Text(stringResource(R.string.quiz_name)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = false
            )
            OutlinedTextField(
                value = question1State,
                onValueChange = { question1State = it },
                label = { Text("Question 1") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer1aState,
                onValueChange = { answer1aState = it },
                label = { Text("Answer A") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer1bState,
                onValueChange = { answer1bState = it },
                label = { Text("Answer B") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer1cState,
                onValueChange = { answer1cState = it },
                label = { Text("Answer C") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = correctAnswer1State,
                onValueChange = { correctAnswer1State = it },
                label = { Text("Correct Answer") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedPlaceholderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Please enter the same word with the correct option") }
            )
            OutlinedTextField(
                value = question2State,
                onValueChange = { question2State = it },
                label = { Text("Question 2") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer2aState,
                onValueChange = { answer2aState = it },
                label = { Text("Answer A") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer2bState,
                onValueChange = { answer2bState = it },
                label = { Text("Answer B") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer2cState,
                onValueChange = { answer2cState = it },
                label = { Text("Answer C") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = correctAnswer2State,
                onValueChange = { correctAnswer2State = it },
                label = { Text("Correct Answer") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedPlaceholderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Please enter the same word with the correct option") }
            )
            OutlinedTextField(
                value = question3State,
                onValueChange = { question3State = it },
                label = { Text("Question 3") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer3aState,
                onValueChange = { answer3aState = it },
                label = { Text("Answer A") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer3bState,
                onValueChange = { answer3bState = it },
                label = { Text("Answer B") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = answer3cState,
                onValueChange = { answer3cState = it },
                label = { Text("Answer C") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = correctAnswer3State,
                onValueChange = { correctAnswer3State = it },
                label = { Text("Correct Answer") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedPlaceholderColor = Color.LightGray
                ),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Please enter the same word with the correct option") }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    updateQuizData(
                        courseState,
                        quizName,
                        question1State,
                        answer1aState,
                        answer1bState,
                        answer1cState,
                        correctAnswer1State,
                        question2State,
                        answer2aState,
                        answer2bState,
                        answer2cState,
                        correctAnswer2State,
                        question3State,
                        answer3aState,
                        answer3bState,
                        answer3cState,
                        correctAnswer3State
                    )
                    onSaveClick()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF4A4389))
            ) {
                Text(text = "Save", color = Color.White)
            }
        }
    }
}

fun updateQuizData(
    course: String,
    name: String,
    q1: String,
    a1a: String,
    a1b: String,
    a1c: String,
    ca1: String,
    q2: String,
    a2a: String,
    a2b: String,
    a2c: String,
    ca2: String,
    q3: String,
    a3a: String,
    a3b: String,
    a3c: String,
    ca3: String
) {
    val db = FirebaseFirestore.getInstance()
    val quizCollection = db.collection("quiz")

    if (course.isNotEmpty() && name.isNotEmpty() && q1.isNotEmpty() && a1a.isNotEmpty() &&
        a1b.isNotEmpty() && a1c.isNotEmpty() && ca1.isNotEmpty() && q2.isNotEmpty() &&
        a2a.isNotEmpty() && a2b.isNotEmpty() && a2c.isNotEmpty() && ca2.isNotEmpty() &&
        q3.isNotEmpty() && a3a.isNotEmpty() && a3b.isNotEmpty() && a3c.isNotEmpty() && ca3.isNotEmpty()
    ) {
        quizCollection.whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val quizData = hashMapOf(
                        "course" to course,
                        "name" to name,
                        "question1" to q1,
                        "answer1a" to a1a,
                        "answer1b" to a1b,
                        "answer1c" to a1c,
                        "correctAnswer1" to ca1,

                        "question2" to q2,
                        "answer2a" to a2a,
                        "answer2b" to a2b,
                        "answer2c" to a2c,
                        "correctAnswer2" to ca2,

                        "question3" to q3,
                        "answer3a" to a3a,
                        "answer3b" to a3b,
                        "answer3c" to a3c,
                        "correctAnswer3" to ca3
                    )
                    quizCollection.document(document.id)
                        .set(quizData)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error updating document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    } else {
        Log.e(TAG, "One or more input fields are empty. Quiz data not updated.")
    }
}