package com.example.gopro.ui.quiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.StringRes
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.gopro.R
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun QuizEntryScreen(
    @StringRes course: Int,
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
            QuizInputForm(
                course = course,
                onSaveClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QuizInputForm(
    @StringRes course: Int,
    onSaveClick: ()->Unit,
    modifier: Modifier = Modifier
) {
    val defaultCourseValue = stringResource(course)
    val courseState = remember { mutableStateOf(defaultCourseValue) }
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val question1State = remember { mutableStateOf(TextFieldValue()) }
    val question2State = remember { mutableStateOf(TextFieldValue()) }
    val question3State = remember { mutableStateOf(TextFieldValue()) }
    val answer1aState = remember { mutableStateOf(TextFieldValue()) }
    val answer1bState = remember { mutableStateOf(TextFieldValue()) }
    val answer1cState = remember { mutableStateOf(TextFieldValue()) }
    val answer2aState = remember { mutableStateOf(TextFieldValue()) }
    val answer2bState = remember { mutableStateOf(TextFieldValue()) }
    val answer2cState = remember { mutableStateOf(TextFieldValue()) }
    val answer3aState = remember { mutableStateOf(TextFieldValue()) }
    val answer3bState = remember { mutableStateOf(TextFieldValue()) }
    val answer3cState = remember { mutableStateOf(TextFieldValue()) }
    val correctAnswer1State = remember { mutableStateOf(TextFieldValue()) }
    val correctAnswer2State = remember { mutableStateOf(TextFieldValue()) }
    val correctAnswer3State = remember { mutableStateOf(TextFieldValue()) }

    Box(modifier) {
        Column(modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState())) {
            OutlinedTextField(
                value = courseState.value,
                onValueChange = { courseState.value = it },
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
                singleLine = true
            )
            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
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
                placeholder = { Text("Cannot use repeat quiz name") }
            )
            OutlinedTextField(
                value = question1State.value,
                onValueChange = { question1State.value = it },
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
                value = answer1aState.value,
                onValueChange = { answer1aState.value = it },
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
                value = answer1bState.value,
                onValueChange = { answer1bState.value = it },
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
                value = answer1cState.value,
                onValueChange = { answer1cState.value = it },
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
                value = correctAnswer1State.value,
                onValueChange = { correctAnswer1State.value = it },
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
                placeholder = { Text("Enter the same word with the correct option") }
            )
            OutlinedTextField(
                value = question2State.value,
                onValueChange = { question2State.value = it },
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
                value = answer2aState.value,
                onValueChange = { answer2aState.value = it },
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
                value = answer2bState.value,
                onValueChange = { answer2bState.value = it },
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
                value = answer2cState.value,
                onValueChange = { answer2cState.value = it },
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
                value = correctAnswer2State.value,
                onValueChange = { correctAnswer2State.value = it },
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
                placeholder = { Text("Enter the same word with the correct option") }
            )
            OutlinedTextField(
                value = question3State.value,
                onValueChange = { question3State.value = it },
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
                value = answer3aState.value,
                onValueChange = { answer3aState.value = it },
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
                value = answer3bState.value,
                onValueChange = { answer3bState.value = it },
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
                value = answer3cState.value,
                onValueChange = { answer3cState.value = it },
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
                value = correctAnswer3State.value,
                onValueChange = { correctAnswer3State.value = it },
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
                placeholder = { Text("Enter the same word with the correct option") }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    saveQuizData(
                        courseState.value,
                        nameState.value.text,
                        question1State.value.text,
                        answer1aState.value.text,
                        answer1bState.value.text,
                        answer1cState.value.text,
                        correctAnswer1State.value.text,
                        question2State.value.text,
                        answer2aState.value.text,
                        answer2bState.value.text,
                        answer2cState.value.text,
                        correctAnswer2State.value.text,
                        question3State.value.text,
                        answer3aState.value.text,
                        answer3bState.value.text,
                        answer3cState.value.text,
                        correctAnswer3State.value.text
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

fun saveQuizData(
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
    quizCollection.whereEqualTo("name", name).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                if (course.isNotEmpty() && name.isNotEmpty() && q1.isNotEmpty() && a1a.isNotEmpty() &&
                    a1b.isNotEmpty() && a1c.isNotEmpty() && ca1.isNotEmpty() && q2.isNotEmpty() &&
                    a2a.isNotEmpty() && a2b.isNotEmpty() && a2c.isNotEmpty() && ca2.isNotEmpty() &&
                    q3.isNotEmpty() && a3a.isNotEmpty() && a3b.isNotEmpty() && a3c.isNotEmpty() && ca3.isNotEmpty()
                ) {
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
                    quizCollection.add(quizData)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                } else {
                    Log.e(TAG, "One or more input fields are empty. Quiz data not saved.")
                }
            } else {
                Log.e(TAG, "Quiz name already exists. Please choose a different name.")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, "Error getting documents: ", exception)
        }
}