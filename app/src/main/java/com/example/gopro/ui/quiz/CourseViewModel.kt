package com.example.gopro.ui.quiz

import android.content.ContentValues
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.gopro.data.CourseUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CourseViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CourseUiState())
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    fun setCourse(@StringRes course: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                course = course
            )
        }
    }

    fun setCourseImg(@DrawableRes courseImg: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                courseImg = courseImg
            )
        }
    }

    fun setQuizResult(quizResult: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quizResult = quizResult
            )
        }
    }

    fun setQuizName(quizName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                quizName = quizName
            )
        }
    }

    fun setLessonNum(lessonNum: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lessonNum = lessonNum
            )
        }
    }

    fun resetQuiz() {
        _uiState.update { currentState ->
            currentState.copy(
                course = 0,
                courseImg = 0,
                quizResult = 0
            )
        }
    }
}
//
//class QuizEntryViewModel(private val quizRepository: QuizRepository) : ViewModel() {
//
//    /**
//     * Holds current item ui state
//     */
//    val quizUiState = mutableStateOf(QuizUiState())
//
//    fun updateUiState(quizDetails: QuizDetails) {
//        quizUiState.value =
//            QuizUiState(quizDetails = quizDetails, isEntryValid = validateInput(quizDetails))
//    }
//
//    suspend fun saveQuiz() {
//        if (validateInput()) {
//            quizRepository.insertQuiz(quizUiState.value.quizDetails.toQuiz())
//        }
//    }
//
//    private fun validateInput(uiState: QuizDetails = quizUiState.value.quizDetails): Boolean {
//        return with(uiState) {
//            name.isNotBlank() &&
//                    question1.isNotBlank() &&
//                    question2.isNotBlank() &&
//                    question3.isNotBlank() &&
//                    answers1.all { it.isNotBlank() } &&
//                    answers2.all { it.isNotBlank() } &&
//                    answers3.all { it.isNotBlank() } && // Check if each answer is not blank
//                    correctAnswer1.isNotBlank() &&
//                    correctAnswer2.isNotBlank() &&
//                    correctAnswer3.isNotBlank() &&
//                    course.isNotBlank()
//        }
//    }
//}
//
///**
// * Represents Ui State for an Item.
// */
//data class QuizUiState(
//    val quizDetails: QuizDetails = QuizDetails(),
//    val isEntryValid: Boolean = false
//)
//
//data class QuizDetails(
//    val id: Int = 0,
//    val name: String = "",
//    val question1: String = "",
//    val answers1: List<String> = listOf(),
//    val correctAnswer1: String = "",
//    val question2: String = "",
//    val answers2: List<String> = listOf(),
//    val correctAnswer2: String = "",
//    val question3: String = "",
//    val answers3: List<String> = listOf(),
//    val correctAnswer3: String = "",
//    val course: String = ""
//)
//
//fun QuizDetails.toQuiz(): Quiz = Quiz(
//    id = id,
//    name = name,
//    question1 = question1,
//    answers1 = answers1,
//    correctAnswer1 = correctAnswer1,
//    question2 = question2,
//    answers2 = answers2,
//    correctAnswer2 = correctAnswer2,
//    question3 = question3,
//    answers3 = answers3,
//    correctAnswer3 = correctAnswer3,
//    course = course
//)
//
//fun Quiz.toQuizDetails(): QuizDetails = QuizDetails(
//    id = id,
//    name = name,
//    question1 = question1,
//    question2 = question2,
//    question3 = question3,
//    answers1 = answers1,
//    answers2 = answers2,
//    answers3 = answers3,
//    correctAnswer1 = correctAnswer1,
//    correctAnswer2 = correctAnswer2,
//    correctAnswer3 = correctAnswer3,
//    course = course
//)