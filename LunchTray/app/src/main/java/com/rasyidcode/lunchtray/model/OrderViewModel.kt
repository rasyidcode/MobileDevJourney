package com.rasyidcode.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.rasyidcode.lunchtray.data.DataSource
import java.text.NumberFormat
import java.util.*

class OrderViewModel : ViewModel() {

    // Map of menu items
    val menuItems = DataSource.menuItems

    // Default values for item prices
    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    private var currentSelectedEntreeKey = ""
    private var currentSelectedSideKey = ""
    private var currentSelectedAccompanimentKey = ""

    // Default tax rate
    private val taxRate = 0.08

    // Entree for the order
    private val _entree = MutableLiveData<MenuItem?>()
    val entree: LiveData<MenuItem?> = _entree

    // SIde for the order
    private val _side = MutableLiveData<MenuItem?>()
    val side: LiveData<MenuItem?> = _side

    // Accompaniment for the order
    private val _accompaniment = MutableLiveData<MenuItem?>()
    val accompaniment: LiveData<MenuItem?> = _accompaniment

    // Subtotal for the order
    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = _subtotal.map {
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it ?: 0.0)
    }

    // Total cost for the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = _total.map {
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it ?: 0.0)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = _tax.map {
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it ?: 0.0)
    }

    init {
        resetOrder()
    }

    /**
     * Set the entree for the order
     */
    fun setEntree(entree: String) {
        currentSelectedEntreeKey = entree

        // if _entree.value is not null, set the previous
        // entree price to the current entree price.
        if (_entree.value != null) {
            previousEntreePrice = _entree.value!!.price
        }

        // if _subtotal.value is not null subtract the previous
        // entree price from the current subtotal value. This ensures
        // that we only charge for the currently selected entree.
        if (_subtotal.value != null) {
            _subtotal.value = _subtotal.value?.minus(previousEntreePrice)
        }

        // set the current entree value to the menu item corresponding to the passed in string
        _entree.value = menuItems[entree]

        // update the subtotal to reflect the price of the selected entree
        val itemPrice = _entree.value?.price
        itemPrice?.let { updateSubtotal(it) }
    }

    /**
     * Side the side for the order.
     */
    fun setSide(side: String) {
        currentSelectedSideKey = side

        // if _side.value is not null, set the previous side price to the current side price.
        if (_side.value != null) {
            previousSidePrice = _side.value!!.price
        }

        // if _subtotal.value is not null subtract the previous
        // side price from the current subtotal value. This ensures
        // that we only charge for the currently selected side.
        if (_subtotal.value != null) {
            _subtotal.value = _subtotal.value?.minus(previousSidePrice)
        }

        // set the current side value to the menu item corresponding to the passed in string
        _side.value = menuItems[side]

        // update the subtotal to reflect the price of the selected side.
        val itemPrice = _side.value?.price
        itemPrice?.let { updateSubtotal(it) }
    }

    /**
     * Set the accompaniment for the order
     */
    fun setAccompaniment(accompaniment: String) {
        currentSelectedAccompanimentKey = accompaniment

        // if _accompaniment.value is not null, set the previous side price to the
        // current accompaniment price.
        if (_accompaniment.value != null) {
            previousAccompanimentPrice = _accompaniment.value!!.price
        }

        // if _subtotal.value is not null subtract the previous
        // side price from the current subtotal value. This ensures
        // that we only charge for the currently selected accompaniment.
        if (_subtotal.value != null) {
            _subtotal.value = _subtotal.value?.minus(previousAccompanimentPrice)
        }

        // set the current accompaniment value to the menu item corresponding
        // to the passed in string
        _accompaniment.value = menuItems[accompaniment]

        // update the subtotal to reflect the price of the selected accompaniment.
        val itemPrice = _accompaniment.value?.price
        itemPrice?.let { updateSubtotal(it) }
    }

    fun isSelectedEntree(keyName: String): Boolean {
        return currentSelectedEntreeKey == keyName
    }

    fun isSelectedSide(keyName: String): Boolean {
        return currentSelectedSideKey == keyName
    }

    fun isSelectedAccompaniment(keyName: String): Boolean {
        return currentSelectedAccompanimentKey == keyName
    }

    /**
     * Rest all values pertaining the order.
     */
    fun resetOrder() {
        // reset all values associated with an order
        _entree.value = null
        _side.value = null
        _accompaniment.value = null
        _subtotal.value = null
        _tax.value = null
        _total.value = null

        previousEntreePrice = 0.0
        previousSidePrice = 0.0
        previousAccompanimentPrice = 0.0

        currentSelectedEntreeKey = ""
        currentSelectedSideKey = ""
        currentSelectedAccompanimentKey = ""
    }

    /**
     * Update subtotal value.
     */
    private fun updateSubtotal(itemPrice: Double) {
        // if _subtotal.value is not null, update it to reflect the price of the
        // recently added item.
        // Otherwise, set subtotal.value to equal to the price of the item.
        if (_subtotal.value != null) {
            _subtotal.value = _subtotal.value?.plus(itemPrice)
        } else {
            _subtotal.value = itemPrice
        }

        // calculate the tax and resulting total
        calculateTaxAndTotal()
    }

    /**
     * Calculate tax and update total.
     */
    private fun calculateTaxAndTotal() {
        // set _tax.value based on the subtotal and the tax rate.
        _tax.value = _subtotal.value?.times(taxRate)
        // set the total based on the subtotal and _tax.value
        _total.value = _tax.value?.let { _subtotal.value?.plus(it) }
    }
}