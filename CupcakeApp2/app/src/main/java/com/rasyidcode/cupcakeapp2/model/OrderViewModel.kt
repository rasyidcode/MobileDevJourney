package com.rasyidcode.cupcakeapp2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _flavors = MutableLiveData<MutableList<String>>()
    val flavors: LiveData<String> = _flavors.map { flavorList ->
        flavorList.joinToString { "$it, " }.dropLast(2)
    }

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = _price.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

    fun setQuantity(numberOfCupcakes: Int) {
        _quantity.value = numberOfCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setFlavors(desiredFlavor: String) {
        _flavors.value?.let {
            if (!it.contains(desiredFlavor)) {
                _flavors.value?.add(desiredFlavor)
            } else {
                _flavors.value?.remove(desiredFlavor)
            }
        }
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun setUserName(customerName: String) {
        _userName.value = customerName
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    fun isMultipleFlavors(): Boolean {
        return _flavors.value?.isNotEmpty() ?: false
    }

    fun isQuantityMoreThanOne(): Boolean {
        val qty = _quantity.value ?: 0
        return qty > 1
    }

    fun isFlavorsContain(desiredFlavor: String): Boolean {
        return _flavors.value?.contains(desiredFlavor) ?: false
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _flavors.value = mutableListOf()
        _date.value = dateOptions[0]
        _price.value = 0.0
        _userName.value = ""
    }

    fun updateDateToTomorrow() {
        _date.value = dateOptions[1]
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    private fun updatePrice() {
        var calculatedPrice = (_quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }

        _price.value = calculatedPrice
    }
}