package com.example.gopro
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gopro.ui.quiz.GoProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoProTheme {
                TeacherCourseViewScreen()
            }
        }
    }
}
