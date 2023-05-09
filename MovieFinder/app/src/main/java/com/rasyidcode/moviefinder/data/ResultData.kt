package com.rasyidcode.moviefinder.data

sealed class ResultData<out R> {

    data class Success<out T>(val data: T) : ResultData<Success<T>>()

    data class Failed(val errorMessage: String?) : ResultData<Nothing>()

    object Loading : ResultData<Nothing>()

}
