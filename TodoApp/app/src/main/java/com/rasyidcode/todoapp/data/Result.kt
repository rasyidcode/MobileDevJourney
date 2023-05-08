package com.rasyidcode.todoapp.data

import java.lang.Exception

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

}


/**
 * Source: https://stackoverflow.com/questions/44298702/what-is-out-keyword-in-kotlin
 */
class Test<out T> {
    private val myList = mutableListOf<T>()
    fun last(): T = myList.last()
//        fun add(c: T) = myList.add(c) // error: the 'out' can't be consumer
}

class Test2<in T> {
    private val myList = mutableListOf<T>()
    fun add(c: T) = myList.add(c)
//    fun first() : T = myList.first() // error: the 'in' can't be producer
}