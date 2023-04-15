package com.rasyidcode.trainingsports.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rasyidcode.trainingsports.R


data class Sport(
    val id: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val subtitleResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    @DrawableRes val sportsImageBanner: Int,
    @StringRes val newsDetail: Int = R.string.sports_news_detail_text
)
