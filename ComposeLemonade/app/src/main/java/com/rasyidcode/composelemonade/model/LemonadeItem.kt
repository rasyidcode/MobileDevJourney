package com.rasyidcode.composelemonade.model

import androidx.compose.ui.graphics.painter.Painter

data class LemonadeItem(
    val instruction: String,
    val image: Painter,
    val description: String
)
