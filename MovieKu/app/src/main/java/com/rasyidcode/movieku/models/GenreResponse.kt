package com.rasyidcode.movieku.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<Genre?>? = null
) : Parcelable