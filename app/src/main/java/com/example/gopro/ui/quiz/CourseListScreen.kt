package com.example.gopro.ui.quiz
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gopro.R
import com.example.gopro.data.Datasource
import com.example.gopro.model.CourseList

@Composable
fun CourseListScreen(
    onCourseClick: (CourseList) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Image(
            painter = painterResource(id = R.drawable.gopro_background2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            CourseList(
                courseList = Datasource().loadCourses(),
                onCourseClick = onCourseClick
            )
        }
    }
}

@Composable
fun CourseList(courseList: List<CourseList>, onCourseClick: (CourseList) -> Unit, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier){
        items(courseList){course ->
            Course(
                onCourseClick = onCourseClick,
                course = course,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun Course(course: CourseList, onCourseClick: (CourseList) -> Unit, modifier: Modifier = Modifier) {
    val viewModel: CourseViewModel = viewModel()

    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .shadow(2.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                onCourseClick(course)
                viewModel.setCourse(course.stringResourceId)
                viewModel.setCourseImg(course.imageResourceId)
            }
    ) {
        Surface(color = Color(0xFFFFFFFF).copy(alpha = 0.8f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Image(
                    painter = painterResource(course.imageResourceId),
                    contentDescription = stringResource(course.stringResourceId),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = stringResource(course.stringResourceId),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}