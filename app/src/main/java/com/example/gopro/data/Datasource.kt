package com.example.gopro.data
import com.example.gopro.R
import com.example.gopro.model.CourseList

class Datasource() {
    fun loadCourses(): List<CourseList> {
        return listOf<CourseList>(
            CourseList(R.string.python, R.drawable.python_logo),
            CourseList(R.string.java, R.drawable.java_logo),
            CourseList(R.string.c, R.drawable.c_logo),
            CourseList(R.string.html, R.drawable.html_logo),
            CourseList(R.string.javascript, R.drawable.javascript_logo),
            CourseList(R.string.sql, R.drawable.sql_logo),
            CourseList(R.string.css, R.drawable.css_logo))
    }
}