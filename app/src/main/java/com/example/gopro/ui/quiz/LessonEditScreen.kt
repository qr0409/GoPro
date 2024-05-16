import android.content.ContentValues
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gopro.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun LessonEditScreen(
    @StringRes course: Int,
    lessonNum: String,
    onSaveLessonClick: () -> Unit
) {
    var youtubeLinkState by remember { mutableStateOf("") }
    val courseName = stringResource(course)
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("lessons")
            .whereEqualTo("course", courseName)
            .whereEqualTo("lessonNum", lessonNum)
            .limit(1)
        val snapshot = query.get().await()
        for (document in snapshot) {
           youtubeLinkState = document.getString("youtubeLink") ?: ""
        }
    }

    Box() {
        Image(
            painter = painterResource(R.drawable.gopro_background2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = youtubeLinkState,
                onValueChange = { youtubeLinkState = it },
                label = { Text(text = "Youtube Link") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 16.dp)
            )
            Button(
                onClick = {
                    saveLessonData(courseName, lessonNum, youtubeLinkState)
                    onSaveLessonClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 16.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    ),
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LessonEditScreenPreview() {
    LessonEditScreen(R.string.python, "2", onSaveLessonClick = {})
}

private fun saveLessonData(courseName: String, lessonNum: String, youtubeLink: String) {
    val db = FirebaseFirestore.getInstance()
    val lessonCollection = db.collection("lessons")
    lessonCollection.whereEqualTo("course", courseName).get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                if (courseName.isNotEmpty() && lessonNum.isNotEmpty() && youtubeLink.isNotEmpty()) {
                    val lessonData = hashMapOf(
                        "course" to courseName,
                        "lessonNum" to lessonNum,
                        "youtubeLink" to youtubeLink
                    )
                    lessonCollection.add(lessonData)
                        .addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error adding document", e)
                        }
                } else {
                    Log.e(ContentValues.TAG, "One or more input fields are empty. Quiz data not saved.")
                }
            } else {
                Log.e(ContentValues.TAG, "Quiz name already exists. Please choose a different name.")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(ContentValues.TAG, "Error getting documents: ", exception)
        }
}