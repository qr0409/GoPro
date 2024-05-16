@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gopro

import LessonEditScreen
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gopro.ui.quiz.CourseViewModel
import com.example.gopro.ui.quiz.QuizEntryScreen
import com.example.gopro.ui.quiz.QuizUpdateScreen
import com.example.gopro.ui.quiz.TeacherQuizLessonScreen

enum class TeacherCourseViewScreen(@StringRes val title: Int){
    CourseListScreen(title = R.string.course_list_screen),
    QuizLessonScreen(title = R.string.quiz_lesson_screen),
    QuizUpdateScreen(title = R.string.quiz_update_screen),
    QuizEntryScreen(title = R.string.quiz_entry_screen),
    LessonEditScreen(title = R.string.lesson_edit_screen)
}

@Composable
fun TeacherCourseBar(
    modifier: Modifier = Modifier,
    currentScreen: TeacherCourseViewScreen,
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
fun TeacherCourseViewScreen(
    courseViewModel: CourseViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Log.d("TeacherCourseViewScreen", "TeacherCourseViewScreen composable called")
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TeacherCourseViewScreen.valueOf(
        backStackEntry?.destination?.route ?: TeacherCourseViewScreen.CourseListScreen.name
    )

    Scaffold(
        topBar = {
            TeacherCourseBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val courseUiState by courseViewModel.uiState.collectAsState()

        val coroutineScope = rememberCoroutineScope()

        NavHost(
            navController = navController,
            startDestination = TeacherCourseViewScreen.CourseListScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            //Course List Screen
            composable(route = TeacherCourseViewScreen.CourseListScreen.name){
                CourseListScreen(
                    onCourseClick = { course ->
                        courseViewModel.setCourse(course.stringResourceId)
                        courseViewModel.setCourseImg(course.imageResourceId)
                        navController.navigate(TeacherCourseViewScreen.QuizLessonScreen.name)
                    }
                )
            }

            //Quiz Lesson Screen
            composable(route = TeacherCourseViewScreen.QuizLessonScreen.name){
                val context = LocalContext.current
                TeacherQuizLessonScreen(
                    onQuizClick = {
                        courseViewModel.setQuizName(it)
                        navController.navigate(TeacherCourseViewScreen.QuizUpdateScreen.name)
                    },
                    onLessonClick = {
                        courseViewModel.setLessonNum(it)
                        navController.navigate(TeacherCourseViewScreen.LessonEditScreen.name)
                    },
                    onQuizEntryClick = { navController.navigate(TeacherCourseViewScreen.QuizEntryScreen.name) },
                    course = courseUiState.course,
                    courseImg = courseUiState.courseImg
                )
            }

            //Lesson Edit Screen
            composable(route = TeacherCourseViewScreen.LessonEditScreen.name){
                LessonEditScreen(
                    course = courseUiState.course,
                    lessonNum = courseUiState.lessonNum,
                    onSaveLessonClick = {
                        navController.navigate(TeacherCourseViewScreen.QuizLessonScreen.name)
                    }
                )
            }

            //Quiz Entry Screen
            composable(route = TeacherCourseViewScreen.QuizEntryScreen.name){
                QuizEntryScreen(
                    course = courseUiState.course,
                    onSaveClick = {
                        navController.navigate(TeacherCourseViewScreen.QuizLessonScreen.name)
                    }
                )
            }

            //Quiz Answer Screen
            composable(route = TeacherCourseViewScreen.QuizUpdateScreen.name){
                QuizUpdateScreen(
                    quizName = courseUiState.quizName,
                    onSaveClick = {
                        navController.navigate(TeacherCourseViewScreen.QuizLessonScreen.name)
                    }
                )
            }
        }
    }
}