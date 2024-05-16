package com.example.gopro.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CourseList(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
