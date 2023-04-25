package com.rasyidcode.forageapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidcode.forageapp.data.ForageableDao
import com.rasyidcode.forageapp.model.Forageable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * Shared [ViewModel] to provide data to the [ForageableListFragment], [ForageableDetailFragment],
 * and [AddForageableFragment] and allow for interaction the [ForageableDao]
 */
class ForageableViewModel(
    private val forageableDao: ForageableDao
) : ViewModel() {

    // create a property to set a list of all forageables from the DAO
    val forageables: LiveData<List<Forageable>> = forageableDao.getForageables().asLiveData()

    // create method that takes id: Long as parameter and retrieve a Forageable from the
    //  database by id via the DAO
    fun getForageable(id: Long): LiveData<Forageable> = forageableDao.getForageable(id).asLiveData()

    // launch a coroutine and call the DAO method to add a Forageable to the database within it
    fun addForageable(
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            forageableDao.insert(forageable)
        }
    }

    fun updateForageable(
        id: Long,
        name: String,
        address: String,
        inSeason: Boolean,
        notes: String
    ) {
        val forageable = Forageable(
            id = id,
            name = name,
            address = address,
            inSeason = inSeason,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to upodate a forageable to the database here
            forageableDao.update(forageable)
        }
    }

    fun deleteForageable(forageable: Forageable) {
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to delete a forageable to the database here
            forageableDao.delete(forageable)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }
}

//  create a view model factory that takes a ForageableDao as a property and
//  create a ForageableViewModel
class ForageableViewModelFactory(
    private val forageableDao: ForageableDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForageableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ForageableViewModel(forageableDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}