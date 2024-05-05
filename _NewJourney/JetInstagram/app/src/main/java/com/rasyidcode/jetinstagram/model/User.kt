package com.rasyidcode.jetinstagram.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rasyidcode.jetinstagram.R

data class User(
    @StringRes val nameRes: Int,
    @StringRes val usernameRes: Int,
    @DrawableRes val imageRes: Int
)