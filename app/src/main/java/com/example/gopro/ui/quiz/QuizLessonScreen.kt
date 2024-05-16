package com.example.gopro.ui.quiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gopro.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun QuizLessonScreen(
    modifier: Modifier = Modifier,
    onQuizClick: ((String) -> Unit)? = null,
    onLessonClick: ((String) -> Unit)? = null,
    @DrawableRes courseImg: Int,
    @StringRes course: Int
){
    val image = painterResource(id = R.drawable.gopro_background2)
    var lessonNum = 1
    val db = Firebase.firestore
    val quizNames = remember { mutableStateOf<List<String>>(emptyList()) }

    Box(modifier){
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Quiz",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 32.sp,
                color = Color.White
            )
            db.collection("quiz")
                .whereEqualTo("course", stringResource(course))
                .get()
                .addOnSuccessListener { documents ->
                    val names = mutableListOf<String>()
                    for (document in documents) {
                        val name = document.getString("name")
                        name?.let {
                            names.add(it)
                        }
                    }
                    // Update the state with the fetched quiz names
                    quizNames.value = names
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
            quizNames.value.forEach { name ->
                QuizLessonList(
                    onClick = onQuizClick,
                    courseImg = courseImg,
                    course = course,
                    event = "Quiz",
                    num = 0,
                    name = name
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Lesson",
                modifier = Modifier
                    .padding(4.dp),
                fontSize = 32.sp,
                color = Color.White
            )
            repeat(3){
                QuizLessonList(
                    onClick = onLessonClick,
                    courseImg = courseImg,
                    course = course,
                    event = "Lesson",
                    num = lessonNum
                )
                lessonNum += 1
            }
        }
    }
}

@Composable
fun QuizLessonList(
    modifier: Modifier = Modifier,
    onClick: ((String) -> Unit)? = null,
    @DrawableRes courseImg: Int,
    @StringRes course: Int,
    event: String,
    num: Int,
    name: String? = null
) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                val nameToPass = name ?: num.toString()
                onClick?.invoke(nameToPass)
            }
    ) {
        Surface(color = Color(0xFFFFFFFF).copy(alpha = 0.8f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.2f))
                Image(
                    painter = painterResource(courseImg),
                    contentDescription = stringResource(course),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = name ?: stringResource(course) + " " + event + " " + num,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.weight(0.2f))
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//private fun QuizEntryScreenPreview() {
//    GoProTheme {
//        QuizLessonScreen(courseImg = R.drawable.trophy, course = R.string.python)
//    }
//}