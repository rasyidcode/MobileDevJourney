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
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it)
    }

    // Total cost for the order
    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = _total.map {
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it)
    }

    // Tax for the order
    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = _tax.map {
        NumberFormat.getCurrencyInstance(Locale("en", "US")).format(it)
    }

    /**
     * Set the entree for the order
     */
    fun setEntree(entree: String) {
        // TODO: if _entree.value is not null, set the previous
        //       entree price to the current entree price.

        // TODO: if _subtotal.value is not null subtract the previous
        //       entree price from the current subtotal value. This ensures
        //       that we only charge for the currently selected entree.

        // TODO: set the current entree value to the menu item corresponding to the passed in string

        // TODO: update the subtotal to reflect the price of the selected entree
    }

    /**
     * Side the side for the order.
     */
    fun setSide(side: String) {
        // TODO: if _side.value is not null, set the previous side price to the current side price.

        // TODO: if _subtotal.value is not null subtract the previous
        //       side price from the current subtotal value. This ensures
        //       that we only charge for the currently selected side.

        // TODO: set the current side value to the menu item corresponding to the passed in string

        // TODO: update the subtotal to reflect the price of the selected side.
    }

    /**
     * Set the accompaniment for the order
     */
    fun setAccompaniment(accompaniment: String) {
        // TODO: if _accompaniment.value is not null, set the previous side price to the
        //       current accompaniment price.

        // TODO: if _subtotal.value is not null subtract the previous
        //       side price from the current subtotal value. This ensures
        //       that we only charge for the currently selected accompaniment.

        // TODO: set the current accompaniment value to the menu item corresponding
        //       to the passed in string

        // TODO: update the subtotal to reflect the price of the selected accompaniment.
    }

    /**
     * Calculate tax and update total.
     */
    fun calculateTaxAndTotal() {
        // TODO: set _tax.value based on the subtotal and the tax rate.
        // TODO: set the total based on the subtotal and _tax.value
    }

    /**
     * Rest all values pertaining the order.
     */
    fun resetOrder() {
        // TODO: reset all values associated with an order
    }

    /**
     * Update subtotal value.
     */
    private fun updateSubtotal(itemPrice: Double) {
        // TODO: if _subtotal.value is not null, update it to reflect the price of the
        //       recently added item.
        //       Otherwise, set subtotal.value to equal to the price of the item.

        // TODO: calculate the tax and resulting total
    }
}