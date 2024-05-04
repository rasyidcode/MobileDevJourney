package com.rasyidcode.course.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val nameResId: Int,
    val numberOfCourses: Int,
    @DrawableRes val imageResId: Int,
)
