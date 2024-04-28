package com.rasyidcode.funfacts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {

    private val _name = mutableStateOf("")
    val name: String
        get() = _name.value

    private val _animal = mutableStateOf("")
    val animal: String
        get() = _animal.value

    fun setName(name: String) {
        _name.value = name
    }

    fun setAnimal(animal: String) {
        _animal.value = animal
    }

    fun isValidFormInputs(): Boolean {
        return name.isNotEmpty() && animal.isNotEmpty()
    }

}