package com.rasyidcode.wordapp4.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with Context as
// receiver.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)

class SettingsDataSource(context: Context) {

    private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

    val preferenceFlow: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            // On the first run of the app. we will use LinearLayoutManager by default
            it[IS_LINEAR_LAYOUT_MANAGER] ?: true
        }

    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        context.dataStore.edit {
            it[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

}