package com.rasyidcode.androiddaggerbasic.storage

import android.content.Context
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    context: Context
) : Storage {

    private val sharedPreferences =
        context.getSharedPreferences("DaggerBasic", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) = with(sharedPreferences.edit()) {
        putString(key, value)
        apply()
    }

    override fun getString(key: String): String = sharedPreferences.getString(key, "") ?: ""
}