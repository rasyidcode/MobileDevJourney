package com.rasyidcode.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasyidcode.inventory.data.ItemDao
import java.lang.IllegalArgumentException

class InventoryViewModelFactory(
    private val itemDao: ItemDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}