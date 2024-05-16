@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gopro

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gopro.ui.quiz.CourseListScreen
import com.example.gopro.ui.quiz.QuizAnswerScreen
import com.example.gopro.ui.quiz.QuizLessonScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gopro.ui.quiz.CourseViewModel
import com.example.gopro.ui.quiz.QuizResultScreen
import com.google.firebase.firestore.FirebaseFirestore

enum class CourseViewScreen(@StringRes val title: Int){
    CourseListScreen(title = R.string.course_list_screen),
    QuizLessonScreen(title = R.string.quiz_lesson_screen),
    QuizAnswerScreen(title = R.string.quiz_answer_screen),
    QuizResultScreen(title = R.string.quiz_result)
}

@Composable
fun CourseBar(
    modifier: Modifier = Modifier,
    currentScreen: CourseViewScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title), color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF5D55AB)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CourseViewScreen(
    courseViewModel: CourseViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Log.d("CourseViewScreen", "CourseViewScreen composable called")
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CourseViewScreen.valueOf(
        backStackEntry?.destination?.route ?: CourseViewScreen.CourseListScreen.name
    )
//    val quizRepository = remember { QuizRepository() }
//    val quizEntryViewModelFactory = QuizEntryViewModelFactory(quizRepository)
//    val quizEntryViewModel: QuizEntryViewModel = viewModel(factory = quizEntryViewModelFactory)

    Scaffold(
        topBar = {
            CourseBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val courseUiState by courseViewModel.uiState.collectAsState()
        //val quizUiState by quizEntryViewModel.quizUiState

        val coroutineScope = rememberCoroutineScope()

        NavHost(
            navController = navController,
            startDestination = CourseViewScreen.CourseListScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            //Course List Screen
            composable(route = CourseViewScreen.CourseListScreen.name){
                CourseListScreen(
                    onCourseClick = { course ->
                        courseViewModel.setCourse(course.stringResourceId)
                        courseViewModel.setCourseImg(course.imageResourceId)
                        navController.navigate(CourseViewScreen.QuizLessonScreen.name)
                    }
                )
            }

            //Quiz Lesson Screen
            composable(route = CourseViewScreen.QuizLessonScreen.name){
                val context = LocalContext.current
                val course = stringResource(courseUiState.course)

                QuizLessonScreen(
                    onQuizClick = {
                        Log.d(TAG, "Setting quiz name: $it")
                        courseViewModel.setQuizName(it)
                        navController.navigate(CourseViewScreen.QuizAnswerScreen.name)
                    },
                    onLessonClick = { lessonNum ->
                        courseViewModel.setLessonNum(lessonNum)
                    },
                    course = courseUiState.course,
                    courseImg = courseUiState.courseImg
                )

                LaunchedEffect(courseUiState.lessonNum) {
                    val lessonNum = courseUiState.lessonNum
                    if (lessonNum.isNotEmpty()) {
                        openLesson(context, lessonNum, course)
                    }
                }
            }

            //Quiz Answer Screen
            composable(route = CourseViewScreen.QuizAnswerScreen.name){
                QuizAnswerScreen(
                    quizName = courseUiState.quizName,
                    onFinish = {
                        courseViewModel.setQuizResult(it)
                        navController.navigate(CourseViewScreen.QuizResultScreen.name)
                    }
                )
            }

            //Quiz Result Screen
            composable(route = CourseViewScreen.QuizResultScreen.name){
                QuizResultScreen(
                    quizResult = courseUiState.quizResult,
                    onQuitButtonClicked = {
                        cancelResultAndNavigateToStart(courseViewModel, navController)
                    },
                    onRetryButtonClicked = {
                        navController.navigate(CourseViewScreen.QuizAnswerScreen.name)
                    }
                )
            }
        }
    }
}

private fun openLesson(context: Context, lessonNum: String, course: String) {
    val db = FirebaseFirestore.getInstance()
    val lessonCollection = db.collection("lessons")
    lessonCollection
        .whereEqualTo("course", course)
        .whereEqualTo("lessonNum", lessonNum)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val youtubeLink = document.getString("youtubeLink")
                if (youtubeLink != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                    context.startActivity(intent)
                    return@addOnSuccessListener
                }
            }
            Log.e(TAG, "No YouTube link found for lessonNum: $lessonNum and course: $course")
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, "Error getting documents: ", exception)
        }
}


private fun cancelResultAndNavigateToStart(
    viewModel: CourseViewModel,
    navController: NavHostController){
    viewModel.resetQuiz()
    navController.popBackStack(CourseViewScreen.CourseListScreen.name, inclusive = false)
}

private fun saveNewQuiz(
    viewModel: CourseViewModel,
    navController: NavHostController){
    navController.popBackStack(CourseViewScreen.CourseListScreen.name, inclusive = false)
}