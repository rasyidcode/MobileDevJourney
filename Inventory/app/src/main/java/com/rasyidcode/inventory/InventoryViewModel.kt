package com.rasyidcode.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidcode.inventory.data.Item
import com.rasyidcode.inventory.data.ItemDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class InventoryViewModel(
    private val itemDao: ItemDao
) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    fun addNewItem(
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun isEntryValid(
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
}